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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDevice;

/**
 * Base class for all devices. They can have a name and a description.
 * 
 * @author Stefan Brenner
 */
public abstract class AbstractDevice extends AbstractModelObject implements IDevice {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "Name")
	private String name = StringUtils.EMPTY;

	@XmlElement(name = "Description")
	private String description = StringUtils.EMPTY;

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
		firePropertyChange(PROPERTY_DESCRIPTION, this.description, this.description = description);
	}

	protected abstract String getDeviceType();

	@Override
	public void reset() {
		setName(getDeviceType());
		setDescription(StringUtils.EMPTY);
	}

}
