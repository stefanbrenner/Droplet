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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.AbstractDropletAction;
import com.stefanbrenner.droplet.ui.actions.NewAction;
import com.stefanbrenner.droplet.ui.actions.OpenFileAction;
import com.stefanbrenner.droplet.ui.actions.SaveAsFileAction;
import com.stefanbrenner.droplet.ui.actions.SaveFileAction;

/**
 * @author Stefan Brenner
 */
public class DropletMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	// model object
	private final IDropletContext dropletContext;

	// menues
	private JMenu fileMenu;
	private JMenu helpMenu;

	// menu items
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem aboutMenuItem;

	// actions
	private AbstractDropletAction newAction;
	private AbstractDropletAction openAction;
	private AbstractDropletAction saveAction;
	private AbstractDropletAction saveAsAction;

	public DropletMenu(IDropletContext dropletContext) {
		super();
		this.dropletContext = dropletContext;
		build();
	}

	private void build() {
		// file menu
		fileMenu = add(new JMenu("File"));
		buildNewMenu();
		// help menu
		helpMenu = add(new JMenu("Help"));
		buildHelpMenu();
	}

	private void buildNewMenu() {

		// create file chooser
		String filename = File.separator + "drp";
		final JFileChooser fileChooser = new JFileChooser(new File(filename));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Droplets", "drp");
		fileChooser.setFileFilter(filter);

		// create actions
		newAction = new NewAction(this, dropletContext);
		openAction = new OpenFileAction(this, fileChooser, dropletContext);
		saveAction = new SaveFileAction(this, fileChooser, dropletContext);
		saveAsAction = new SaveAsFileAction(this, fileChooser, dropletContext);

		// create menu items
		newMenuItem = new JMenuItem("New");
		newMenuItem.setAction(newAction);
		fileMenu.add(newMenuItem);
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setAction(openAction);
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAction(saveAction);
		fileMenu.add(saveMenuItem);
		saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.setAction(saveAsAction);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		exitMenuItem = new JMenuItem("Exit");
		// TODO brenner implement action
		fileMenu.add(exitMenuItem);

	}

	private void buildHelpMenu() {
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO show about dialog
			}
		});
		helpMenu.add(aboutMenuItem);
	}

}
