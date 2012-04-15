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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.AddDeviceDialog;

/**
 * Actions to add a new device to droplet.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class AddDeviceAction extends AbstractDropletAction {
	
	public AddDeviceAction(final JFrame frame, final IDropletContext dropletContext) {
		super(frame, dropletContext, Messages.getString("AddDeviceAction.title")); //$NON-NLS-1$
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		JDialog dialog = new AddDeviceDialog(getFrame(), getDropletContext());
		dialog.setVisible(true);
	}
	
}
