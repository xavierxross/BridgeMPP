package bridgempp.services.whatsapp;

import java.util.Scanner;
import java.util.logging.Level;

import bridgempp.ShadowManager;

final class WhatsappErrorListener implements Runnable {
	/**
	 * 
	 */
	private final WhatsappMessageListener whatsappMessageListener;

	/**
	 * @param whatsappMessageListener
	 */
	WhatsappErrorListener(WhatsappMessageListener whatsappMessageListener) {
		this.whatsappMessageListener = whatsappMessageListener;
	}

	@Override
	public void run() {
		ShadowManager.log(Level.INFO, "Starting Yowsup Error Listener...");

		Scanner error = new Scanner(this.whatsappMessageListener.whatsappService.yowsup.getErrorStream());
		while (error.hasNext()) {
			System.err.println(error.nextLine());
		}
		error.close();
		ShadowManager.log(Level.INFO, "Stopping Yowsup Error Listener");
	}
}