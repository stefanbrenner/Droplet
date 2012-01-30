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

import gnu.io.CommPortIdentifier;

import java.io.File;

/**
 * @author Stefan Brenner
 */
public interface IDropletContext extends INotificationSupport {

	public static final String PROPERTY_PORT = "port";

	public static final String PROPERTY_DROPLET = "droplet";

	public abstract void setPort(CommPortIdentifier port);

	public abstract CommPortIdentifier getPort();

	public abstract void setDroplet(IDroplet droplet);

	public abstract IDroplet getDroplet();

	public abstract void setFile(File file);

	public abstract File getFile();

}
