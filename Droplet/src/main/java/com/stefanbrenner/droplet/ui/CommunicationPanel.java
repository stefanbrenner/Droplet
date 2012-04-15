/*****************************************************************************
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
 *****************************************************************************/
package com.stefanbrenner.droplet.ui;

import gnu.io.CommPortIdentifier;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;

/**
 * Panel that displays controls to connect to a microcontroller.
 * 
 * @author Stefan Brenner
 */
public class CommunicationPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final IDropletContext dropletContext;
	
	private final JComboBox cmbPort;
	private final JLabel lblStatus;
	
	private ISerialCommunicationService commService;
	private List<CommPortIdentifier> ports;
	
	/**
	 * Create the panel.
	 */
	public CommunicationPanel(final IDropletContext context) {
		
		this.dropletContext = context;
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		setBorder(BorderFactory.createTitledBorder(Messages.getString("CommunicationPanel.title"))); //$NON-NLS-1$
		
		// port selection combo box
		cmbPort = new JComboBox();
		cmbPort.setRenderer(new ListCellRenderer() {
			
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
			
			@Override
			public Component getListCellRendererComponent(final JList list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				
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
			public void itemStateChanged(final ItemEvent event) {
				selectPort();
			}
		});
		add(cmbPort);
		
		// status label
		add(new JLabel(Messages.getString("CommunicationPanel.status"))); //$NON-NLS-1$
		lblStatus = new JLabel(Messages.getString("CommunicationPanel.notConnected")); //$NON-NLS-1$
		lblStatus.setForeground(Color.RED);
		add(lblStatus);
		
		// set communication service from configuration
		setCommService(Configuration.getSerialCommProvider());
		
		// add listener to selected communication provider
		Configuration.addPropertyChangeListener(Configuration.CONF_SERIAL_COMM_PROVIDER, new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				// disconnect old service provider
				Object oldValue = event.getOldValue();
				if (oldValue instanceof ISerialCommunicationService) {
					((ISerialCommunicationService) oldValue).close();
				}
				cmbPort.removeAllItems();
				Object newValue = event.getNewValue();
				if (newValue instanceof ISerialCommunicationService) {
					setCommService((ISerialCommunicationService) newValue);
				}
			}
		});
		
		// add listener to update available ports
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					
					List<CommPortIdentifier> newPorts = Arrays.asList(commService.getPorts());
					
					// check if new port is available
					for (CommPortIdentifier port : newPorts) {
						if (!ports.contains(port)) {
							ports.add(port);
							cmbPort.addItem(port);
						}
					}
					
					// check if port has gone
					for (CommPortIdentifier port : new ArrayList<CommPortIdentifier>(ports)) {
						if (!newPorts.contains(port)) {
							ports.remove(port);
							cmbPort.removeItem(port);
						}
					}
					
					// sleep for 2 seconds
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}
	
	private void setCommService(final ISerialCommunicationService commService) {
		this.commService = commService;
		setPorts(commService.getPorts());
	}
	
	private void setPorts(final CommPortIdentifier[] ports) {
		this.ports = new ArrayList<CommPortIdentifier>(Arrays.asList(ports));
		if ((ports == null) || (ports.length == 0)) {
			cmbPort.addItem(Messages.getString("CommunicationPanel.NoPortAvailable")); //$NON-NLS-1$
		} else {
			for (CommPortIdentifier port : ports) {
				cmbPort.addItem(port);
			}
		}
	}
	
	private void selectPort() {
		boolean connected = false;
		
		// close previous connection
		if (commService.isConnected()) {
			commService.close();
		}
		
		Object selectedItem = cmbPort.getSelectedItem();
		if (selectedItem instanceof CommPortIdentifier) {
			CommPortIdentifier portId = (CommPortIdentifier) selectedItem;
			dropletContext.setPort(portId);
			connected = commService.connect(portId, dropletContext);
		}
		
		updateStatus(connected);
	}
	
	private void updateStatus(final boolean connected) {
		if (connected) {
			lblStatus.setText(Messages.getString("CommunicationPanel.connected")); //$NON-NLS-1$
			lblStatus.setForeground(Color.GREEN);
		} else {
			lblStatus.setText(Messages.getString("CommunicationPanel.notConnected")); //$NON-NLS-1$
			lblStatus.setForeground(Color.RED);
		}
	}
	
}
