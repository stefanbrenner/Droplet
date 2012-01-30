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

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.service.ISerialCommService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;

public class CommunicationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final IDropletContext dropletContext;

	private final JComboBox cmbPort;
	private final JLabel lblStatus;

	private final ISerialCommService commService = ArduinoService.getInstance();

	/**
	 * Create the panel.
	 */
	public CommunicationPanel(IDropletContext context) {

		this.dropletContext = context;

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
				selectPort();
			}
		});
		add(cmbPort);

		// status label
		add(new JLabel("Status:"));
		lblStatus = new JLabel("Offline");
		lblStatus.setForeground(Color.RED);
		add(lblStatus);

		// TODO brenner: update port list periodically
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// while (!Thread.interrupted()) {
		// updatePorts();
		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }).start();

		selectPort();

	}

	private void selectPort() {
		// TODO brenner: run in thread
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		boolean connected = false;

		// close previous connection
		if (commService.isConnected()) {
			commService.close();
		}

		Object selectedItem = cmbPort.getSelectedItem();
		if (selectedItem instanceof CommPortIdentifier) {
			CommPortIdentifier portId = (CommPortIdentifier) selectedItem;
			dropletContext.setPort(portId);
			connected = commService.connect(portId);
		}

		updateStatus(connected);
		// }
		// }).start();
	}

	private void updateStatus(boolean connected) {
		if (connected) {
			lblStatus.setText("Connected");
			lblStatus.setForeground(Color.GREEN);
		} else {
			lblStatus.setText("Not Connected");
			lblStatus.setForeground(Color.RED);
		}
	}

}