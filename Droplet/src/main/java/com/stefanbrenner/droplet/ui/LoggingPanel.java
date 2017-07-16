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
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.DropletFonts;
import com.stefanbrenner.droplet.utils.Messages;

/**
 * Panel that shows logging messages.
 * 
 * @author Stefan Brenner
 */
public class LoggingPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final JTextArea txtMessages;
	
	/**
	 * Create the panel.
	 */
	public LoggingPanel(final IDropletContext context) {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(Messages.getString("LoggingPanel.title"))); //$NON-NLS-1$
		setMinimumSize(new Dimension(400, 200));
		
		BeanAdapter<IDropletContext> adapter = new BeanAdapter<IDropletContext>(context, true);
		
		txtMessages = BasicComponentFactory.createTextArea(adapter.getValueModel(IDropletContext.PROPERTY_LOGGING));
		txtMessages.setFocusable(true);
		txtMessages.setFocusTraversalKeysEnabled(true);
		txtMessages.setMargin(new Insets(0, 10, 0, 10));
		txtMessages.setFont(DropletFonts.FONT_LOGGING_SMALL);
		txtMessages.setEditable(false);
		JScrollPane loggingScrollPane = new JScrollPane(txtMessages);
		loggingScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(final AdjustmentEvent event) {
				// event.getAdjustable().setValue(event.getAdjustable().getMaximum());
				if (txtMessages.getText().length() > 1) {
					txtMessages.setCaretPosition(txtMessages.getText().length() - 1);
				}
			}
		});
		add(loggingScrollPane, BorderLayout.CENTER);
		
		txtMessages.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent event) {
				if ((event.getKeyCode() == KeyEvent.VK_DELETE) || (event.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					context.clearLoggingMessages();
				}
				super.keyPressed(event);
			}
		});
		
	}
	
}
