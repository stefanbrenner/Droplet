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
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.internal.Droplet;

public class DropletMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane;

	private final IDroplet droplet = Droplet.getInstance();

	private final JMenuBar menuBar;

	// panels
	private final CommunicationPanel commPanel;
	private final ConfigurationPanel configPanel;
	private final ProcessingPanel processingPanel;
	private final LoggingPanel loggingPanel;
	private final DropletToolbar toolbarPanel;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// identify that we run on a mac
		String lcOSName = System.getProperty("os.name").toLowerCase();
		boolean IS_MAC = lcOSName.startsWith("mac os x");
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

		// build menu
		ActionListener menuItemListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				loggingPanel.addMessage(event.getActionCommand());
			}
		};
		menuBar = new JMenuBar();
		JMenu fileMenu = menuBar.add(new JMenu("File"));
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		newMenuItem.addActionListener(menuItemListener);
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		openMenuItem.addActionListener(menuItemListener);
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		saveMenuItem.addActionListener(menuItemListener);
		fileMenu.add(saveMenuItem);
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(menuItemListener);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(menuItemListener);
		fileMenu.add(exitMenuItem);
		JMenu helpMenu = menuBar.add(new JMenu("Help"));
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		commPanel = new CommunicationPanel();
		contentPane.add(commPanel, BorderLayout.NORTH);

		{
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new GridBagLayout());
			contentPane.add(mainPanel, BorderLayout.CENTER);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.BOTH;

			configPanel = new ConfigurationPanel();
			gbc.gridy = 0;
			mainPanel.add(configPanel, gbc);

			// reset y weight for following components
			gbc.weighty = 0;

			processingPanel = new ProcessingPanel();
			gbc.gridy = 1;
			mainPanel.add(processingPanel, gbc);

			loggingPanel = new LoggingPanel();
			gbc.gridy = 2;
			mainPanel.add(loggingPanel, gbc);
		}

		toolbarPanel = new DropletToolbar();
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

		// add key event dispatcher
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent event) {

				// don't react on key events from within text components
				if (event.getSource() instanceof JTextComponent) {
					return false;
				} else {
					if (event.getID() == KeyEvent.KEY_TYPED) {
						if (event.getKeyChar() == 's') {
							System.out.println("Start");
						} else {
							return false;
						}
						// we reach this only if we consumed the event
						return true;
					}
				}

				return false;
			}
		});

	}

}
