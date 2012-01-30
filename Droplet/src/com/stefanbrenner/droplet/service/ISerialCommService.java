/*******************************************************************************
 * Project: Droplet - Toolkit for Liquid Art Photographers
 * Copyright (C) 2012 Stefan Brenner
 *
 * This file is part of Droplet.
 *
 * Droplet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Droplet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Droplet. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.stefanbrenner.droplet.service;

import gnu.io.CommPortIdentifier;

/**
 * Interface for Serial Communication.
 * 
 * @author Stefan Brenner
 */
public interface ISerialCommService {

	/**
	 * Returns all available serial ports.
	 * 
	 * @return Array of available serial ports
	 */
	public abstract CommPortIdentifier[] getPorts();

	/**
	 * Indicates if the service is currently connected to a serial port.
	 * 
	 * @return <code>true</code> if the serivce is connected, otherwise
	 *         <code>false</code>
	 */
	public abstract boolean isConnected();

	/**
	 * Send a message to the receiver.
	 * 
	 * @param message
	 *            message to send
	 */
	public abstract void sendData(String message);

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public abstract void close();

	/**
	 * Connect to a given serial port.
	 * 
	 * @param portId
	 *            port identifier to connect to
	 */
	public abstract boolean connect(CommPortIdentifier portId);

}
