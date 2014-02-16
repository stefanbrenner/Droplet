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

/**
 * Base interface for all devices that can be used with droplet.
 * 
 * @author Stefan Brenner
 */
public interface IDevice extends INotificationSupport {
	
	/** Property name for the device number. */
	String PROPERTY_NUMBER = "number"; //$NON-NLS-1$
	
	/** Property name for the device name. */
	String PROPERTY_NAME = "name"; //$NON-NLS-1$
	
	/** Property name for the device description. */
	String PROPERTY_DESCRIPTION = "description"; //$NON-NLS-1$
	
	/**
	 * @return number of this device
	 */
	String getNumber();
	
	/**
	 * @param number
	 *            of this device
	 */
	void setNumber(String number);
	
	/**
	 * @return name of this device
	 */
	String getName();
	
	/**
	 * @param name
	 *            of this device
	 */
	void setName(String name);
	
	/**
	 * @param description
	 *            of this device
	 */
	void setDescription(String description);
	
	/**
	 * @return description of this device
	 */
	String getDescription();
	
	/**
	 * Reset all fields of this device to the default value.
	 */
	void reset();
	
}
