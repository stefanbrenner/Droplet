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
package com.stefanbrenner.droplet.model;


/**
 * @author Stefan Brenner
 */
public interface IAction extends INotificationSupport {

	public static final String PROPERTY_ENABLED = "enabled";

	public static final String PROPERTY_OFFSET = "offset";

	public abstract boolean isEnabled();

	public abstract void setEnabled(boolean enabled);

	public abstract void setOffset(Integer offset);

	public abstract Integer getOffset();

	public abstract void addOffset(Integer offset);

}
