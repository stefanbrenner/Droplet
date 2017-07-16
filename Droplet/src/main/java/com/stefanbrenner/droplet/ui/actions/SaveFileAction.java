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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.UiUtils;
import com.stefanbrenner.droplet.xml.JAXBHelper;

/**
 * Action to save a droplet configuration.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class SaveFileAction extends AbstractDropletAction {
	
	private final JFileChooser fileChooser;
	
	public SaveFileAction(final JFrame frame, final JFileChooser fileChooser, final IDropletContext dropletContext) {
		this(Messages.getString("SaveFileAction.title"), frame, fileChooser, dropletContext); //$NON-NLS-1$
		
		putValue(Action.ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_S));
		putValue(Action.MNEMONIC_KEY, UiUtils.getMnemonic(Messages.getString("SaveFileAction.mnemonic"))); //$NON-NLS-1$
		putValue(Action.SHORT_DESCRIPTION, Messages.getString("SaveFileAction.description")); //$NON-NLS-1$
	}
	
	public SaveFileAction(final String name, final JFrame frame, final JFileChooser fileChooser,
			final IDropletContext dropletContext) {
		super(frame, dropletContext, name);
		this.fileChooser = fileChooser;
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		File file = getDropletContext().getFile();
		if (file == null) {
			showFileChooser();
		} else {
			saveFile(file);
		}
	}
	
	protected void showFileChooser() {
		int returnVal = fileChooser.showSaveDialog(getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
			// Get the selected file
			File file = fileChooser.getSelectedFile();
			
			// check if file extension fits
			if (StringUtils.containsIgnoreCase(file.getName(), ".") //$NON-NLS-1$
					&& !(StringUtils.endsWithIgnoreCase(file.getName(),
							"." + IDropletContext.DROPLET_FILE_EXTENSION))) {
				JOptionPane.showMessageDialog(getFrame(), Messages.getString("SaveFileAction.extensionNotAllowed"), //$NON-NLS-1$
						Messages.getString("SaveFileAction.wrongExtension"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
				showFileChooser();
				return;
			} else { // automatically add droplet file extension
				if (!StringUtils.endsWithIgnoreCase(file.getName(), "." + IDropletContext.DROPLET_FILE_EXTENSION)) {
					String newPath = StringUtils.join(file.getPath(), "." + IDropletContext.DROPLET_FILE_EXTENSION);
					file = new File(newPath);
				}
			}
			
			// check if file already exists
			if (file.exists()) {
				int retVal = JOptionPane.showConfirmDialog(getFrame(),
						Messages.getString("SaveFileAction.overwriteFile"), Messages.getString("SaveFileAction.1"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				if (retVal == JOptionPane.NO_OPTION) {
					showFileChooser();
					return;
				}
			}
			
			saveFile(file);
			
			// set file to context
			getDropletContext().setFile(file);
			
		}
	}
	
	protected void saveFile(final File file) {
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
