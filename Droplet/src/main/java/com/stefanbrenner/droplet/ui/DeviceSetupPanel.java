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
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;

/**
 * Panel to setup one droplet device.
 * 
 * @author Stefan Brenner
 */
public class DeviceSetupPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private IDroplet droplet;
	
	private final JPanel container;
	
	/**
	 * Create the panel.
	 */
	public DeviceSetupPanel(final IDroplet droplet) {
		
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(Short.MIN_VALUE, 200));
		
		container = new JPanel();
		
		// configure ui appearance and behavior
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		container.setBorder(BorderFactory.createTitledBorder(Messages.getString("DeviceSetupPanel.title"))); //$NON-NLS-1$
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JScrollPane scrollPane = new JScrollPane(container, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		// resize vertical scrollbar
		scrollPane.getHorizontalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		add(scrollPane, BorderLayout.CENTER);
		
		// set model object
		setDroplet(droplet);
		
	}
	
	private void updatePanels() {
		// remove previous components
		container.removeAll();
		// add components
		container.add(Box.createRigidArea(new Dimension(10, 0)));
		// add devices panels
		List<IDevice> devices = droplet.getDevices(IDevice.class);
		// sort devices
		Collections.sort(devices, droplet.getDeviceComparator());
		for (IDevice device : devices) {
			DevicePanel<?> devicePanel;
			
			if (device instanceof IActionDevice) {
				devicePanel = new ActionDevicePanel(this, droplet, (IActionDevice) device);
			} else {
				devicePanel = new DevicePanel<IDevice>(this, droplet, device);
			}
			
			container.add(devicePanel);
			container.add(Box.createRigidArea(new Dimension(10, 0)));
		}
		
		container.add(Box.createHorizontalGlue());
		
		container.revalidate();
		container.repaint();
		
	}
	
	public IDroplet getDroplet() {
		return droplet;
	}
	
	private final PropertyChangeListener updateListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			updatePanels();
		}
	};
	
	public void setDroplet(final IDroplet droplet) {
		
		unregisterListener();
		this.droplet = droplet;
		registerListener();
		
		updatePanels();
	}
	
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
