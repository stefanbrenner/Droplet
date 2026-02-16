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

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.AddCameraAction;
import com.stefanbrenner.droplet.ui.actions.AddFlashAction;
import com.stefanbrenner.droplet.ui.actions.AddValveAction;
import com.stefanbrenner.droplet.ui.actions.DisableAllCamerasAction;
import com.stefanbrenner.droplet.ui.actions.DisableAllFlashesAction;
import com.stefanbrenner.droplet.ui.actions.DisableAllValvesAction;
import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.Messages;

/**
 * Droplet toolbar containing the most useful actions.
 * 
 * @author Stefan Brenner
 */
public class DropletDevicesToolbar extends JToolBar {
	
	private static final long serialVersionUID = 1L;
	
	// ui components
	
	/**
	 * Create the panel.
	 */
	public DropletDevicesToolbar(final JFrame frame, final IDropletContext dropletContext) {
		
		setOrientation(JToolBar.VERTICAL);
		// setLayout(new FlowLayout(FlowLayout.RIGHT));
		setFloatable(false);
		setRollover(true);
		setBackground(DropletColors.DARK_GRAY);
		
		// add new device of a category
		JButton btnAddValve = new JButton(new AddValveAction(frame, dropletContext, true));
		btnAddValve.setBorderPainted(false);
		btnAddValve.setMargin(new Insets(10, 0, 10, 0));
		btnAddValve.setToolTipText(Messages.getString("AddValveAction.tooltip")); //$NON-NLS-1$
		add(btnAddValve);
		
		JButton btnAddFlash = new JButton(new AddFlashAction(frame, dropletContext, true));
		btnAddFlash.setBorderPainted(false);
		btnAddFlash.setMargin(new Insets(10, 0, 10, 0));
		btnAddFlash.setToolTipText(Messages.getString("AddFlashAction.tooltip")); //$NON-NLS-1$
		add(btnAddFlash);
		
		JButton btnAddCamera = new JButton(new AddCameraAction(frame, dropletContext, true));
		btnAddCamera.setBorderPainted(false);
		btnAddCamera.setMargin(new Insets(10, 0, 10, 0));
		btnAddCamera.setToolTipText(Messages.getString("AddCameraAction.tooltip")); //$NON-NLS-1$
		add(btnAddCamera);
		
		addSeparator();
		
		// disable all devices of a category
		JButton btnDisableAllValves = new JButton(new DisableAllValvesAction(frame, dropletContext, true));
		btnDisableAllValves.setBorderPainted(false);
		btnDisableAllValves.setMargin(new Insets(10, 0, 10, 0));
		btnDisableAllValves.setToolTipText(Messages.getString("DisableAllValvesAction.tooltip")); //$NON-NLS-1$
		add(btnDisableAllValves);
		
		JButton btnDisableAllFlashes = new JButton(new DisableAllFlashesAction(frame, dropletContext, true));
		btnDisableAllFlashes.setBorderPainted(false);
		btnDisableAllFlashes.setMargin(new Insets(10, 0, 10, 0));
		btnDisableAllFlashes.setToolTipText(Messages.getString("DisableAllFlashesAction.tooltip")); //$NON-NLS-1$
		add(btnDisableAllFlashes);
		
		JButton btnDisableAllCameras = new JButton(new DisableAllCamerasAction(frame, dropletContext, true));
		btnDisableAllCameras.setBorderPainted(false);
		btnDisableAllCameras.setMargin(new Insets(10, 0, 10, 0));
		btnDisableAllCameras.setToolTipText(Messages.getString("DisableAllCamerasAction.tooltip")); //$NON-NLS-1$
		add(btnDisableAllCameras);
		
	}
	
}
