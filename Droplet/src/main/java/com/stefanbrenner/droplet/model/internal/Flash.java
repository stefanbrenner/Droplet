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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.xml.ColorAdapter;

@XmlRootElement(name = "Flash")
public class Flash extends AbstractActionDevice implements IFlash {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "Color")
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color color;

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		firePropertyChange(PROPERTY_COLOR, this.color, this.color = color);
	}

	@Override
	protected String getDeviceType() {
		return "Flash";
	}

	@Override
	public IAction createNewAction() {
		return new Action();
	}

}
