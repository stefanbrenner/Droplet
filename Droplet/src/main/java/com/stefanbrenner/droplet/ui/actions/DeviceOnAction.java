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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;

/**
 * Action to send a ON message to device.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class DeviceOnAction extends AbstractSerialAction {
	
	private final IDevice device;
	
	public DeviceOnAction(final JFrame frame, final IDropletContext dropletContext, final IDevice device) {
		super(frame, dropletContext, Messages.getString("DeviceOnAction.title")); //$NON-NLS-1$
		this.device = device;
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		IDropletMessageProtocol messageProtocolProvider = Configuration.getMessageProtocolProvider();
		
		String message = messageProtocolProvider.createDeviceOnMessage(getDroplet(), device);
		serialCommProvider.sendData(message);
	}
	
}
