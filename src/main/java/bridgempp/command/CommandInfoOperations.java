package bridgempp.command;

import java.util.Collection;

import bridgempp.Message;
import bridgempp.PermissionsManager.Permission;
import bridgempp.command.wrapper.CommandName;
import bridgempp.command.wrapper.CommandTrigger;
import bridgempp.command.wrapper.HelpTopic;
import bridgempp.command.wrapper.RequiredPermission;
import bridgempp.data.DataManager;
import bridgempp.data.Endpoint;
import bridgempp.data.User;

public class CommandInfoOperations {

	@CommandName("!listendpoints: List all BridgeMPP Endpoints")
	@CommandTrigger("!listendpoints")
	@HelpTopic("Lists all Endpoints in the BridgeMPP Database")
	@RequiredPermission(Permission.LIST_ENDPOINTS)
	public static String cmdListEndpoints(Message message)
	{
		Collection<Endpoint> endpoints = DataManager.getAllEndpoints();
		String endpointList = "";
		for(Endpoint endpoint : endpoints)
		{
			endpointList += endpoint.toString() + "\n";
		}
		return "Listing all Endpoints:\n" + endpointList.trim() + "\nListed all Endpoints";
	}
	
	@CommandName("!listusers: List all BridgeMPP Users")
	@CommandTrigger("!listusers")
	@HelpTopic("Lists all Users in the BridgeMPP Database")
	@RequiredPermission(Permission.LIST_ENDPOINTS)
	public static String cmdListUsers(Message message)
	{
		Collection<User> users = DataManager.getAllUsers();
		String userList = "";
		for(User user : users)
		{
			userList += user.getIdentifier() + ": " + user.toString() + "\n";
		}
		return "Listing all Users:\n" + userList.trim() + "\nListed all Users";
	}
	
	@CommandName("!getendpointusers: List all Users in an Endpoint")
	@CommandTrigger("!getendpointusers")
	@HelpTopic("Lists all registered Users for an Endpoint in the Database. Requires Endpoint Identifier")
	@RequiredPermission(Permission.LIST_ENDPOINTS)
	public static String cmdGetEndpointUsers(Message message, String identifier)
	{
		Endpoint endpoint = DataManager.getEndpointForIdentifier(identifier);
		if(endpoint == null)
		{
			return "Endpoint not found!";
		}
		String userList = "";
		for(User user : endpoint.getUsers())
		{
			userList += user.getInfo() + "\n";
		}
		return "Listing all Users:\n" + userList.trim() + "\nListed all Users";
	}
	
}
