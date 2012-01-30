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

import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

public class ConfigurationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private IDroplet droplet;

	/**
	 * Create the panel.
	 */
	public ConfigurationPanel(IDroplet droplet) {

		// configure ui appearance and behavior
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createTitledBorder("Configuration"));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		// set model object
		setDroplet(droplet);

	}

	private void updatePanels() {
		// remove previous components
		removeAll();
		// add components
		add(Box.createRigidArea(new Dimension(10, 0)));
		// add valve panels
		for (IValve valve : droplet.getValves()) {
			ActionDevicePanel valvePanel = new ActionDevicePanel(valve);
			add(valvePanel);
			add(Box.createRigidArea(new Dimension(10, 0)));
		}
		// add flash panels
		for (IFlash flash : droplet.getFlashes()) {
			ActionDevicePanel flashPanel = new ActionDevicePanel(flash);
			add(flashPanel);
			add(Box.createRigidArea(new Dimension(10, 0)));
		}
		// add camera panels
		for (ICamera camera : droplet.getCameras()) {
			ActionDevicePanel cameraPanel = new ActionDevicePanel(camera);
			add(cameraPanel);
			add(Box.createRigidArea(new Dimension(10, 0)));
		}

		add(Box.createHorizontalGlue());

		revalidate();
		repaint();
	}

	public IDroplet getDroplet() {
		return droplet;
	}

	public void setDroplet(IDroplet droplet) {
		this.droplet = droplet;
		updatePanels();
	}

}
