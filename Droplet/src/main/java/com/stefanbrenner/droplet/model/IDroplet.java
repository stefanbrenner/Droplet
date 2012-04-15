/***************************************************************************
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
 ***************************************************************************/
package com.stefanbrenner.droplet.model;

import java.util.List;

import com.stefanbrenner.droplet.service.impl.DropletDeviceComparator;

/**
 * Interface for the base droplet object that contains all devices.
 * 
 * @author Stefan Brenner
 */
public interface IDroplet extends INotificationSupport {
	
	/** Property name for the droplet name. */
	String PROPERTY_NAME = "name"; //$NON-NLS-1$
	
	/** Property name for the droplet description. */
	String PROPERTY_DESCRIPTION = "description"; //$NON-NLS-1$
	
	/** Association name for the droplet actions. */
	String ASSOCIATION_DEVICES = "devices"; //$NON-NLS-1$
	
	/**
	 * @param name
	 *            of droplet
	 */
	void setName(String name);
	
	/**
	 * @return the name of droplet
	 */
	String getName();
	
	/**
	 * @param description
	 *            of droplet
	 */
	void setDescription(String description);
	
	/**
	 * @return the description of droplet
	 */
	String getDescription();
	
	/**
	 * @param <T>
	 *            device type
	 * @param type
	 *            device type
	 * @return all devices with a specific type
	 */
	<T extends IDevice> List<T> getDevices(Class<T> type);
	
	/**
	 * @param devices
	 *            list of all droplet devices
	 */
	void setDevices(List<IDevice> devices);
	
	/**
	 * @return list of all droplet devices
	 */
	List<IDevice> getDevices();
	
	/**
	 * @param device
	 *            to be added
	 */
	void addDevice(IDevice device);
	
	/**
	 * @param device
	 *            to be removed
	 */
	void removeDevice(IDevice device);
	
	/**
	 * Reset droplet and its devices to the default values.
	 */
	void reset();
	
	/**
	 * @return {@link DropletDeviceComparator} that is used to sort devices
	 */
	DropletDeviceComparator getDeviceComparator();
	
}
