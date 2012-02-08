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

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;
import com.stefanbrenner.droplet.utils.PluginLoader;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class PreferencesDialog extends JDialog {

	private final JComboBox cmbCommService;
	private final JComboBox cmbMsgProtocol;

	public PreferencesDialog(JFrame frame) {
		super(frame, true);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 1));

		panel.add(new JLabel("Serial Communication Service:"));
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		List<ISerialCommunicationService> commProviders = PluginLoader.getPlugins(ISerialCommunicationService.class);
		cmbCommService = new JComboBox();
		for (ISerialCommunicationService s : commProviders) {
			cmbCommService.addItem(s);
			if (s.getClass().equals(serialCommProvider.getClass())) {
				cmbCommService.setSelectedItem(s);
			}
		}
		cmbCommService.setRenderer(renderer);
		panel.add(cmbCommService);

		panel.add(new JLabel("Droplet Message Protocol:"));
		cmbMsgProtocol = new JComboBox();
		IDropletMessageProtocol messageProtocolProvider = Configuration.getMessageProtocolProvider();
		List<IDropletMessageProtocol> messageProviders = PluginLoader.getPlugins(IDropletMessageProtocol.class);
		for (IDropletMessageProtocol p : messageProviders) {
			cmbMsgProtocol.addItem(p);
			if (p.getClass().equals(messageProtocolProvider.getClass())) {
				cmbMsgProtocol.setSelectedItem(p);
			}
		}
		cmbMsgProtocol.setRenderer(renderer);
		panel.add(cmbMsgProtocol);

		// toolbar panel
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new GridLayout(1, 0));
		panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAndClose();
			}
		});
		panelBottom.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				dispose();
			}
		});
		panelBottom.add(btnCancel);

		panel.add(panelBottom);
		add(panel);

		pack();
		setLocationRelativeTo(frame);
		setResizable(false);
		setTitle("Preferences");

	}

	/**
	 * {@link ListCellRenderer} for {@link ISerialCommunicationService} and
	 * {@link IDropletMessageProtocol}.
	 */
	private ListCellRenderer renderer = new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				if (value instanceof ISerialCommunicationService) {
					label.setText(((ISerialCommunicationService) value).getName());
				}
				if (value instanceof IDropletMessageProtocol) {
					label.setText(((IDropletMessageProtocol) value).getName());
				}
			}
			return comp;
		}
	};

	private void saveAndClose() {

		// save to configuration
		Object selectedItem = cmbCommService.getSelectedItem();
		if (selectedItem instanceof ISerialCommunicationService) {
			Configuration.setSerialCommProvider((ISerialCommunicationService) selectedItem);
		}
		selectedItem = cmbMsgProtocol.getSelectedItem();
		if (selectedItem instanceof IDropletMessageProtocol) {
			Configuration.setMessageProtocolProvider((IDropletMessageProtocol) selectedItem);
		}

		setVisible(false);
		dispose();
	}
}
