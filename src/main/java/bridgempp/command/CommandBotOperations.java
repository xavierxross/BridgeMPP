package bridgempp.command;

import java.util.logging.Level;

import bridgempp.Message;
import bridgempp.PermissionsManager;
import bridgempp.ShadowManager;
import bridgempp.PermissionsManager.Permission;
import bridgempp.command.wrapper.CommandName;
import bridgempp.command.wrapper.CommandTrigger;
import bridgempp.command.wrapper.HelpTopic;
import bridgempp.command.wrapper.RequiredPermission;
import bridgempp.data.Group;

public class CommandBotOperations
{

	@CommandName("!botcreatealias: Set the current users alias")
	@CommandTrigger("!botcreatealias")
	@HelpTopic("Sets the current users alias to the given parameter. Does not send confirmation messages.")
	public static void cmdCreateAlias(Message message, String newAlias)
	{
		ShadowManager.log(Level.FINER, "Endpoint: " + message.getOrigin().toString() + " now has assigned Alias: " + newAlias);
		message.getSender().setName(newAlias);
	}
	
	@CommandName("!botsubscribegroup: Join a group")
	@CommandTrigger("!botsubscribegroup")
	@HelpTopic("Subscribe the Message's Sender to the specified Group with name. Does not send confirmation messages.")
	@RequiredPermission(Permission.SUBSCRIBE_UNSUBSCRIBE_GROUP)
	public static void cmdSubscribeGroup(String name, Message message)
	{
		Group group = CommandGroupOperations.subscribeGroup(name, message.getOrigin());
		if (group != null)
		{
			ShadowManager.log(Level.FINE, message.getOrigin().toString() + " has been subscribed: " + group.getName());
		} else
		{
			message.getOrigin().sendOperatorMessage("Error: Group not found");
		}
	}
	
	@CommandName("!botusekey: Use a BridgeMPP access key")
	@CommandTrigger("!botusekey")
	@HelpTopic("Uses a BridgeMPP Access key to give the Sender of the message the key's rights. Does not send confirmation messages.")
	public static void cmdUseKey(String key, Message message)
	{
		boolean success = PermissionsManager.useKey(key, message.getOrigin());
		if (success)
		{
			ShadowManager.log(Level.INFO, message.getOrigin().toString() + " has used key and now has " + message.getOrigin().getPermissions());
		} else
		{
			message.getOrigin().sendOperatorMessage("Key Failure. Incorrect Key");
		}
	}
	
}
