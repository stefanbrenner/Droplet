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

import java.awt.Color;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

/**
 * @author Stefan Brenner
 */
public class DropletColors {

	public static final Color BG_VALVE = new Color(204, 255, 204, 255);

	public static final Color BG_FLASH = new Color(204, 255, 255, 255);

	public static final Color BG_CAMERA = new Color(254, 255, 204, 255);

	public static Color getBackgroundColor(IDevice device) {
		if (device instanceof IValve) {
			return BG_VALVE;
		} else if (device instanceof IFlash) {
			return BG_FLASH;
		} else if (device instanceof ICamera) {
			return BG_CAMERA;
		}
		return Color.GRAY;
	}

}
