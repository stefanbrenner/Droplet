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

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ValveActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// UI components
	private final JSpinner spOffset;
	private final JSpinner spDuration;

	private SpinnerNumberModel spOffsetModel;
	private SpinnerNumberModel spDurationModel;

	/**
	 * Create the panel.
	 */
	public ValveActionPanel() {
		setLayout(new BorderLayout(0, 0));

		spOffset = new JSpinner();
		spOffsetModel = new SpinnerNumberModel(0, 0, 10000, 1);
		spOffset.setModel(spOffsetModel);
		add(spOffset, BorderLayout.WEST);

		spDuration = new JSpinner();
		spDurationModel = new SpinnerNumberModel(0, 0, 10000, 1);
		spDuration.setModel(spDurationModel);
		add(spDuration, BorderLayout.EAST);

	}

}
