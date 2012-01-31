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

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import com.stefanbrenner.droplet.model.IDropletContext;

/**
 * @author Stefan Brenner
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractDropletAction extends AbstractAction {

	private final IDropletContext dropletContext;
	private final JComponent parent;

	public AbstractDropletAction(JComponent parent, IDropletContext dropletContext, String title) {
		super(title);
		this.parent = parent;
		this.dropletContext = dropletContext;
	}

	protected JComponent getParent() {
		return parent;
	}

	protected IDropletContext getDropletContext() {
		return dropletContext;
	}

}
