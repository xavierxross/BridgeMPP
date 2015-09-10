/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgempp;

import bridgempp.service.BridgeService;
import bridgempp.services.ConsoleService;
import bridgempp.storage.PersistanceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Vinpasso
 */
public class ServiceManager
{

	private static ArrayList<BridgeService> services;

	// Load Services from Config
	public static void loadAllServices()
	{
		ShadowManager.log(Level.INFO, "Loading all Services...");

		services = new ArrayList<>();
		Collection<BridgeService> serviceConfigurations = PersistanceManager.getPersistanceManager().getServiceConfigurations();
		Iterator<BridgeService> iterator = serviceConfigurations.iterator();
		while (iterator.hasNext())
		{
			BridgeService service = iterator.next();
			ShadowManager.log(Level.INFO, "Loading Service: " + service.getName());
			loadService(service);
			ShadowManager.log(Level.INFO, "Loaded Service: " + service.getName());
		}
		if (serviceConfigurations.isEmpty())
		{
			setupFirstRun();
		}
		ShadowManager.log(Level.INFO, "All Services loaded");

	}

	private static void setupFirstRun()
	{
		ShadowManager.log(Level.INFO, "No Services loaded. Automatically loading Console-Service");
		loadService(new ConsoleService());
		ShadowManager.log(Level.INFO, "Loaded Service: Console Service. Due to automatic loading.");
		ShadowManager.log(Level.INFO, "Creating Server Key. Due to automatic loading");
		String key = PermissionsManager.generateKey(Integer.MAX_VALUE, true);
		ShadowManager.log(Level.INFO, "Created Server Key " + key + ". Due to automatic loading");
	}

	public static void loadService(BridgeService service)
	{
		services.add(service);
		connectService(service);
		PersistanceManager.getPersistanceManager().updateState(service);
	}

	private static void connectService(BridgeService service)
	{
		try
		{
			service.connect();
		} catch (Exception e)
		{
			ShadowManager.log(Level.SEVERE, "Could not load Service: " + service.toString());
			ShadowManager.fatal(e);
		}
	}

	public static void unloadAllServices()
	{
		ShadowManager.log(Level.INFO, "Unloading all Services...");
		for (int i = 0; i < services.size(); i++)
		{
			try
			{
				ShadowManager.log(Level.INFO, "Unloading Service: " + services.get(i).getName());
				services.get(i).disconnect();
				ShadowManager.log(Level.INFO, "Unloaded Service: " + services.get(i).getName());
			} catch (Exception e)
			{
				ShadowManager.log(Level.INFO, "Unloading of Service " + services.get(i).getName() + " has failed", e);
			}
		}
		ShadowManager.log(Level.INFO, "Unloaded all Services...");
	}

	public static BridgeService getServiceByServiceIdentifier(int serviceIdentifierId)
	{
		Iterator<BridgeService> iterator = services.iterator();
		while (iterator.hasNext())
		{
			BridgeService service = iterator.next();
			if (service.getIdentifier() == serviceIdentifierId)
			{
				return service;
			}
		}
		return null;
	}

	public static List<BridgeService> listServices()
	{
		return Collections.unmodifiableList(services);
	}

	public static void unloadService(int serviceID)
	{
		BridgeService service = getServiceByServiceIdentifier(serviceID);
		ShadowManager.log(Level.WARNING, "Unloading Service: " + service.toString());
		services.remove(service);
		PersistanceManager.getPersistanceManager().removeState(service);
		disconnectService(service);
	}

	private static void disconnectService(BridgeService service)
	{
		try
		{
			service.disconnect();
		} catch (Exception e)
		{
			ShadowManager.log(Level.SEVERE, "Could not unload Service: " + service.toString());
			ShadowManager.fatal(e);

		}
	}
}
