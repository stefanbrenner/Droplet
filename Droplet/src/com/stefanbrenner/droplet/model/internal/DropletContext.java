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

import gnu.io.CommPortIdentifier;

import java.io.File;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class DropletContext extends AbstractModelObject implements IDropletContext {

	private File file;

	private CommPortIdentifier port;

	private IDroplet droplet;

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public CommPortIdentifier getPort() {
		return port;
	}

	@Override
	public void setPort(CommPortIdentifier port) {
		firePropertyChange(PROPERTY_PORT, this.port, this.port = port);
	}

	@Override
	public IDroplet getDroplet() {
		return droplet;
	}

	@Override
	public void setDroplet(IDroplet droplet) {
		firePropertyChange(PROPERTY_DROPLET, this.droplet, this.droplet = droplet);
	}

}
