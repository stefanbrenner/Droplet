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
package com.stefanbrenner.droplet.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

/**
 * Comparator for Droplet devices. Since we don't know all our Subclasses of
 * IDevice yet, we need some dynamic way to determine the absolute order of
 * devices. Therefore nested comparators can be registered and consulted.
 * 
 * @author Stefan Brenner
 */
public class DropletDeviceComparator implements Comparator<IDevice> {
	
	/** The order of the devices is undefined. */
	public static final int UNDEFINED_ORDER = 0;
	/** The device is greater. */
	public static final int GREATER = 1;
	/** The device is smaller. */
	public static final int SMALLER = -1;
	
	/**
	 * List of nested comparators.
	 */
	private final List<DropletDeviceComparator> comparators = new ArrayList<DropletDeviceComparator>();
	
	/**
	 * Valve < Flash < Camera.
	 * 
	 * @param device1
	 *            first device to compare to second device
	 * @param device2
	 *            second device to compare to first device
	 */
	@Override
	public int compare(final IDevice device1, final IDevice device2) {
		if (device1 instanceof IValve) {
			if (device2 instanceof IValve) {
				return DropletDeviceComparator.UNDEFINED_ORDER;
			}
			if ((device2 instanceof IFlash) || (device2 instanceof ICamera)) {
				return DropletDeviceComparator.SMALLER;
			}
		}
		if (device1 instanceof IFlash) {
			if (device2 instanceof IFlash) {
				return DropletDeviceComparator.UNDEFINED_ORDER;
			}
			if (device2 instanceof IValve) {
				return DropletDeviceComparator.GREATER;
			}
			if (device2 instanceof ICamera) {
				return DropletDeviceComparator.SMALLER;
			}
		}
		if (device1 instanceof ICamera) {
			if (device2 instanceof ICamera) {
				return DropletDeviceComparator.UNDEFINED_ORDER;
			}
			if ((device2 instanceof IValve) || (device2 instanceof IFlash)) {
				return DropletDeviceComparator.GREATER;
			}
		}
		// ask comparators for proper comparison, combine result of all
		// comparator results and calculate a weighted order
		int result = DropletDeviceComparator.UNDEFINED_ORDER;
		for (DropletDeviceComparator comparator : comparators) {
			result += Math.signum(comparator.compare(device1, device2));
		}
		return (int) Math.signum(result);
	}
	
	/**
	 * @param comparator
	 *            to be registered
	 */
	public void registerComparator(final DropletDeviceComparator comparator) {
		comparators.add(comparator);
	}
	
	/**
	 * @param comparator
	 *            to be unregistered
	 */
	public void unRegisterComparator(final DropletDeviceComparator comparator) {
		comparators.remove(comparator);
	}
	
}
