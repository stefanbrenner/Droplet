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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;

import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Action to enable/disable fullscreen.
 *
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class FullscreenAction extends AbstractDropletAction {
	
	public FullscreenAction(final JFrame frame) {
		super(frame, null, Messages.getString("FullscreenAction.title")); //$NON-NLS-1$
		
		putValue(Action.ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_F, KeyEvent.SHIFT_DOWN_MASK));
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		boolean newValue = !Configuration.isFullscreenEnabled();
		getFrame().dispose();
		getFrame().setUndecorated(newValue);
		getFrame().setExtendedState(newValue ? Frame.MAXIMIZED_BOTH : Frame.NORMAL);
		getFrame().setVisible(true);
		Configuration.setFullscreenEnabled(newValue);
	}
}
