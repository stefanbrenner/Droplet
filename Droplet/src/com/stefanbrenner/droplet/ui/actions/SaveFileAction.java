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
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.UiUtils;
import com.stefanbrenner.droplet.xml.JAXBHelper;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class SaveFileAction extends AbstractDropletAction {

	private final JComponent parent;
	private final JFileChooser fileChooser;

	public SaveFileAction(JComponent parent, JFileChooser fileChooser, IDropletContext dropletContext) {
		this("Save", parent, fileChooser, dropletContext);

		putValue(ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_S));
		putValue(SHORT_DESCRIPTION, "Save Droplet Configuration");
	}

	public SaveFileAction(String name, JComponent parent, JFileChooser fileChooser, IDropletContext dropletContext) {
		super(dropletContext, name);
		this.parent = parent;
		this.fileChooser = fileChooser;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		File file = getDropletContext().getFile();
		if (file == null) {
			showFileChooser();
		} else {
			saveFile(file);
		}
	}

	protected void showFileChooser() {
		int returnVal = fileChooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// Get the selected file
			File file = fileChooser.getSelectedFile();

			// add file to context
			getDropletContext().setFile(file);

			// TODO brenner: automatically add file extension
			// TODO brenner: warn before overwrite
			saveFile(file);

		}
	}

	protected void saveFile(File file) {
		try {

			IDroplet droplet = getDropletContext().getDroplet();

			JAXBHelper jaxbHelper = new JAXBHelper();
			String xml = jaxbHelper.toXml(droplet);

			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(xml);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

};
