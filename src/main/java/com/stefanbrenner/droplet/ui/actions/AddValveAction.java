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

import java.awt.Color;

import javax.swing.JFrame;

import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.model.internal.Valve;
import com.stefanbrenner.droplet.utils.DropletColors;

/**
 * Actions to add a new device to droplet.
 *
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class AddValveAction extends AbstractAddSingleDeviceAction {
	
	public AddValveAction(final JFrame frame, final IDropletContext dropletContext, final boolean iconOnly) {
		super(frame, dropletContext, iconOnly);
	}
	
	@Override
	protected Color getDeviceColor() {
		return DropletColors.getBackgroundColor(IValve.class);
	}
	
	@Override
	protected IDevice getNewDevice() {
		return new Valve();
	}
	
}
