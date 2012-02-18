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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mangosdk.spi.ProviderFor;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDurationAction;
import com.stefanbrenner.droplet.model.internal.Camera;
import com.stefanbrenner.droplet.model.internal.Flash;
import com.stefanbrenner.droplet.model.internal.Valve;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;

/**
 * This class implements the Droplet Serial Communication Protocol (see
 * doc/dsc-protocol.txt for more information).
 * 
 * @author Stefan Brenner
 */
@ProviderFor(IDropletMessageProtocol.class)
public class DropletMessageProtocol implements IDropletMessageProtocol {

	// meta characters
	public static final String FIELD_SEPARATOR = ";"; //$NON-NLS-1$
	public static final String DEVICE_SEPARATOR = "^"; //$NON-NLS-1$
	public static final String TIME_SEPARATOR = "|"; //$NON-NLS-1$

	// commands
	public static final String COMMAND_RELEASE = "R"; //$NON-NLS-1$
	public static final String COMMAND_SEND = "S"; //$NON-NLS-1$
	public static final String COMMAND_INFO = "I"; //$NON-NLS-1$

	// devices
	public static final String DEVICE_VALVE = "V"; //$NON-NLS-1$
	public static final String DEVICE_FLASH = "F"; //$NON-NLS-1$
	public static final String DEVICE_CAMERA = "C"; //$NON-NLS-1$

	// mapping for devices and shortcuts
	public static final HashMap<Class<?>, String> DEVICE_SHORTS = new HashMap<Class<?>, String>();

	static {
		DEVICE_SHORTS.put(Valve.class, DEVICE_VALVE);
		DEVICE_SHORTS.put(Flash.class, DEVICE_FLASH);
		DEVICE_SHORTS.put(Camera.class, DEVICE_CAMERA);
	}

	@Override
	public String getName() {
		return "Droplet Serial Communication Protocol";
	}

	@Override
	public String createStartMessage() {
		return createStartMessage(1, 0);
	}

	@Override
	public String createStartMessage(int rounds, int delay) {
		String result = COMMAND_RELEASE + FIELD_SEPARATOR + rounds;

		if (rounds > 1 && delay > 0) {
			result += FIELD_SEPARATOR + delay;
		}

		return result;
	}

	@Override
	public String createSetMessage(IDroplet droplet) {

		String result = COMMAND_SEND;

		String devices = marshalDevices(droplet);
		if (StringUtils.isNotBlank(devices)) {
			result += FIELD_SEPARATOR + devices;
		}

		return result;
	}

	private String marshalDevices(IDroplet droplet) {
		String result = StringUtils.EMPTY;
		Map<Class<? extends IDevice>, Integer> counters = new HashMap<Class<? extends IDevice>, Integer>();

		for (IActionDevice d : droplet.getDevices(IActionDevice.class)) {

			Class<? extends IActionDevice> deviceClass = d.getClass();

			// get counter for device
			Integer counter = counters.get(deviceClass);

			// add counter for new device
			if (counter == null) {
				counter = 0;

			}

			// update counter
			counter++;
			counters.put(deviceClass, counter);

			// don't add device with no actions defined
			List<IAction> enabledActions = d.getEnabledActions();
			if (enabledActions.isEmpty()) {
				continue;
			}

			result += DEVICE_SHORTS.get(deviceClass) + counter;

			for (IAction a : enabledActions) {
				result += FIELD_SEPARATOR + a.getOffset();
				if (a instanceof IDurationAction) {
					result += TIME_SEPARATOR + ((IDurationAction) a).getDuration();
				}
			}

			result += DEVICE_SEPARATOR;
		}

		return result;
	}

	@Override
	public String createInfoMessage() {
		return COMMAND_INFO;
	}

	@Override
	public String createResetMessage() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String createCancelMessage() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String createDeviceOffMessage(IDroplet droplet, IDevice device) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public String createDeviceOnMessage(IDroplet droplet, IDevice device) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
