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
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.model.internal.DropletContext;
import com.stefanbrenner.droplet.ui.actions.StartAction;
import com.stefanbrenner.droplet.utils.DropletFonts;

public class DropletMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// model objects
	private final IDropletContext dropletContext;

	// ui components
	private final DropletMenu dropletMenu;
	private final JPanel contentPane;
	private final CommunicationPanel commPanel;
	private final DeviceSetupPanel configPanel;
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
		// set look and feel
		try {
			// use nimbus L&F if available
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

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

		// create new context
		dropletContext = new DropletContext();

		// create new droplet model and add to context
		Droplet droplet = new Droplet();
		droplet.initializeWithDefaults();
		dropletContext.setDroplet(droplet);

		// basic frame setup
		setTitle("Droplet - Toolkit for Liquid Art Photographers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		// TODO brenner: setIconImage(new
		// ImageIcon("icons/icon.gif").getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// create menu
		dropletMenu = new DropletMenu(dropletContext);
		setJMenuBar(dropletMenu);

		commPanel = new CommunicationPanel(dropletContext);
		contentPane.add(commPanel, BorderLayout.NORTH);

		{
			configPanel = new DeviceSetupPanel(dropletContext.getDroplet());
			processingPanel = new ProcessingPanel();
			loggingPanel = new LoggingPanel();

			JSplitPane splitPaneBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, processingPanel, loggingPanel);
			splitPaneBottom.setOneTouchExpandable(true);
			splitPaneBottom.setDividerLocation(0.5d);
			splitPaneBottom.setResizeWeight(0.0d);

			JSplitPane splitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, configPanel, splitPaneBottom);
			splitPaneMain.setOneTouchExpandable(true);
			splitPaneMain.setDividerLocation(0.5d);
			splitPaneMain.setResizeWeight(1.0d);
			contentPane.add(splitPaneMain, BorderLayout.CENTER);
		}

		toolbarPanel = new DropletToolbar(dropletContext);
		contentPane.add(toolbarPanel, BorderLayout.SOUTH);

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
		contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), "start");
		contentPane.getActionMap().put("start", new StartAction(contentPane, dropletContext));

		// add listener
		dropletContext.addPropertyChangeListener(IDropletContext.PROPERTY_DROPLET, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO brenner: inform panels that droplet model changed
				configPanel.setDroplet(dropletContext.getDroplet());
			}
		});

		// set default fonts
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, DropletFonts.FONT_STANDARD_SMALL);
			}
		}

		// finishing frame settings
		// make frame as small as possible
		// pack();
		// set to full screen mode
		setExtendedState(Frame.MAXIMIZED_BOTH);
		// set minimum size to packed size
		setMinimumSize(new Dimension(300, 100));
		// update ui for nimbus component resizing
		SwingUtilities.updateComponentTreeUI(this);

	}
}
