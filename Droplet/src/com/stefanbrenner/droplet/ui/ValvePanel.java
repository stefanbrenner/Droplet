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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.model.internal.Valve;

// TODO rename to ActionPanel?
public class ValvePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// UI components
	private final JLabel lblTitle;

	private List<ValveActionPanel> valveActions = new ArrayList<ValveActionPanel>();

	/**
	 * Create the panel.
	 */
	public ValvePanel() {
		setLayout(new BorderLayout(10, 0));

		Valve valve1 = new Valve();
		valve1.setName("MV 1");
		BeanAdapter<IValve> adapter = new BeanAdapter<IValve>(valve1);
		JLabel createLabel = BasicComponentFactory.createLabel(adapter.getValueModel("name"));
		add(createLabel, BorderLayout.CENTER);

		// title label
		lblTitle = new JLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitle.setPreferredSize(new Dimension(100, 25));
		add(lblTitle, BorderLayout.NORTH);

		// add button
		JButton btnAdd = new JButton("Add");
		add(btnAdd, BorderLayout.SOUTH);

	}

	public void setTitle(String title) {
		lblTitle.setText(title);
	}

}
