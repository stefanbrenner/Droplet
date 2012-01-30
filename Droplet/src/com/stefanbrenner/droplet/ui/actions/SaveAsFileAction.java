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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class SaveAsFileAction extends SaveFileAction {

	private final JComponent parent;
	private final JFileChooser fileChooser;

	public SaveAsFileAction(JComponent parent, JFileChooser fileChooser) {
		super("Save As...");

		this.fileChooser = fileChooser;
		this.parent = parent;

		putValue(SHORT_DESCRIPTION, "Save Droplet Configuration in new file");

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int returnVal = fileChooser.showSaveDialog(parent);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// Get the selected file
			File file = fileChooser.getSelectedFile();

			// TODO brenner: save conf file

			System.out.println("Opening: " + file.getName());
		} else {
			System.out.println("Open command cancelled by user");
		}
	}

};
