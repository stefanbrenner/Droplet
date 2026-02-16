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
package com.stefanbrenner.droplet.ui;

import java.awt.AWTEvent;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;
import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.Messages;

/**
 * Droplet toolbar containing the most useful actions.
 * 
 * @author Stefan Brenner
 */
public class DevicePanelToolbar extends JToolBar {
	
	private static final long serialVersionUID = 1L;
	
	private final IDropletContext dropletContext;
	private final IDevice device;
	
	private final JToggleButton btnOff;
	
	private final FontIcon offIcon = FontIcon.of(FontAwesome.POWER_OFF, 16, DropletColors.LIGHT_GRAY);
	private final FontIcon offIconHover = FontIcon.of(FontAwesome.POWER_OFF, 16, DropletColors.WHITE);
	private final FontIcon onIcon = FontIcon.of(FontAwesome.POWER_OFF, 16, Color.GREEN);
	
	// ui components
	
	/**
	 * Create the panel.
	 */
	public DevicePanelToolbar(final IDropletContext dropletContext, final IDevice device) {
		
		this.dropletContext = dropletContext;
		this.device = device;
		
		setOrientation(JToolBar.HORIZONTAL);
		setFloatable(false);
		setRollover(true);
		setBackground(DropletColors.DARK_GRAY);
		
		// add device button for actiondevices
		if (device instanceof IActionDevice) {
			FontIcon iconAddAction = FontIcon.of(FontAwesome.PLUS, 16, DropletColors.LIGHT_GRAY);
			FontIcon iconAddActionHover = FontIcon.of(FontAwesome.PLUS, 16, DropletColors.WHITE);
			JButton btnAddAction = new JButton();
			btnAddAction.setIcon(iconAddAction);
			btnAddAction.setRolloverIcon(iconAddActionHover);
			btnAddAction.setBorderPainted(false);
			btnAddAction.addActionListener(e -> addAction());
			btnAddAction.setToolTipText(Messages.getString("ActionDevicePanel.addAction.tooltip"));
			add(btnAddAction);
		}
		
		// seperator
		add(Box.createHorizontalGlue());
		
		// on/off buttons
		
		btnOff = new JToggleButton(offIcon, false);
		btnOff.setToolTipText(Messages.getString("ActionDevicePanel.onOff.tooltip"));
		btnOff.addActionListener(e -> off());
		btnOff.setRolloverIcon(offIconHover);
		updateOffButton();
		add(btnOff);
		
		// seperator
		add(Box.createHorizontalGlue());
		
		// remove button
		FontIcon iconRemove = FontIcon.of(FontAwesome.TRASH, 16, DropletColors.LIGHT_GRAY);
		FontIcon iconRemoveHover = FontIcon.of(FontAwesome.TRASH, 16, DropletColors.WHITE);
		JButton btnRemove = new JButton();
		btnRemove.setIcon(iconRemove);
		btnRemove.setRolloverIcon(iconRemoveHover);
		btnRemove.setBorderPainted(false);
		btnRemove.addActionListener(this::removeDevice);
		btnRemove.setToolTipText(Messages.getString("ActionDevicePanel.removeDevice.tooltip"));
		add(btnRemove);
		
	}
	
	private void removeDevice(final AWTEvent e) {
		int retVal = JOptionPane.showConfirmDialog(this,
				Messages.getString("ActionDevicePanel.removeDevice", device.getName()), //$NON-NLS-1$ //$NON-NLS-2$
																						// //$NON-NLS-3$
				StringUtils.EMPTY, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (retVal == JOptionPane.YES_OPTION) {
			dropletContext.getDroplet().removeDevice(device);
		}
	}
	
	private void addAction() {
		((IActionDevice) device).createNewAction();
	}
	
	private void off() {
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		IDropletMessageProtocol messageProtocolProvider = Configuration.getMessageProtocolProvider();
		
		if (!btnOff.isSelected()) {
			String message = messageProtocolProvider.createDeviceOffMessage(dropletContext.getDroplet(), device);
			serialCommProvider.sendData(message);
		} else {
			String message = messageProtocolProvider.createDeviceOnMessage(dropletContext.getDroplet(), device);
			serialCommProvider.sendData(message);
		}
		
		updateOffButton();
	}
	
	private void updateOffButton() {
		if (btnOff.isSelected()) {
			btnOff.setIcon(onIcon);
		} else {
			btnOff.setIcon(offIcon);
		}
	}
	
}
