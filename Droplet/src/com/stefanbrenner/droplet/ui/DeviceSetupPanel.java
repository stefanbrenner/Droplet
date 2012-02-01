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
package com.stefanbrenner.droplet.ui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

public class DeviceSetupPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private IDroplet droplet;

	private final JPanel container;

	/**
	 * Create the panel.
	 */
	public DeviceSetupPanel(IDroplet droplet) {

		setLayout(new BorderLayout());

		container = new JPanel();

		// configure ui appearance and behavior
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		container.setBorder(BorderFactory.createTitledBorder("Device Setup"));
		container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		// resize vertical scrollbar
		scrollPane.getHorizontalScrollBar().putClientProperty("JComponent.sizeVariant", "mini");
		add(scrollPane, BorderLayout.CENTER);

		// set model object
		setDroplet(droplet);

	}

	private void updatePanels() {
		// remove previous components
		container.removeAll();
		// add components
		container.add(Box.createRigidArea(new Dimension(10, 0)));
		// add valve panels
		for (IValve valve : droplet.getValves()) {
			ActionDevicePanel<?> valvePanel = new ActionDevicePanel(droplet, valve);
			container.add(valvePanel);
			container.add(Box.createRigidArea(new Dimension(10, 0)));
		}
		// add flash panels
		for (IFlash flash : droplet.getFlashes()) {
			ActionDevicePanel flashPanel = new ActionDevicePanel(droplet, flash);
			container.add(flashPanel);
			container.add(Box.createRigidArea(new Dimension(10, 0)));
		}
		// add camera panels
		for (ICamera camera : droplet.getCameras()) {
			ActionDevicePanel cameraPanel = new ActionDevicePanel(droplet, camera);
			container.add(cameraPanel);
			container.add(Box.createRigidArea(new Dimension(10, 0)));
		}

		container.add(Box.createHorizontalGlue());

		container.revalidate();
		container.repaint();
	}

	public IDroplet getDroplet() {
		return droplet;
	}

	private PropertyChangeListener updateListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updatePanels();
		}
	};

	public void setDroplet(IDroplet droplet) {

		unregisterListener();
		this.droplet = droplet;
		registerListener();

		updatePanels();
	}

	private void registerListener() {
		if (droplet != null) {
			droplet.addPropertyChangeListener(IDroplet.ASSOCIATION_VALVES, updateListener);
			droplet.addPropertyChangeListener(IDroplet.ASSOCIATION_FLASHES, updateListener);
			droplet.addPropertyChangeListener(IDroplet.ASSOCIATION_CAMERAS, updateListener);
		}
	}

	private void unregisterListener() {
		if (droplet != null) {
			droplet.removePropertyChangeListener(updateListener);
		}
	}

}
