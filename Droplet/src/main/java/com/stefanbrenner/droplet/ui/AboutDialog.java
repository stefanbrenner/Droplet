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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stefanbrenner.droplet.utils.DropletFonts;
import com.stefanbrenner.droplet.utils.Messages;

/**
 * A dialog that displays information about droplet.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	
	/**
	 * Constructs a new about dialog.
	 * 
	 * @param frame
	 *            parent of this dialog
	 */
	public AboutDialog(final JFrame frame) {
		super(frame);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lbTitle = new JLabel(Messages.getString("AboutDialog.title")); //$NON-NLS-1$
		lbTitle.setFont(DropletFonts.FONT_HEADER_LARGE);
		panel.add(lbTitle);
		
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(new JLabel(Messages.getString("AboutDialog.version"))); //$NON-NLS-1$
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(new JLabel(Messages.getString("AboutDialog.credentials"))); //$NON-NLS-1$
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JLabel lbVisit = new JLabel(Messages.getString("AboutDialog.link")); //$NON-NLS-1$
		lbVisit.setForeground(Color.BLUE);
		lbVisit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbVisit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				try {
					AboutDialog.open(new URI("http://www.droplet.at")); //$NON-NLS-1$
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(lbVisit);
		
		add(panel);
		
		pack();
		setLocationRelativeTo(frame);
		setResizable(false);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				dispose();
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				dispose();
			}
		});
	}
	
	/**
	 * Open a URI in the system browser.
	 * 
	 * @param uri
	 *            to be opened
	 */
	private static void open(final URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
			}
		}
	}
	
}
