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
package com.stefanbrenner.droplet.utils;

import java.awt.Dimension;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

/**
 * @author Stefan Brenner
 */
public class DropletDimensions {

	public static final int WIDTH_ACTION = 180;

	public static final int WIDTH_DURATION_ACTION = 290;

	public static final int HEIGHT_MIN = 220;

	public static Dimension getDimension(IDevice device) {
		if (device instanceof IValve) {
			return new Dimension(WIDTH_DURATION_ACTION, HEIGHT_MIN);
		} else if (device instanceof IFlash) {
			return new Dimension(WIDTH_ACTION, HEIGHT_MIN);
		} else if (device instanceof ICamera) {
			return new Dimension(WIDTH_DURATION_ACTION, HEIGHT_MIN);
		}
		return new Dimension();
	}
}
