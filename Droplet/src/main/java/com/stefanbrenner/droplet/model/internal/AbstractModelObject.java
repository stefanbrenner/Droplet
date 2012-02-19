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

import javax.xml.bind.annotation.XmlAccessorType;

import com.jgoodies.binding.beans.Model;

/**
 * @author Stefan Brenner
 */
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public abstract class AbstractModelObject extends Model {

	private static final long serialVersionUID = 1L;

	// private final PropertyChangeSupport propertyChangeSupport = new
	// PropertyChangeSupport(
	// this);
	//
	// public void addPropertyChangeListener(PropertyChangeListener listener) {
	// propertyChangeSupport.addPropertyChangeListener(listener);
	// }
	//
	// public void addPropertyChangeListener(String propertyName,
	// PropertyChangeListener listener) {
	// propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	// }
	//
	// public void removePropertyChangeListener(PropertyChangeListener listener)
	// {
	// propertyChangeSupport.removePropertyChangeListener(listener);
	// }
	//
	// public void removePropertyChangeListener(String propertyName,
	// PropertyChangeListener listener) {
	// propertyChangeSupport.removePropertyChangeListener(propertyName,
	// listener);
	// }
	//
	// protected void firePropertyChange(String propertyName, Object oldValue,
	// Object newValue) {
	// propertyChangeSupport.firePropertyChange(propertyName, oldValue,
	// newValue);
	// }

}