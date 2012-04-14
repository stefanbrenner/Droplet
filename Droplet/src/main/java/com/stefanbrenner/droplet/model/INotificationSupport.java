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
package com.stefanbrenner.droplet.model;

import java.beans.PropertyChangeListener;

/**
 * Interface for notification support.
 * 
 * @author Stefan Brenner
 */
public interface INotificationSupport {

	/**
	 * @param listener
	 *            to be added
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @param propertyName
	 *            to which the listener should be added
	 * @param listener
	 *            to be added
	 */
	void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

	/**
	 * @param listener
	 *            to be removed
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @param propertyName
	 *            for which the listener should be removed
	 * @param listener
	 *            to be removed
	 */
	void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

}
