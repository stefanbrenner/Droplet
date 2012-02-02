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
package com.stefanbrenner.droplet.model;

import java.util.List;

import com.stefanbrenner.droplet.service.DropletDeviceComparator;

/**
 * @author Stefan Brenner
 */
public interface IDroplet extends INotificationSupport {

	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_DESCRIPTION = "description";

	public static final String ASSOCIATION_DEVICES = "devices";

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract <T extends IDevice> List<T> getDevices(Class<T> type);

	public abstract void setDevices(List<IDevice> devices);

	public abstract List<IDevice> getDevices();

	public abstract void addDevice(IDevice device);

	public abstract void removeDevice(IDevice device);

	public abstract void reset();

	public abstract DropletDeviceComparator getDeviceComparator();

}