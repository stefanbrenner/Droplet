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

/**
 * Base interface for all droplet actions.
 * 
 * @author Stefan Brenner
 */
public interface IAction extends INotificationSupport {

	/** Property name for the enabled flag of an action. */
	String PROPERTY_ENABLED = "enabled"; //$NON-NLS-1$

	/** Property name for the offset of the action. */
	String PROPERTY_OFFSET = "offset"; //$NON-NLS-1$

	/**
	 * @return <code>true</code> if this action is enabled, <code>false</code>
	 *         otherwise
	 */
	boolean isEnabled();

	/**
	 * @param enabled
	 *            sets the enabled flag of this action
	 */
	void setEnabled(boolean enabled);

	/**
	 * @param offset
	 *            offset of this action in milliseconds
	 */
	void setOffset(Integer offset);

	/**
	 * @return the offset of this action in milliseconds
	 */
	Integer getOffset();

	/**
	 * @param offset
	 *            in milliseconds to be added to the action's offset
	 */
	void addOffset(Integer offset);

}
