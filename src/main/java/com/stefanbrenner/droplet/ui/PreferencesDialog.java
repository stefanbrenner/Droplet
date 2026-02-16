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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.PluginLoader;

/**
 * Dialog that shows droplet preferences.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class PreferencesDialog extends JDialog {
	
	private final JComboBox<ISerialCommunicationService> cmbCommService;
	private final JComboBox<IDropletMessageProtocol> cmbMsgProtocol;
	private final JComboBox<Locale> cmbLocale;
	
	public PreferencesDialog(final JFrame frame) {
		super(frame, true);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BorderLayout());
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		settingsPanel.add(new JLabel(Messages.getString("PreferencesDialog.SerialCommunicationService"))); //$NON-NLS-1$
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		List<ISerialCommunicationService> commProviders = PluginLoader.getPlugins(ISerialCommunicationService.class);
		cmbCommService = new JComboBox<>();
		for (ISerialCommunicationService s : commProviders) {
			cmbCommService.addItem(s);
			if (s.getClass().equals(serialCommProvider.getClass())) {
				cmbCommService.setSelectedItem(s);
			}
		}
		cmbCommService.setRenderer(new ListCellRenderer<ISerialCommunicationService>() {
			@Override
			public Component getListCellRendererComponent(final JList<? extends ISerialCommunicationService> list,
					final ISerialCommunicationService value, final int index, final boolean isSelected,
					final boolean cellHasFocus) {
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index,
						isSelected, cellHasFocus);
			}
		});
		settingsPanel.add(cmbCommService);
		
		settingsPanel.add(new JLabel(Messages.getString("PreferencesDialog.DropletMessageProtocol"))); //$NON-NLS-1$
		cmbMsgProtocol = new JComboBox<>();
		IDropletMessageProtocol messageProtocolProvider = Configuration.getMessageProtocolProvider();
		List<IDropletMessageProtocol> messageProviders = PluginLoader.getPlugins(IDropletMessageProtocol.class);
		for (IDropletMessageProtocol p : messageProviders) {
			cmbMsgProtocol.addItem(p);
			if (p.getClass().equals(messageProtocolProvider.getClass())) {
				cmbMsgProtocol.setSelectedItem(p);
			}
		}
		cmbMsgProtocol.setRenderer(new ListCellRenderer<IDropletMessageProtocol>() {
			@Override
			public Component getListCellRendererComponent(final JList<? extends IDropletMessageProtocol> list,
					final IDropletMessageProtocol value, final int index, final boolean isSelected,
					final boolean cellHasFocus) {
				return new DefaultListCellRenderer().getListCellRendererComponent(list, value.getName(), index,
						isSelected, cellHasFocus);
			}
		});
		settingsPanel.add(cmbMsgProtocol);
		
		settingsPanel.add(new JLabel(Messages.getString("PreferencesDialog.Locale"))); //$NON-NLS-1$
		cmbLocale = new JComboBox<>();
		cmbLocale.addItem(Locale.GERMAN);
		cmbLocale.addItem(Locale.ENGLISH);
		cmbLocale.setSelectedItem(Configuration.getLocale());
		cmbLocale.setRenderer(new ListCellRenderer<Locale>() {
			@Override
			public Component getListCellRendererComponent(final JList<? extends Locale> list, final Locale value,
					final int index, final boolean isSelected, final boolean cellHasFocus) {
				return new DefaultListCellRenderer().getListCellRendererComponent(list,
						value.getDisplayName(Configuration.getLocale()), index, isSelected, cellHasFocus);
			}
		});
		settingsPanel.add(cmbLocale);
		
		panel.add(settingsPanel, BorderLayout.CENTER);
		
		// toolbar panel
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				setVisible(false);
				dispose();
			}
		});
		panelBottom.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				saveAndClose();
			}
		});
		panelBottom.add(btnOk);
		
		panel.add(panelBottom, BorderLayout.PAGE_END);
		add(panel);
		
		pack();
		setLocationRelativeTo(frame);
		setResizable(false);
		setTitle(Messages.getString("PreferencesDialog.Title")); //$NON-NLS-1$
		
	}
	
	private void saveAndClose() {
		
		Configuration.setSerialCommProvider((ISerialCommunicationService) cmbCommService.getSelectedItem());
		Configuration.setMessageProtocolProvider((IDropletMessageProtocol) cmbMsgProtocol.getSelectedItem());
		
		if (!Configuration.getLocale().equals(cmbLocale.getSelectedItem())) {
			Configuration.setLocale((Locale) cmbLocale.getSelectedItem());
			JOptionPane.showMessageDialog(this, Messages.getString("PreferencesDialog.Locale.info.description"),
					Messages.getString("PreferencesDialog.Locale.info.title"), JOptionPane.INFORMATION_MESSAGE);
		}
		
		setVisible(false);
		dispose();
	}
}
