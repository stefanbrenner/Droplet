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

import com.stefanbrenner.droplet.model.IDroplet;

/**
 * Service Interface for the Droplet Message Protocol.
 * 
 * @author Stefan Brenner
 */
public interface IDropletMessageProtocol {

	/**
	 * Returns the name of the service provider used for service selection on
	 * the user interface.
	 */
	String getName();

	/**
	 * Returns a message to start droplet for one round
	 */
	String createStartMessage();

	/**
	 * Returns a message to start droplet
	 * 
	 * @param rounds
	 *            number of rounds to execute
	 * @param delay
	 *            delay between rounds in seconds
	 */
	String createStartMessage(int rounds, int delay);

	/**
	 * Returns a message containing the device configurations
	 * 
	 * If message contains newline characters ('\n') the message is split up and
	 * sent one by one.
	 */
	String createSetMessage(IDroplet droplet);

	/**
	 * Returns a message for sending an ON signal to a device
	 */
	String createDeviceOffMessage(int deviceNumber);

	/**
	 * Returns a message for sending an OFF signal to a device
	 */
	String createDeviceOnMessage(int deviceNumber);

	/**
	 * Returns a message to retrieve the current device configurations
	 */
	String createInfoMessage();

	/**
	 * Returns a message to reset the microcontroller
	 */
	String createResetMessage();

	/**
	 * Returns a message to cancel the current execution on the microcontroller
	 */
	String createCancelMessage();

}
