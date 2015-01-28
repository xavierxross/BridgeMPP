/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgempp.services;

import bridgempp.BridgeService;
import bridgempp.Endpoint;
import bridgempp.ShadowManager;
import bridgempp.command.CommandInterpreter;
import bridgempp.messageformat.MessageFormat;
import bridgempp.services.XMPPService.BOB.BOBIQ;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.filter.IQTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.xhtmlim.XHTMLManager;
import org.jivesoftware.spark.util.DummySSLSocketFactory;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Vinpasso
 */
public class XMPPService implements BridgeService {

	private XMPPTCPConnection connection;
	private ChatManager chatmanager;
	private HashMap<String, XMPPMessageListener> activeChats;
	private HashMap<String, String> cachedObjects;

	private static MessageFormat[] supportedMessageFormats = new MessageFormat[] { MessageFormat.XHTML,
			MessageFormat.PLAIN_TEXT };

	public XMPPService() {
		activeChats = new HashMap<>();
		cachedObjects = new HashMap<>();
	}

	@Override
	// Arguments <server>; <port>; <domain>; <username>; <password>; <status>;
	// <oldStyleSSL>
	public void connect(String parameters) {
		try {
			ShadowManager.log(Level.INFO, "Starting XMPP Service...");

			String[] args = parameters.split("; ");
			if (args.length != 7) {
				throw new UnsupportedOperationException("XMPP Configuration Error: " + parameters);
			}
			ConnectionConfiguration configuration = new ConnectionConfiguration(args[0], Integer.parseInt(args[1]),
					args[2]);
			if (Boolean.parseBoolean(args[6])) {
				configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
				configuration.setSocketFactory(new DummySSLSocketFactory());
			}
			connection = new XMPPTCPConnection(configuration);
			connection.connect();
			connection.login(args[3], args[4]);
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus(args[5]);
			connection.sendPacket(presence);
			connection.addPacketListener(new XMPPRosterListener(), new PacketFilter() {

				@Override
				public boolean accept(Packet packet) {
					if (packet instanceof Presence) {
						if (((Presence) packet).getType().equals(Presence.Type.subscribe)) {
							return true;
						}
					}
					return false;
				}

			});
			connection.getRoster().setSubscriptionMode(Roster.SubscriptionMode.manual);
			chatmanager = ChatManager.getInstanceFor(connection);
			chatmanager.addChatListener(new XMPPChatListener());
			MultiUserChat.addInvitationListener(connection, new XMPPMultiUserChatListener());
			ShadowManager.log(Level.INFO, "Started XMPP Service");
			ProviderManager.addIQProvider("data", "urn:xmpp:bob", new BOB());
			connection.addPacketListener(new XMPPIQPacketListener(), new PacketFilter() {

				@Override
				public boolean accept(Packet packet) {
					return packet instanceof BOBIQ;
				}
			});
		} catch (XMPPException | SmackException | IOException ex) {
			Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void disconnect() {
		try {
			ShadowManager.log(Level.INFO, "Stopping XMPP Service...");
			connection.disconnect();
			// Prevent Executor services from idling in the background
			connection = null;
			ShadowManager.log(Level.INFO, "Stopped XMPP Service...");
		} catch (SmackException.NotConnectedException ex) {
			Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void sendMessage(bridgempp.Message message) {
		activeChats.get(message.getTarget().getTarget()).sendMessage(message);
	}

	@Override
	public String getName() {
		return "XMPP";
	}

	@Override
	public boolean isPersistent() {
		return true;
	}

	@Override
	public void addEndpoint(Endpoint endpoint) {
		if (endpoint.getExtra().isEmpty()) {
			XMPPSingleChatMessageListener listener = new XMPPSingleChatMessageListener(endpoint);
			listener.chat.addMessageListener(listener);
		} else {
			XMPPMultiUserMessageListener listener = new XMPPMultiUserMessageListener(endpoint);
			listener.multiUserChat.addMessageListener(listener);
		}
	}

	@Override
	public void interpretCommand(bridgempp.Message message) {
		message.getSender().sendOperatorMessage(getClass().getSimpleName() + ": No supported Protocol options");
	}

	private class XMPPRosterListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) throws SmackException.NotConnectedException {
			try {
				Presence presence = (Presence) packet;
				Presence subscribed = new Presence(Presence.Type.subscribed);
				subscribed.setTo(packet.getFrom());
				connection.sendPacket(subscribed);
				connection.getRoster().createEntry(presence.getFrom(), presence.getFrom(), null);
				Presence subscribeRequest = new Presence(Presence.Type.subscribe);
				subscribeRequest.setTo(packet.getFrom());
				connection.sendPacket(subscribeRequest);
			} catch (SmackException.NotLoggedInException | SmackException.NoResponseException
					| XMPPException.XMPPErrorException ex) {
				Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	interface XMPPMessageListener {

		public void sendMessage(bridgempp.Message message);
	}

	class XMPPSingleChatMessageListener implements XMPPMessageListener, MessageListener {

		Endpoint endpoint;
		private Chat chat;

		// For resumed Chats
		public XMPPSingleChatMessageListener(Endpoint endpoint) {
			this.endpoint = endpoint;
			chat = chatmanager.createChat(endpoint.getTarget(), this);
			activeChats.put(endpoint.getTarget(), this);
		}

		public XMPPSingleChatMessageListener(Chat chat) {
			this.chat = chat;
			endpoint = new Endpoint(XMPPService.this, chat.getParticipant());
			activeChats.put(endpoint.getTarget(), this);
		}

		@Override
		public void processMessage(Chat chat, Message message) {
			CommandInterpreter.processMessage(new bridgempp.Message(endpoint, message.getBody(),
					getSupportedMessageFormats()[0]));
		}

		@Override
		public void sendMessage(bridgempp.Message message) {
			try {
				Message sendMessage = new Message();
				if (message.chooseMessageFormat(supportedMessageFormats).equals(MessageFormat.XHTML)) {
					String messageContents = message.toSimpleString(supportedMessageFormats);
					messageContents = cacheEmbeddedBase64Image(messageContents);
					XHTMLManager.addBody(sendMessage, messageContents);
				}

				sendMessage.addBody(null, message.toSimpleString(new MessageFormat[] { MessageFormat.PLAIN_TEXT }));
				chat.sendMessage(sendMessage);
			} catch (SmackException.NotConnectedException ex) {
				Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	class XMPPChatListener implements ChatManagerListener {

		@Override
		public void chatCreated(Chat chat, boolean createdLocally) {
			if (!createdLocally) {
				chat.addMessageListener(new XMPPSingleChatMessageListener(chat));
			}
		}
	}

	class XMPPMultiUserChatListener implements InvitationListener {

		@Override
		public void invitationReceived(XMPPConnection conn, String room, String inviter, String reason,
				String password, Message message) {
			try {
				MultiUserChat multiUserChat = new MultiUserChat(conn, room);
				DiscussionHistory discussionHistory = new DiscussionHistory();
				discussionHistory.setMaxStanzas(0);
				multiUserChat.join("BridgeMPP", password, discussionHistory, conn.getPacketReplyTimeout());
				multiUserChat.addMessageListener(new XMPPMultiUserMessageListener(multiUserChat));
			} catch (XMPPException.XMPPErrorException | SmackException.NoResponseException
					| SmackException.NotConnectedException ex) {
				Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	class XMPPMultiUserMessageListener implements XMPPMessageListener, PacketListener {

		Endpoint endpoint;
		MultiUserChat multiUserChat;

		public XMPPMultiUserMessageListener(Chat chat) {
			throw new UnsupportedOperationException("Attempted to create multiuserchat from Single Chat");
		}

		// For resumed Chats
		public XMPPMultiUserMessageListener(Endpoint endpoint) {
			try {
				multiUserChat = new MultiUserChat(connection, endpoint.getTarget());
				DiscussionHistory discussionHistory = new DiscussionHistory();
				discussionHistory.setMaxStanzas(0);
				multiUserChat.join("BridgeMPP", "", discussionHistory, connection.getPacketReplyTimeout());
				this.endpoint = endpoint;
				activeChats.put(endpoint.getTarget(), this);
			} catch (XMPPException.XMPPErrorException | SmackException.NoResponseException
					| SmackException.NotConnectedException ex) {
				Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		public XMPPMultiUserMessageListener(MultiUserChat multiUserChat) {
			this.multiUserChat = multiUserChat;
			endpoint = new Endpoint(XMPPService.this, multiUserChat.getRoom());
			activeChats.put(endpoint.getTarget(), this);
		}

		@Override
		public void sendMessage(bridgempp.Message message) {
			try {
				Message sendMessage = new Message(multiUserChat.getRoom(), Message.Type.groupchat);
				if (message.chooseMessageFormat(supportedMessageFormats).equals(MessageFormat.XHTML)) {
					String messageContents = message.toSimpleString(supportedMessageFormats);
					messageContents = cacheEmbeddedBase64Image(messageContents);
					XHTMLManager.addBody(sendMessage, "<body xmlns=\"http://www.w3.org/1999/xhtml\">" + messageContents
							+ "</body>");
				}
				sendMessage.addBody(null, message.toSimpleString(new MessageFormat[] { MessageFormat.PLAIN_TEXT }));
				multiUserChat.sendMessage(sendMessage);
			} catch (XMPPException | SmackException.NotConnectedException ex) {
				Logger.getLogger(XMPPService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		@Override
		public void processPacket(Packet packet) throws SmackException.NotConnectedException {
			Message message = (Message) packet;
			if (message.getType() != Message.Type.groupchat || message.getBody() == null
					|| !message.getFrom().contains(multiUserChat.getRoom() + "/")
					|| message.getFrom().contains(multiUserChat.getRoom() + "/" + multiUserChat.getNickname())) {
				return;
			}
			String jid = multiUserChat.getOccupant(message.getFrom()).getJid();
			if (jid != null) {
				endpoint.setExtra(jid.substring(0, jid.indexOf("/")));
			} else {
				endpoint.setExtra(message.getFrom().substring(message.getFrom().indexOf("/")));
			}
			CommandInterpreter.processMessage(new bridgempp.Message(endpoint, message.getBody(),
					getSupportedMessageFormats()[0]));
		}
	}

	@Override
	public MessageFormat[] getSupportedMessageFormats() {
		return supportedMessageFormats;
	}

	private String cacheEmbeddedBase64Image(String messageContents) {
		Matcher matcher = Pattern.compile("<img src=\"data:image\\/jpeg;base64,(.*?)\".*?\\/>").matcher(
				messageContents);
		//cachedObjects.clear();
		for(String key : new HashSet<String>(cachedObjects.keySet()))
		{
			if(System.currentTimeMillis() - Long.parseLong(key.substring(0, key.indexOf('@'))) > 3600000l)
			{
				cachedObjects.remove(key);
			}
		}
		while (matcher.find()) {
			String data = matcher.group(1);
			String identifier = System.currentTimeMillis() + "@bob.xmpp.org";
			messageContents = messageContents
					.replace("data:image/jpeg;base64," + data, "cid:" + identifier);
			cachedObjects.put(identifier, data);
		}
		return messageContents;
	}

	public class XMPPIQPacketListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) throws NotConnectedException {
			BOBIQ iq = (BOBIQ) packet;
			String hash = iq.hash;
			String data = cachedObjects.get(hash);
			BOBIQ reply = new BOBIQ();
			reply.hash = hash;
			reply.data = data;
			reply.setType(IQ.Type.RESULT);
			reply.setTo(iq.getFrom());
			reply.setPacketID(iq.getPacketID());
			reply.setFrom(iq.getTo());
			connection.sendPacket(reply);
		}

	}

	public static class BOB implements IQProvider {

		@Override
		public IQ parseIQ(XmlPullParser parser) throws Exception {
			BOBIQ bOBIQ = new BOBIQ();
			boolean done = false;
			while (!done) {
				if ("data".equals(parser.getName())) {
					bOBIQ.hash = parser.getAttributeValue("", "cid");
					done = true;
				}
				int eventType = parser.next();

				if (eventType == XmlPullParser.START_TAG && "data".equals(parser.getName())) {
					// Initialize the variables from the parsed XML
					bOBIQ.hash = parser.getAttributeValue("", "cid");
				} else if (eventType == XmlPullParser.END_TAG && "data".equals(parser.getName())) {
					done = true;
				}
			}
			return bOBIQ;
		}

		public static class BOBIQ extends IQ {
			private String hash;
			private String data;

			public CharSequence getChildElementXML() {
				return "<data xmlns='urn:xmpp:bob' cid='" + hash + "' type='image/jpeg'>" + data + "</data>";
			}
		}
	}

}
