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
package com.stefanbrenner.droplet.xml;

import java.awt.Color;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML adapter to marshal {@link Color} informations.
 * 
 * @author Stefan Brenner
 */
public class ColorAdapter extends XmlAdapter<Integer, Color> {
	
	@Override
	public final Integer marshal(final Color color) throws Exception {
		if (color != null) {
			return color.getRGB();
		}
		return null;
	}
	
	@Override
	public final Color unmarshal(final Integer value) throws Exception {
		if (value != null) {
			return new Color(value);
		}
		return null;
	}
	
}
