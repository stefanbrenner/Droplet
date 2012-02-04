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

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommService;
import com.stefanbrenner.droplet.utils.PluginLoader;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class PreferencesDialog extends JDialog {

	private final IDropletContext context;

	private JComboBox cmbCommService;
	private JComboBox cmbMsgProtocol;

	public PreferencesDialog(JFrame frame, IDropletContext context) {
		super(frame, true);

		this.context = context;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 1));

		// TODO brenner: bind to some model object
		panel.add(new JLabel("Serial Communication Service:"));
		List<ISerialCommService> commProviders = PluginLoader.getPlugins(ISerialCommService.class);
		cmbCommService = new JComboBox(commProviders.toArray());
		cmbCommService.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (comp instanceof JLabel && value instanceof ISerialCommService) {
					((JLabel) comp).setText(((ISerialCommService) value).getName());
				}
				return comp;
			}

		});
		panel.add(cmbCommService);

		// TODO brenner: bind to some model object
		panel.add(new JLabel("Droplet Message Protocol:"));
		List<IDropletMessageProtocol> messageProviders = PluginLoader.getPlugins(IDropletMessageProtocol.class);
		cmbMsgProtocol = new JComboBox(messageProviders.toArray());
		cmbMsgProtocol.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (comp instanceof JLabel && value instanceof IDropletMessageProtocol) {
					((JLabel) comp).setText(((IDropletMessageProtocol) value).getName());
				}
				return comp;
			}

		});
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

	private void saveAndClose() {

		// TODO brenner: save to configuration

		setVisible(false);
		dispose();
	}

}
