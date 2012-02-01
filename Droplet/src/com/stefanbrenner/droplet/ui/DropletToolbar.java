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

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.SendAction;
import com.stefanbrenner.droplet.ui.actions.ShowAction;
import com.stefanbrenner.droplet.ui.actions.StartAction;

// TODO brenner: use JToolBar
public class DropletToolbar extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DropletToolbar(JFrame frame, IDropletContext dropletContext) {

		setLayout(new FlowLayout(FlowLayout.RIGHT));

		// show button
		JButton btnShow = new JButton(new ShowAction(frame, dropletContext));
		add(btnShow);

		// send button
		JButton btnSend = new JButton(new SendAction(frame, dropletContext));
		add(btnSend);

		// start button
		JButton btnStart = new JButton(new StartAction(frame, dropletContext));
		add(btnStart);

	}

}
