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

import javax.swing.Box;
import javax.swing.JComponent;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDropletContext;

/**
 * @author Stefan Brenner
 * 
 */
public class ActionDevicePanel extends DevicePanel<IActionDevice> {
	
	private static final long serialVersionUID = 1L;
	
	public ActionDevicePanel(final JComponent parent, final IDropletContext dropletContext,
			final IActionDevice device) {
		super(parent, dropletContext, device);
		
		updateActionsPanel();
		initializeListeners();
	}
	
	@Override
	void initializeListeners() {
		super.initializeListeners();
		
		device.addPropertyChangeListener(IActionDevice.ASSOCIATION_ACTIONS, e -> {
			updateActionsPanel();
		});
	}
	
	private void updateActionsPanel() {
		// remove all components
		actionsPanel.removeAll();
		// add components for each action
		for (IAction action : device.getActions()) {
			ActionPanel<IAction> valveActionPanel = new ActionPanel<IAction>(device, action);
			actionsPanel.add(valveActionPanel);
		}
		// add fill
		actionsPanel.add(Box.createVerticalGlue());
		// redraw panel
		actionsPanel.revalidate();
		actionsPanel.repaint();
		// update parent if we add the first action or remove the last action
		if (device.getActions().size() < 2) {
			parent.revalidate();
			parent.repaint();
		}
	}
	
}
