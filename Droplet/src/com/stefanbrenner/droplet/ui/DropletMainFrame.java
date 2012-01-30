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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.ui.actions.StartAction;
import com.stefanbrenner.droplet.utils.UiUtils;

public class DropletMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// TODO brenner: don't use singleton due to bad design
	private final IDroplet droplet = Droplet.getInstance();

	// ui components
	private final DropletMenu dropletMenu;
	private final JPanel contentPane;
	private final CommunicationPanel commPanel;
	private final ConfigurationPanel configPanel;
	private final ProcessingPanel processingPanel;
	private final LoggingPanel loggingPanel;
	private final DropletToolbar toolbarPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// TODO brenner: identify that we run on a mac
		// String lcOSName = System.getProperty("os.name").toLowerCase();
		// boolean IS_MAC = lcOSName.startsWith("mac os x");

		// put jmenubar on mac menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		// set application name
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Droplet");
		// use nativ look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DropletMainFrame frame = new DropletMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DropletMainFrame() {

		// basic frame setup
		setTitle("Droplet - Toolkit for Liquid Art Photographers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		// TODO setIconImage(new ImageIcon("icons/icon.gif").getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// create menu
		dropletMenu = new DropletMenu();
		setJMenuBar(dropletMenu);

		commPanel = new CommunicationPanel();
		contentPane.add(commPanel, BorderLayout.NORTH);

		{
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			contentPane.add(mainPanel, BorderLayout.CENTER);

			GridBagConstraints gbc = UiUtils.createGridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;

			configPanel = new ConfigurationPanel();
			UiUtils.editGridBagConstraints(gbc, 0, 0, 1, 1);
			mainPanel.add(configPanel, gbc);

			processingPanel = new ProcessingPanel();
			UiUtils.editGridBagConstraints(gbc, 0, 1, 1, 0);
			mainPanel.add(processingPanel, gbc);

			loggingPanel = new LoggingPanel();
			UiUtils.editGridBagConstraints(gbc, 0, 2, 1, 0);
			mainPanel.add(loggingPanel, gbc);
		}

		toolbarPanel = new DropletToolbar(droplet);
		contentPane.add(toolbarPanel, BorderLayout.SOUTH);

		// make frame as small as possible
		pack();
		// set minimum size to packed size
		setMinimumSize(getSize());

		// remove focus from text components if user clicks outside
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
				super.mouseClicked(e);
			}
		});

		// register action shortcuts
		// TODO brenner: don't consume keys in JTextComponents
		contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F4"),
				"start");
		contentPane.getActionMap().put("start", new StartAction());

	}

}
