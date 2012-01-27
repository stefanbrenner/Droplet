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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.internal.Droplet;

public class DropletMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPane;

	private final IDroplet droplet = Droplet.getInstance();

	// panels
	private final CommunicationPanel commPanel;
	private final ConfigurationPanel configPanel;
	private final ProcessingPanel processingPanel;
	private final LoggingPanel loggingPanel;
	private final DropletToolbar toolbarPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
