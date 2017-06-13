/**
 * Create at Jan 31, 2013
 */
package com.jason.netty.startup;

import com.jason.utils.SpringBeanFactory;
import org.apache.log4j.Logger;

/**
 * @author jason
 * 
 *         Server bootstrap
 */
public class Bootstrap {

	private static Logger log = Logger.getLogger(Bootstrap.class.getName());

	/**
	 * Daemon object used by main.
	 */
	private static Server daemon = null;

	private static Server getInstance() {
        // load server
        HttpServer server = SpringBeanFactory.getHttpServer();
        return server;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (daemon == null) {
			daemon = getInstance();
			try {
				daemon.init();
			} catch (Throwable t) {
				t.printStackTrace();
				return;
			}
		}

		try {
			String command = "start";
			if (args.length > 0) {
				command = args[args.length - 1];
			}

			if (command.equals("start")) {
				// init reflaction, log4j etc...
				// ApplicationConfig.init();
				try {
					daemon.start();
				} catch (Exception e) {
					e.printStackTrace();
					log.fatal(e.getMessage());
					log.fatal("exit program. pls check and restart again.");
					System.exit(0);
				}
			} else if (command.equals("stop")) {
				log.info("Stopping...");
				daemon.stop();
			} else if (command.equals("status")) {
				log.info("Status...");
				daemon.status();
			} else {
				log.warn("Bootstrap: command \"" + command
						+ "\" does not exist.");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
