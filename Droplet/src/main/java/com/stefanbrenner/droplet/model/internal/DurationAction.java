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

import com.stefanbrenner.droplet.model.IDurationAction;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Stefan Brenner
 */
@XmlRootElement(name = "DurationAction")
@Getter
@NoArgsConstructor
public class DurationAction extends Action implements IDurationAction {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "Duration")
	private Integer duration = 0;
	
	@Override
	public void setDuration(final Integer duration) {
		firePropertyChange(IDurationAction.PROPERTY_DURATION, this.duration, this.duration = duration);
	}
	
	@Override
	public void addDuration(final Integer duration) {
		firePropertyChange(IDurationAction.PROPERTY_DURATION, this.duration, this.duration += duration);
	}
	
}
