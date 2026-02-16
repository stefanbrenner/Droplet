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

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDevice;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class for all devices. They can have a name and a description.
 * 
 * @author Stefan Brenner
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDevice extends AbstractModelObject implements IDevice {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "Number")
	private int number = 0;
	
	@XmlAttribute(name = "Name")
	private String name = StringUtils.EMPTY;
	
	@XmlElement(name = "Description")
	private String description = StringUtils.EMPTY;
	
	@XmlElement(name = "Enabled")
	private boolean enabled = true;
	
	@Override
	public void setNumber(final int number) {
		firePropertyChange(IDevice.PROPERTY_NUMBER, this.number, this.number = number);
	}
	
	@Override
	public void setName(final String name) {
		firePropertyChange(IDevice.PROPERTY_NAME, this.name, this.name = name);
	}
	
	@Override
	public void setDescription(final String description) {
		firePropertyChange(IDevice.PROPERTY_DESCRIPTION, this.description, this.description = description);
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		firePropertyChange(IDevice.PROPERTY_ENABLED, this.enabled, this.enabled = enabled);
	}
	
	/**
	 * @return a string representing the type of the device
	 */
	protected abstract String getDeviceType();
	
	@Override
	public void reset() {
		setName(getDeviceType());
		setDescription(StringUtils.EMPTY);
	}
	
}
