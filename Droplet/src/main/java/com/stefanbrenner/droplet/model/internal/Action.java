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
import javax.xml.bind.annotation.XmlRootElement;

import com.stefanbrenner.droplet.model.IAction;

/**
 * @author Stefan Brenner
 */
@XmlRootElement(name = "Action")
public class Action extends AbstractModelObject implements IAction {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "Enabled")
	private boolean enabled;
	
	@XmlAttribute(name = "Offset")
	private Integer offset;
	
	/**
	 * Creates a new enabled action with offset 0.
	 */
	public Action() {
		setEnabled(true);
		setOffset(0);
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void setEnabled(final boolean enabled) {
		firePropertyChange(IAction.PROPERTY_ENABLED, this.enabled, this.enabled = enabled);
	}
	
	@Override
	public Integer getOffset() {
		return offset;
	}
	
	@Override
	public void setOffset(final Integer offset) {
		firePropertyChange(IAction.PROPERTY_OFFSET, this.offset, this.offset = offset);
	}
	
	@Override
	public void addOffset(final Integer offset) {
		firePropertyChange(IAction.PROPERTY_OFFSET, this.offset, this.offset += offset);
	}
	
}
