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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;

/**
 * Panel to setup one droplet device.
 * 
 * @author Stefan Brenner
 */
public class DeviceSetupPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private IDropletContext dropletContext;
	private IDroplet droplet;
	
	private final JPanel container;
	
	/**
	 * Create the panel.
	 */
	public DeviceSetupPanel(final JFrame frame, final IDropletContext dropletContext) {
		
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(300, 200));
		
		container = new JPanel();
		
		// configure ui appearance and behavior
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		container.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		container.setBackground(Color.LIGHT_GRAY);
		
		JScrollPane scrollPane = new JScrollPane(container, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		// resize vertical scrollbar
		scrollPane.getHorizontalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		add(scrollPane, BorderLayout.CENTER);
		
		// add toolbar
		JToolBar toolbar = new DropletDevicesToolbar(frame, dropletContext);
		add(toolbar, BorderLayout.WEST);
		
		// set model object
		setDropletContext(dropletContext);
		
		// // add listener
		dropletContext.addPropertyChangeListener(IDropletContext.PROPERTY_DROPLET,
				e -> setDropletContext(dropletContext));
		
	}
	
	private void updatePanels() {
		// remove previous components
		container.removeAll();
		
		// add devices panels
		droplet.getDevices().stream().sorted(droplet.getDeviceComparator()).forEach(this::addDevice);
		
		container.add(Box.createHorizontalGlue());
		
		container.revalidate();
		container.repaint();
		
	}
	
	private void addDevice(final IDevice device) {
		DevicePanel<?> devicePanel;
		
		if (device instanceof IActionDevice) {
			devicePanel = new ActionDevicePanel(this, dropletContext, (IActionDevice) device);
		} else {
			devicePanel = new DevicePanel<IDevice>(this, dropletContext, device);
		}
		
		container.add(devicePanel);
		container.add(Box.createRigidArea(new Dimension(10, 0)));
	}
	
	public void setDropletContext(final IDropletContext dropletContext) {
		
		unregisterListener();
		this.dropletContext = dropletContext;
		this.droplet = dropletContext.getDroplet();
		registerListener();
		
		updatePanels();
	}
	
	private final PropertyChangeListener updateListener = e -> updatePanels();
	
	private void registerListener() {
		if (droplet != null) {
			droplet.addPropertyChangeListener(IDroplet.ASSOCIATION_DEVICES, updateListener);
		}
	}
	
	private void unregisterListener() {
		if (droplet != null) {
			droplet.removePropertyChangeListener(updateListener);
		}
	}
	
}
