/**
 * Create at Jan 31, 2013
 */
package com.jason.netty.startup;


import com.jason.utils.Constants;

/**
 * @author jason
 *
 */
public class Config {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Config [port=" + port + ", portBackend=" + portBackend + "]";
	}

	private int port = Constants.BUSINESS_PORT;
	private int portBackend = Constants.CONSOLE_PORT;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the portBackend
	 */
	public int getPortBackend() {
		return portBackend;
	}

	/**
	 * @param portBackend the portBackend to set
	 */
	public void setPortBackend(int portBackend) {
		this.portBackend = portBackend;
	}

}
