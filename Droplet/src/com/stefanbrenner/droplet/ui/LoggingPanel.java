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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.service.ISerialCommService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * @author Stefan Brenner
 */
public class LoggingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ISerialCommService commService;

	private JTextArea txtMessages;

	/**
	 * Create the panel.
	 */
	public LoggingPanel() {

		commService = new ArduinoService();

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Logging"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		txtMessages = new JTextArea(5, 50);
		txtMessages.setFocusable(false);
		txtMessages.setFocusTraversalKeysEnabled(true);
		txtMessages.setMargin(new Insets(10, 10, 10, 10));
		txtMessages.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		txtMessages.setEditable(false);
		JScrollPane loggingScrollPane = new JScrollPane(txtMessages);
		UiUtils.editGridBagConstraints(gbc, 0, 0, 1, 0);
		add(loggingScrollPane, gbc);

		JButton btnEcho = new JButton("Test Echo");
		btnEcho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				commService.connect(Droplet.getInstance().getPort());
				commService.sendData("This is just a test message!\n");
				commService.close();
			}
		});
		UiUtils.editGridBagConstraints(gbc, 0, 1, 1, 0, GridBagConstraints.SOUTHEAST);
		gbc.fill = GridBagConstraints.NONE;
		add(btnEcho, gbc);

	}

	/**
	 * Add a message to the logging text area.
	 */
	public void addMessage(String message) {
		// TODO brenner: make locale configurable in settings
		DateFormat format = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());
		String timestamp = format.format(new Date(System.currentTimeMillis()));
		String logEntry = timestamp + ": " + message;
		if (txtMessages.getText().isEmpty()) {
			txtMessages.setText(logEntry);
		} else {
			txtMessages.setText(StringUtils.join(txtMessages.getText(), "\n", logEntry));
		}
	}
}
