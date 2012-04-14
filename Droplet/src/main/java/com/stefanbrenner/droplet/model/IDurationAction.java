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
 * Interface for all droplet actions with a duration.
 * 
 * @author Stefan Brenner
 */
public interface IDurationAction extends IAction {

	/** Property name for the action duration. */
	String PROPERTY_DURATION = "duration"; //$NON-NLS-1$

	/**
	 * @param duration
	 *            of the action in milliseconds
	 */
	void setDuration(Integer duration);

	/**
	 * @return duration of the action in milliseconds
	 */
	Integer getDuration();

	/**
	 * @param duration
	 *            in milliseconds to be added to the action duration
	 */
	void addDuration(Integer duration);

}
