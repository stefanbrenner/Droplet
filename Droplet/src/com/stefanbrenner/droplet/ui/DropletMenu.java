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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.AbstractDropletAction;
import com.stefanbrenner.droplet.ui.actions.AddDeviceAction;
import com.stefanbrenner.droplet.ui.actions.ExitAction;
import com.stefanbrenner.droplet.ui.actions.NewAction;
import com.stefanbrenner.droplet.ui.actions.OpenAsTemplateAction;
import com.stefanbrenner.droplet.ui.actions.OpenFileAction;
import com.stefanbrenner.droplet.ui.actions.PreferencesAction;
import com.stefanbrenner.droplet.ui.actions.SaveAsFileAction;
import com.stefanbrenner.droplet.ui.actions.SaveFileAction;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * @author Stefan Brenner
 */
public class DropletMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private final JFrame frame;

	// model object
	private final IDropletContext dropletContext;

	// menues
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu helpMenu;

	// menu items
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem openTemplateMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem addDeviceMenuItem;
	private JMenuItem preferencesMenuItem;
	private JMenuItem aboutMenuItem;

	// actions
	private AbstractDropletAction newAction;
	private AbstractDropletAction openAction;
	private AbstractDropletAction openTemplateAction;
	private AbstractDropletAction saveAction;
	private AbstractDropletAction saveAsAction;
	private AbstractDropletAction addDeviceAction;
	private AbstractDropletAction preferencesAction;
	private AbstractDropletAction exitAction;

	public DropletMenu(JFrame frame, IDropletContext dropletContext) {
		super();
		this.frame = frame;
		this.dropletContext = dropletContext;
		build();
	}

	private void build() {
		// file menu
		fileMenu = add(new JMenu(Messages.getString("DropletMenu.file"))); //$NON-NLS-1$
		fileMenu.setMnemonic(UiUtils.getMnemonic(Messages.getString("DropletMenu.fileMnemonic"))); //$NON-NLS-1$
		buildNewMenu();
		// edit menu
		editMenu = add(new JMenu(Messages.getString("DropletMenu.edit"))); //$NON-NLS-1$
		editMenu.setMnemonic(UiUtils.getMnemonic(Messages.getString("DropletMenu.editMnemonic"))); //$NON-NLS-1$
		buildEditMenu();
		// help menu
		helpMenu = add(new JMenu(Messages.getString("DropletMenu.help"))); //$NON-NLS-1$
		helpMenu.setMnemonic(UiUtils.getMnemonic(Messages.getString("DropletMenu.helpMnemonic"))); //$NON-NLS-1$
		buildHelpMenu();
	}

	private void buildNewMenu() {

		// create file chooser
		String filename = File.separator + IDropletContext.DROPLET_FILE_EXTENSION;
		final JFileChooser fileChooser = new JFileChooser(new File(filename));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				Messages.getString("DropletMenu.fileNameFilterTitle"), IDropletContext.DROPLET_FILE_EXTENSION); //$NON-NLS-1$
		fileChooser.setFileFilter(filter);

		// create actions
		newAction = new NewAction(frame, dropletContext);
		openAction = new OpenFileAction(frame, fileChooser, dropletContext);
		openTemplateAction = new OpenAsTemplateAction(frame, fileChooser, dropletContext);
		saveAction = new SaveFileAction(frame, fileChooser, dropletContext);
		saveAsAction = new SaveAsFileAction(frame, fileChooser, dropletContext);
		exitAction = new ExitAction(frame, dropletContext);

		// create menu items
		newMenuItem = new JMenuItem(newAction);
		fileMenu.add(newMenuItem);
		openMenuItem = new JMenuItem(openAction);
		fileMenu.add(openMenuItem);
		openTemplateMenuItem = new JMenuItem(openTemplateAction);
		fileMenu.add(openTemplateMenuItem);
		saveMenuItem = new JMenuItem(saveAction);
		fileMenu.add(saveMenuItem);
		saveAsMenuItem = new JMenuItem(saveAsAction);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		exitMenuItem = new JMenuItem(exitAction);
		fileMenu.add(exitMenuItem);

	}

	private void buildEditMenu() {

		addDeviceAction = new AddDeviceAction(frame, dropletContext);
		preferencesAction = new PreferencesAction(frame, dropletContext);

		// create menu items
		addDeviceMenuItem = new JMenuItem(Messages.getString("DropletMenu.addDevice")); //$NON-NLS-1$
		addDeviceMenuItem.setAction(addDeviceAction);
		editMenu.add(addDeviceMenuItem);
		preferencesMenuItem = new JMenuItem("Preferences..."); //$NON-NLS-1$
		preferencesMenuItem.setAction(preferencesAction);
		editMenu.add(preferencesMenuItem);

	}

	private void buildHelpMenu() {
		aboutMenuItem = new JMenuItem(Messages.getString("DropletMenu.about")); //$NON-NLS-1$
		aboutMenuItem.setMnemonic(UiUtils.getMnemonic(Messages.getString("DropletMenu.aboutMnemonic"))); //$NON-NLS-1$
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog(frame).setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
	}

}
