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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import org.apache.commons.io.IOUtils;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.UiUtils;
import com.stefanbrenner.droplet.xml.JAXBHelper;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class OpenFileAction extends AbstractDropletAction {

	private final JFileChooser fileChooser;

	public OpenFileAction(JComponent parent, JFileChooser fileChooser, IDropletContext dropletContext) {
		super(parent, dropletContext, "Open...");

		this.fileChooser = fileChooser;

		putValue(ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_O));
		putValue(SHORT_DESCRIPTION, "Open Droplet Configuration");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		int returnVal = fileChooser.showOpenDialog(getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();

				BufferedReader in = new BufferedReader(new FileReader(file));
				String xml = IOUtils.toString(in);
				in.close();

				JAXBHelper jaxbHelper = new JAXBHelper();
				IDroplet droplet = jaxbHelper.fromXml(xml, IDroplet.class);

				// set droplet to context
				getDropletContext().setDroplet(droplet);
				getDropletContext().setFile(file);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

};
