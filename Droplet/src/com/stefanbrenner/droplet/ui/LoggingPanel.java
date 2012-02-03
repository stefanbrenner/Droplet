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
import java.awt.Dimension;
import java.awt.Insets;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.utils.DropletFonts;

/**
 * @author Stefan Brenner
 */
public class LoggingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea txtMessages;

	/**
	 * Create the panel.
	 */
	public LoggingPanel() {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(Messages.getString("LoggingPanel.title"))); //$NON-NLS-1$
		setMinimumSize(new Dimension(400, 200));

		txtMessages = new JTextArea();
		txtMessages.setFocusable(false);
		txtMessages.setFocusTraversalKeysEnabled(true);
		txtMessages.setMargin(new Insets(10, 10, 10, 10));
		txtMessages.setFont(DropletFonts.FONT_LOGGING_SMALL);
		txtMessages.setEditable(false);
		JScrollPane loggingScrollPane = new JScrollPane(txtMessages);
		add(loggingScrollPane, BorderLayout.CENTER);

	}

	/**
	 * Add a message to the logging text area.
	 */
	public void addMessage(String message) {
		// TODO brenner: make locale configurable in settings
		DateFormat format = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());
		String timestamp = format.format(new Date(System.currentTimeMillis()));
		String logEntry = timestamp + ": " + message; //$NON-NLS-1$
		if (txtMessages.getText().isEmpty()) {
			txtMessages.setText(logEntry);
		} else {
			txtMessages
					.setText(StringUtils.join(txtMessages.getText(), System.getProperty("line.separator"), logEntry)); //$NON-NLS-1$
		}
	}
}
