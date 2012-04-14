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

	public static final int UNDEFINED_ORDER = 0;

	public static final int GREATER = 1;

	public static final int SMALLER = -1;

	/**
	 * List of nested comparators
	 */
	private List<DropletDeviceComparator> comparators = new ArrayList<DropletDeviceComparator>();

	/**
	 * Valve < Flash < Camera
	 */
	@Override
	public int compare(IDevice device1, IDevice device2) {
		if (device1 instanceof IValve) {
			if (device2 instanceof IValve) {
				return UNDEFINED_ORDER;
			}
			if (device2 instanceof IFlash || device2 instanceof ICamera) {
				return SMALLER;
			}
		}
		if (device1 instanceof IFlash) {
			if (device2 instanceof IFlash) {
				return UNDEFINED_ORDER;
			}
			if (device2 instanceof IValve) {
				return GREATER;
			}
			if (device2 instanceof ICamera) {
				return SMALLER;
			}
		}
		if (device1 instanceof ICamera) {
			if (device2 instanceof ICamera) {
				return UNDEFINED_ORDER;
			}
			if (device2 instanceof IValve || device2 instanceof IFlash) {
				return GREATER;
			}
		}
		// ask comparators for proper comparison, combine result of all
		// comparator results and calculate a weighted order
		int result = UNDEFINED_ORDER;
		for (DropletDeviceComparator comparator : comparators) {
			result += Math.signum(comparator.compare(device1, device2));
		}
		return (int) Math.signum(result);
	}

	public void registerComparator(DropletDeviceComparator comparator) {
		comparators.add(comparator);
	}

	public void unRegisterComparator(DropletDeviceComparator comparator) {
		comparators.remove(comparator);
	}

}
