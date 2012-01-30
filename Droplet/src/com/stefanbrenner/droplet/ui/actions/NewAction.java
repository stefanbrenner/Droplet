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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class NewAction extends AbstractDropletAction {

	public NewAction(IDropletContext dropletContext) {
		super(dropletContext, "New");

		putValue(ACCELERATOR_KEY, UiUtils.getAccelerator(KeyEvent.VK_N));
		putValue(SHORT_DESCRIPTION, "New Droplet Configuration");

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO brenner: warn that information gets lost
		Droplet droplet = new Droplet();
		// TODO brenner: remove this initialization
		droplet.initializeWithDefaults();
		getDropletContext().setDroplet(droplet);
	}

}
