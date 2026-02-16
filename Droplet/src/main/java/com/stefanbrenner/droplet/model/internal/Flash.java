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

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.xml.ColorAdapter;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Stefan Brenner
 */
@XmlRootElement(name = "Flash")
@Data
@EqualsAndHashCode(callSuper = true)
public class Flash extends AbstractActionDevice implements IFlash {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "Color")
	@XmlJavaTypeAdapter(ColorAdapter.class)
	private Color color;
	
	@Override
	public void setColor(final Color color) {
		firePropertyChange(IFlash.PROPERTY_COLOR, this.color, this.color = color);
	}
	
	@Override
	protected String getDeviceType() {
		return "Flash";
	}
	
	@Override
	public IAction createNewActionInternal() {
		return new Action();
	}
	
}
