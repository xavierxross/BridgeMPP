package bridgempp.servicecomponent;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import bridgempp.ShadowManager;
import bridgempp.data.MessageNodeIO;
import bridgempp.message.Message;

public class MessageBodyBase64UTF8Encoder extends MessageNodeIO<Message, Message>
{
	
	public Message processIO(Message message)
	{
		try
		{
			return new Message(message.getSender(), message.getOrigin(), message.getDestination(), message.getGroup(), Base64.getEncoder().encodeToString(message.getMessageRaw().getBytes("UTF-8")), message.getMessageFormat());
		} catch (UnsupportedEncodingException e)
		{
			ShadowManager.fatal("Platform does not support UTF-8 encoding. Fatal.");
		}
		return null;
	}

}
