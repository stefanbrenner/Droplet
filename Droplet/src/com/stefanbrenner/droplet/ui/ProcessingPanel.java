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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProcessingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ProcessingPanel() {

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Processing"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// checkbox
		JCheckBox cbEnable = new JCheckBox("Enable Processing");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		// gbc.gridwidth = GridBagConstraints.RELATIVE;
		add(cbEnable, gbc);

		// watch folder label
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(new JLabel("Watch Folder"), gbc);

		// watch folder textbox
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		JTextField txtWatchFolder = new JTextField(
				"/Users/Stefan/Pictures/AutomatedImport");
		txtWatchFolder.setEnabled(false);
		add(txtWatchFolder, gbc);

		// watch folder button
		gbc.gridx = 2;
		gbc.gridy = 1;
		JButton btnWatchFolder = new JButton("Browse...");
		add(btnWatchFolder, gbc);

		// tag label
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(new JLabel("Tags"), gbc);

		// tag textbox
		gbc.gridx = 1;
		gbc.gridy = 2;
		JTextArea txtTag = new JTextArea(4, 20);
		txtTag.setMargin(new Insets(5, 5, 5, 5));
		txtTag.setLineWrap(true);
		JScrollPane tagScrollPane = new JScrollPane(txtTag);
		add(tagScrollPane, gbc);

		// comments label
		gbc.gridx = 3;
		gbc.gridy = 2;
		add(new JLabel("Comments"), gbc);

		// comments textbox
		gbc.gridx = 4;
		gbc.gridy = 2;
		JTextArea txtComments = new JTextArea(4, 20);
		txtComments.setMargin(new Insets(5, 5, 5, 5));
		txtComments.setLineWrap(true);
		JScrollPane commentsScrollPane = new JScrollPane(txtComments);
		add(commentsScrollPane, gbc);

		// checkbox
		JCheckBox cbWriteMetaData = new JCheckBox("Write Metadata to EXIF");
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(cbWriteMetaData, gbc);

	}

}
