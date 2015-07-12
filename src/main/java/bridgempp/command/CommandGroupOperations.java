/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgempp.command;

import java.util.logging.Level;

import bridgempp.GroupManager;
import bridgempp.Message;
import bridgempp.PermissionsManager;
import bridgempp.ShadowManager;
import bridgempp.data.Endpoint;
import bridgempp.data.Group;
import bridgempp.messageformat.MessageFormat;

/**
 *
 * @author Vinpasso
 */
public class CommandGroupOperations {

    //Unsubscribe the Message's Sender to the specified Group with name
    private static Group unsubscribeGroup(String name, Endpoint endpoint) {
        Group group = GroupManager.findGroup(name);
        if (group == null) {
            return null;
        }
        group.removeEndpoint(endpoint);
        return group;
    }

    static void cmdRemoveGroup(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.CREATE_REMOVE_GROUP)) {
            boolean success = removeGroup(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()));
            if (success) {
                ShadowManager.log(Level.FINE, "Group has been removed: " + CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()));
                message.getOrigin().sendOperatorMessage("Group has been removed");
            } else {
                message.getOrigin().sendOperatorMessage("Error: Group not found");
            }
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    //Create a Group with name
    private static Group createGroup(String name) {
        if (GroupManager.findGroup(name) != null) {
            return null;
        }
        Group group = GroupManager.newGroup();
        group.setName(name);
        return group;
    }

    static void cmdSubscribeGroup(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.SUBSCRIBE_UNSUBSCRIBE_GROUP)) {
            Group group = subscribeGroup(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()), message.getOrigin());
            if (group != null) {
                ShadowManager.log(Level.FINE, message.getOrigin().toString() + " has been subscribed: " + group.getName());
                group.sendMessage(new Message(message.getSender(), message.getOrigin(), null, group, "BridgeMPP: Endpoint: " + message.getOrigin().toString() + " has been added to Group: " + group.getName(), MessageFormat.PLAIN_TEXT));
                message.getOrigin().sendOperatorMessage("Group has been subscribed");
            } else {
                message.getOrigin().sendOperatorMessage("Error: Group not found");
            }
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    static void cmdListGroups(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.LIST_GROUPS)) {
            message.getOrigin().sendOperatorMessage("Listing Groups:\nBridgeMPP: " + GroupManager.listGroups().replaceAll("\n", "\nBridgeMPP: ") + "BridgeMPP: Finished listing Groups");
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    static void cmdUnsubscribeGroup(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.SUBSCRIBE_UNSUBSCRIBE_GROUP)) {
            Group group = unsubscribeGroup(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()), message.getOrigin());
            if (group != null) {
                ShadowManager.log(Level.FINE, message.getOrigin().toString() + " has been unsubscribed: " + group.getName());
                group.sendMessage(new Message(message.getSender(), message.getOrigin(), null, group, "BridgeMPP: Endpoint: " + message.getOrigin().toString() + " has been removed from Group: " + group.getName(), MessageFormat.PLAIN_TEXT));
                message.getOrigin().sendOperatorMessage("Group has been unsubscribed");
            } else {
                message.getOrigin().sendOperatorMessage("Error: Group not found");
            }
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    //Subscribe the Message's Sender to the specified Group with name
    private static Group subscribeGroup(String message, Endpoint sender) {
        Group group = GroupManager.findGroup(message);
        if (group == null) {
            return null;
        }
        group.addEndpoint(sender);
        return group;
    }

    static void cmdListMembers(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.LIST_MEMBERS)) {
            Group group = GroupManager.findGroup(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()));
            if (group == null) {
                message.getOrigin().sendOperatorMessage("Error: No such group");
                return;
            }
            message.getOrigin().sendOperatorMessage("Listing Members:\nBridgeMPP: " + group.toString().replaceAll("\n", "\nBridgeMPP: ") + "Finished listing Members");
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    static void cmdCreateGroup(Message message) {
        if (CommandInterpreter.checkPermission(message.getOrigin(), PermissionsManager.Permission.CREATE_REMOVE_GROUP)) {
            Group group = createGroup(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()));
            if (group == null) {
                message.getOrigin().sendOperatorMessage("Error: Group already exists");
            } else {
                ShadowManager.log(Level.FINE, "Group has been created: " + group.getName());
                message.getOrigin().sendOperatorMessage("Group has been created: " + group.getName());
            }
        } else {
            message.getOrigin().sendOperatorMessage("Access denied");
        }
    }

    //Remove everyone from Group and destroy Group
    private static boolean removeGroup(String name) {
        Group group = GroupManager.findGroup(name);
        if (group == null) {
            return false;
        }
        GroupManager.removeGroup(group);
        return true;
    }
    
}
