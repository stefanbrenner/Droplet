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
package com.stefanbrenner.droplet.model.internal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IFlashAction;

public class Flash extends AbstractModelObject implements IFlash {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private Color color;

	private List<IFlashAction> actions = new ArrayList<IFlashAction>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		firePropertyChange(PROPERTY_NAME, this.name, this.name = name);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		firePropertyChange(PROPERTY_DESCRIPTION, this.description,
				this.description = description);
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		firePropertyChange(PROPERTY_COLOR, this.color, this.color = color);
	}

}
