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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.ObjectUtils;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Camera;
import com.stefanbrenner.droplet.model.internal.Flash;
import com.stefanbrenner.droplet.model.internal.Valve;

/**
 * @author Stefan Brenner
 * 
 */
@SuppressWarnings("serial")
public class AddDeviceDialog extends AbstractDropletDialog implements ActionListener {

	private final JButton btnValve;
	private final JButton btnFlash;
	private final JButton btnCamera;
	private final JButton btnClose;

	public AddDeviceDialog(JFrame frame, IDropletContext dropletContext) {
		super(frame, dropletContext, Messages.getString("AddDeviceDialog.title")); //$NON-NLS-1$

		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(1, 0, 7, 7));

		btnValve = new JButton(Messages.getString("AddDeviceDialog.valve")); //$NON-NLS-1$
		btnValve.addActionListener(this);
		panel.add(btnValve);
		btnFlash = new JButton(Messages.getString("AddDeviceDialog.flash")); //$NON-NLS-1$
		btnFlash.addActionListener(this);
		panel.add(btnFlash);
		btnCamera = new JButton(Messages.getString("AddDeviceDialog.camera")); //$NON-NLS-1$
		btnCamera.addActionListener(this);
		panel.add(btnCamera);
		btnClose = new JButton(Messages.getString("AddDeviceDialog.close")); //$NON-NLS-1$
		btnClose.addActionListener(this);
		panel.add(btnClose);

		add(panel);

		setAlwaysOnTop(true);
		setResizable(false);
		pack();
		setLocationRelativeTo(frame);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (ObjectUtils.equals(btnValve, source)) {
			getDropletContext().getDroplet().addDevice(new Valve());
		} else if (ObjectUtils.equals(btnFlash, source)) {
			getDropletContext().getDroplet().addDevice(new Flash());
		} else if (ObjectUtils.equals(btnCamera, source)) {
			getDropletContext().getDroplet().addDevice(new Camera());
		} else if (ObjectUtils.equals(btnClose, source)) {
			setVisible(false);
		}
	}

}
