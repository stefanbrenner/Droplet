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

import gnu.io.CommPortIdentifier;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.service.ISerialCommService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;

public class CommunicationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JComboBox cmbPort;
	private final JLabel lblStatus;

	private final ISerialCommService commService = new ArduinoService();

	/**
	 * Create the panel.
	 */
	public CommunicationPanel() {

		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		setBorder(BorderFactory.createTitledBorder("Communication"));

		// port selection combo box
		cmbPort = new JComboBox(commService.getPorts());
		cmbPort.setRenderer(new ListCellRenderer() {

			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				JLabel lbItem = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);

				if (value instanceof CommPortIdentifier) {
					lbItem.setText(((CommPortIdentifier) value).getName());
				}

				return lbItem;
			}
		});
		cmbPort.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				updateStatus();
			}
		});
		add(cmbPort);

		// status label
		add(new JLabel("Status:"));
		lblStatus = new JLabel("Offline");
		lblStatus.setForeground(Color.RED);
		add(lblStatus);

		// start status thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					updateStatus();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	private void updateStatus() {
		boolean isOnline = false;

		Object selectedItem = cmbPort.getSelectedItem();
		if (selectedItem instanceof CommPortIdentifier) {
			CommPortIdentifier portId = (CommPortIdentifier) selectedItem;

			// set selected port on Droplet
			Droplet.getInstance().setPort(portId);

			// TODO brenner: connect immediately?

			isOnline = commService.isOnline(portId);
		}

		if (isOnline) {
			lblStatus.setText("Online");
			lblStatus.setForeground(Color.GREEN);
		} else {
			lblStatus.setText("Offline");
			lblStatus.setForeground(Color.RED);
		}
	}

}
