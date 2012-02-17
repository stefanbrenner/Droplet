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

	public static final String PROPERTY_NAME = "name"; //$NON-NLS-1$

	public static final String PROPERTY_DESCRIPTION = "description"; //$NON-NLS-1$

	public static final String PROPERTY_ROUNDS = "rounds"; //$NON-NLS-1$

	public static final String PROPERTY_ROUND_DELAY = "roundDelay"; //$NON-NLS-1$

	public static final String ASSOCIATION_DEVICES = "devices"; //$NON-NLS-1$

	void setName(String name);

	String getName();

	void setDescription(String description);

	String getDescription();

	<T extends IDevice> List<T> getDevices(Class<T> type);

	void setDevices(List<IDevice> devices);

	List<IDevice> getDevices();

	void addDevice(IDevice device);

	void removeDevice(IDevice device);

	void reset();

	DropletDeviceComparator getDeviceComparator();

	Integer getRounds();

	void setRounds(Integer rounds);

	Integer getRoundDelay();

	void setRoundDelay(Integer delay);

}