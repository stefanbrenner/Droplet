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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.utils.Messages;

/**
 * @author Stefan Brenner
 * 
 */
public class ActionDevicePanel extends DevicePanel<IActionDevice> {
	
	private static final long serialVersionUID = 1L;
	
	public ActionDevicePanel(JComponent parent, IDroplet droplet, IActionDevice device) {
		super(parent, droplet, device);
		
		updateActionsPanel();
		initializeListeners();
	}
	
	void createAddButton(JPanel panel) {
		// add button
		JButton btnAdd = new JButton(Messages.getString("ActionDevicePanel.addAction")); //$NON-NLS-1$
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent action) {
				addAction(getDevice().createNewAction());
			}
		});
		btnAdd.setFocusable(false);
		panel.add(btnAdd);
	}
	
	private void initializeListeners() {
		device.addPropertyChangeListener(IActionDevice.ASSOCIATION_ACTIONS, new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				updateActionsPanel();
			}
		});
	}
	
	private void addAction(final IAction action) {
		device.addAction(action);
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
