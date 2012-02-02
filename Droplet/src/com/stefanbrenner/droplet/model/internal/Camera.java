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

import javax.xml.bind.annotation.XmlRootElement;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDurationAction;

@XmlRootElement(name = "Camera")
public class Camera extends AbstractActionDevice implements ICamera {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDeviceType() {
		return "Camera";
	}

	@Override
	public IDurationAction createNewAction() {
		return new DurationAction();
	}

}
