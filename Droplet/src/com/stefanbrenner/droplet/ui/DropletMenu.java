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
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.AbstractDropletAction;
import com.stefanbrenner.droplet.ui.actions.AddDeviceAction;
import com.stefanbrenner.droplet.ui.actions.ExitAction;
import com.stefanbrenner.droplet.ui.actions.NewAction;
import com.stefanbrenner.droplet.ui.actions.OpenAsTemplateAction;
import com.stefanbrenner.droplet.ui.actions.OpenFileAction;
import com.stefanbrenner.droplet.ui.actions.SaveAsFileAction;
import com.stefanbrenner.droplet.ui.actions.SaveFileAction;

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
	private JMenuItem aboutMenuItem;

	// actions
	private AbstractDropletAction newAction;
	private AbstractDropletAction openAction;
	private AbstractDropletAction openTemplateAction;
	private AbstractDropletAction saveAction;
	private AbstractDropletAction saveAsAction;
	private AbstractDropletAction addDeviceAction;
	private AbstractDropletAction exitAction;

	public DropletMenu(JFrame frame, IDropletContext dropletContext) {
		super();
		this.frame = frame;
		this.dropletContext = dropletContext;
		build();
	}

	private void build() {
		// file menu
		fileMenu = add(new JMenu("File"));
		fileMenu.setMnemonic('f');
		buildNewMenu();
		// edit menu
		editMenu = add(new JMenu("Edit"));
		editMenu.setMnemonic('e');
		buildEditMenu();
		// help menu
		helpMenu = add(new JMenu("Help"));
		helpMenu.setMnemonic('h');
		buildHelpMenu();
	}

	private void buildNewMenu() {

		// create file chooser
		String filename = File.separator + "drp";
		final JFileChooser fileChooser = new JFileChooser(new File(filename));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Droplets", "drp");
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

		// create menu items
		addDeviceMenuItem = new JMenuItem("Add Device");
		addDeviceMenuItem.setAction(addDeviceAction);
		editMenu.add(addDeviceMenuItem);
	}

	private void buildHelpMenu() {
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setMnemonic('a');
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO show about dialog
			}
		});
		helpMenu.add(aboutMenuItem);
	}

}
