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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.UiUtils;
import com.stefanbrenner.droplet.xml.JAXBHelper;

/**
 * Action to open a droplet configuration from a file.
 *
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class OpenFileAction extends AbstractDropletAction {

	private final JFileChooser fileChooser;

	public OpenFileAction(final JFrame frame, final JFileChooser fileChooser, final IDropletContext dropletContext) {
		this(frame, fileChooser, dropletContext, Messages.getString("OpenFileAction.title")); //$NON-NLS-1$
	}

	protected OpenFileAction(final JFrame frame, final JFileChooser fileChooser, final IDropletContext dropletContext,
			final String title) {
		super(frame, dropletContext, title);

		this.fileChooser = fileChooser;

		putValue(Action.ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_O));
		putValue(Action.MNEMONIC_KEY, UiUtils.getMnemonic(Messages.getString("OpenFileAction.mnemonic"))); //$NON-NLS-1$
		putValue(Action.SHORT_DESCRIPTION, Messages.getString("OpenFileAction.description")); //$NON-NLS-1$
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		open(false);
	}

	protected void open(final boolean asTemplate) {
		int returnVal = fileChooser.showOpenDialog(getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();

				if (!file.exists()) {
					JOptionPane.showMessageDialog(getFrame(), Messages.getString("SaveFileAction.fileNotFound"), //$NON-NLS-1$
							Messages.getString("SaveFileAction.fileNotFound"), //$NON-NLS-1$
							JOptionPane.ERROR_MESSAGE);
					open(false);
					return;
				}

				BufferedReader in = new BufferedReader(new FileReader(file));
				String xml = IOUtils.toString(in);
				in.close();

				JAXBHelper jaxbHelper = new JAXBHelper();
				IDroplet droplet = jaxbHelper.fromXml(xml, IDroplet.class);

				if (asTemplate) {
					// reset droplet
					droplet.reset();
					getDropletContext().setFile(null);
				} else {
					getDropletContext().setFile(file);
				}

				// set droplet to context
				getDropletContext().setDroplet(droplet);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

};
