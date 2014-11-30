/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgempp.command;

import bridgempp.Endpoint;
import bridgempp.EndpointTranslator;
import bridgempp.Message;
import bridgempp.ShadowManager;
import bridgempp.PermissionsManager.Permission;

import java.util.Scanner;
import java.util.logging.Level;

/**
 *
 * @author Vinpasso
 */
public class CommandAliasOperations {

    static void cmdCreateAlias(Message message) {
        if (!message.getSender().hasAlias()) {
            message.getSender().sendOperatorMessage("You already have an Alias, overwriting your old Alias");
        }
        String newAlias = CommandInterpreter.getStringFromArgument(message.getPlainTextMessage());
        ShadowManager.log(Level.FINER, "Endpoint: " + message.getSender().toString() + " now has assigned Alias: " + newAlias);
        EndpointTranslator.saveHumanReadableEndpoint(message.getSender(), newAlias);
        message.getSender().sendOperatorMessage("Alias successfully assigned");
    }

    static void cmdImportAliasList(Message message) {
        if (CommandInterpreter.checkPermission(message.getSender(), Permission.IMPORT_ALIAS)) {
            Scanner scanner = new Scanner(CommandInterpreter.getStringFromArgument(message.getPlainTextMessage()));
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                EndpointTranslator.saveHumanReadableEndpoint(new Endpoint(null, scanner.next()), rearrangeNameFormat(scanner.next()));
            }
            scanner.close();
            message.getSender().sendOperatorMessage("Alias List successfully imported");
        }
        else
        {
            message.getSender().sendOperatorMessage("Access Denied");
        }
    }
    
    static void removeAlias(Message message)
    {
    	if(!message.getSender().hasAlias())
    	{
    		message.getSender().sendOperatorMessage("No aliases found");
    	}
    	EndpointTranslator.removeHumanReadableEndpoint(message.getSender());
    	message.getSender().sendOperatorMessage("Aliases successfully deleted");
    }

    //If user is written in outlook Last Name, First Name format then rearrange it to First Name Last Name
    private static String rearrangeNameFormat(String name) {
        if (name.contains(", ")) {
            name = name.substring(name.indexOf(", ") + 2) + " " + name.substring(0, name.indexOf(", "));
        }
        return name;
    }

}