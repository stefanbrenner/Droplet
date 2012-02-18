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

import org.apache.commons.lang3.StringUtils;
import org.mangosdk.spi.ProviderFor;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDurationAction;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;

/**
 * Simple message protocol for sketch_droplet_v0_2.
 * 
 * @author Stefan Brenner
 */
@ProviderFor(IDropletMessageProtocol.class)
public class SimpleMessageProtocol implements IDropletMessageProtocol {

	@Override
	public String getName() {
		return "Simple Message Protocol";
	}

	@Override
	public String createStartMessage() {
		return "run"; //$NON-NLS-1$
	}

	@Override
	public String createStartMessage(int rounds, int delay) {
		return createStartMessage();
	}

	@Override
	public String createSetMessage(IDroplet droplet) {
		String message = "set"; //$NON-NLS-1$

		IValve valve1 = droplet.getDevices(IValve.class).get(0);

		IDurationAction action1 = (IDurationAction) valve1.getActions().get(0);

		int dur1 = action1.getDuration();

		IDurationAction action2 = (IDurationAction) valve1.getActions().get(1);

		int del1 = Math.abs(action2.getOffset() - action1.getOffset());
		int dur2 = action2.getDuration();

		IDurationAction action3 = (IDurationAction) valve1.getActions().get(2);

		int del2 = Math.abs(action3.getOffset() - action2.getOffset());
		int dur3 = action3.getDuration();

		int delCam = Math.abs(droplet.getDevices(ICamera.class).get(0).getActions().get(0).getOffset()
				- action3.getOffset());

		message += StringUtils.leftPad(String.valueOf(dur1), 3, "0"); //$NON-NLS-1$
		message += StringUtils.leftPad(String.valueOf(del1), 3, "0"); //$NON-NLS-1$
		message += StringUtils.leftPad(String.valueOf(dur2), 3, "0"); //$NON-NLS-1$
		message += StringUtils.leftPad(String.valueOf(del2), 3, "0"); //$NON-NLS-1$
		message += StringUtils.leftPad(String.valueOf(dur3), 3, "0"); //$NON-NLS-1$
		message += StringUtils.leftPad(String.valueOf(delCam), 3, "0"); //$NON-NLS-1$

		return message + "\n"; //$NON-NLS-1$
	}

	@Override
	public String createInfoMessage() {
		return "show"; //$NON-NLS-1$
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
