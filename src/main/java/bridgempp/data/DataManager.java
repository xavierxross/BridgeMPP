package bridgempp.data;

import java.util.List;

import bridgempp.BridgeService;
import bridgempp.storage.PersistanceManager;

public class DataManager
{

	private static Endpoint registerEndpoint(BridgeService service, String identifier)
	{
		Endpoint endpoint = new Endpoint(service, identifier);
		//TODO: Persist this
		return endpoint;
	}
	
	private static User registerUser(String identifier)
	{
		User user = new User(identifier);
		//TODO: Persist this
		return user;
	}
	
	/**
	 * Fetches a User for a given Identifier
	 * @param identifier The Primary Key to find the User from
	 * @return The User found from the Primary Key
	 */
	public static User getUserForIdentifier(String identifier, BridgeService service)
	{
		return PersistanceManager.getPersistanceManager().getUserForIdentifier(getUserIdentifier(identifier, service));
	}
	
	/**
	 * Fetches a User for a given Identifier. Creates a new User if it doesn't exist
	 * @param identifier The Primary Key to find the User from
	 * @return The User found from the Primary Key, or a new User
	 */
	public static User getOrNewUserForIdentifier(String identifier, BridgeService service, Endpoint... endpoints)
	{
		User user = getUserForIdentifier(identifier, service);
		if(user == null)
		{
			user = registerUser(getUserIdentifier(identifier, service));
		}
		for(Endpoint endpoint : endpoints)
		{
			endpoint.putUser(user);
		}
		return user;
	}
	
	/**
	 * Fetches an Endpoint for a given Identifier
	 * @param identifier The Primary Key to find the Endpoint from
	 * @return The Endpoint found from the Primary Key
	 */
	public static Endpoint getEndpointForIdentifier(String identifier, BridgeService service)
	{
		return PersistanceManager.getPersistanceManager().getEndpointForIdentifier(getEndpointIdentifier(identifier, service));
	}
	
	/**
	 * Fetches a Endpoint for a given Identifier. Creates a new Endpoint if it doesn't exist
	 * @param identifier The Primary Key to find the Endpoint from
	 * @return The Endpoint found from the Primary Key, or a new Endpoint
	 */
	public static Endpoint getOrNewEndpointForIdentifier(String identifier, BridgeService service)
	{
		Endpoint endpoint = getEndpointForIdentifier(identifier, service);
		if(endpoint == null)
		{
			endpoint = registerEndpoint(service, getEndpointIdentifier(identifier, service));
		}
		return endpoint;
	}
	
	private static String getEndpointIdentifier(String identifier, BridgeService service)
	{
		return identifier + "@" + service.getName();
	}

	private static String getUserIdentifier(String identifier, BridgeService service)
	{
		return identifier + "@" + service.getName();
	}
}
