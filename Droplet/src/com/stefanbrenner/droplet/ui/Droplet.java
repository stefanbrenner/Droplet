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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Droplet extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Droplet frame = new Droplet();
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
	public Droplet() {
		setTitle("Droplet - Toolkit for Liquid Art Photographers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel commPanel = new CommunicationPanel();
		contentPane.add(commPanel, BorderLayout.NORTH);

		{
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout(0, 0));
			contentPane.add(mainPanel, BorderLayout.CENTER);

			JPanel configPanel = new ConfigurationPanel();
			mainPanel.add(configPanel, BorderLayout.CENTER);

			JPanel processingPanel = new ProcessingPanel();
			mainPanel.add(processingPanel, BorderLayout.SOUTH);
		}

		JPanel toolbarPanel = new DropletToolbar();
		contentPane.add(toolbarPanel, BorderLayout.SOUTH);

		// make frame as small as possible
		pack();
		// set minimum size to packed size
		setMinimumSize(getSize());

	}

}
