/*****************************************************************************
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
 *****************************************************************************/
package com.stefanbrenner.droplet.model;

import gnu.io.CommPortIdentifier;

import java.io.File;

/**
 * Interface that encapsulates the droplet context and it's base objects.
 * 
 * @author Stefan Brenner
 */
public interface IDropletContext extends INotificationSupport {

	/** The droplet file extension. */
	String DROPLET_FILE_EXTENSION = "drp"; //$NON-NLS-1$

	/** Property name for the serial port. */
	String PROPERTY_PORT = "port"; //$NON-NLS-1$

	/** Property name for the droplet save file. */
	String PROPERTY_FILE = "file"; //$NON-NLS-1$

	/** Property name for the droplet instance. */
	String PROPERTY_DROPLET = "droplet"; //$NON-NLS-1$

	/** Property name for the amount of rounds. */
	String PROPERTY_ROUNDS = "rounds"; //$NON-NLS-1$

	/** Property name for the round delay. */
	String PROPERTY_ROUND_DELAY = "roundDelay"; //$NON-NLS-1$

	/** Property name for the logging messages. */
	String PROPERTY_LOGGING = "loggingMessages"; //$NON-NLS-1$

	/**
	 * @param port
	 *            port that should be used for serial communication
	 */
	void setPort(CommPortIdentifier port);

	/**
	 * @return the port to be used for serial communication.
	 */
	CommPortIdentifier getPort();

	/**
	 * @param file
	 *            file in which the droplet configurations should be saved
	 */
	void setFile(File file);

	/**
	 * @return the file that is used to save the droplet setup.
	 */
	File getFile();

	/**
	 * @param droplet
	 *            {@link IDroplet} to be used in the context
	 */
	void setDroplet(IDroplet droplet);

	/**
	 * @return the droplet instance of the context
	 */
	IDroplet getDroplet();

	/**
	 * @param message
	 *            logging message to be added
	 */
	void addLoggingMessage(String message);

	/**
	 * @return all logging messages in one single string
	 */
	String getLoggingMessages();

	/**
	 * Clear all logging messages.
	 */
	void clearLoggingMessages();

	/**
	 * @param lastSetMessage
	 *            last message that was sent by the serial communication service
	 */
	void setLastSetMessage(String lastSetMessage);

	/**
	 * @return last message that was sent by the serial communication service
	 */
	String getLastSetMessage();

	/**
	 * @return number of rounds to execute
	 */
	Integer getRounds();

	/**
	 * @param rounds
	 *            number of rounds to execute
	 */
	void setRounds(Integer rounds);

	/**
	 * @return round delay in milliseconds
	 */
	Integer getRoundDelay();

	/**
	 * @param delay
	 *            round delay in milliseconds
	 */
	void setRoundDelay(Integer delay);

}
