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
package com.stefanbrenner.droplet.utils;

import java.awt.Color;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

/**
 * Droplet color utilities.
 * <p>
 * This class provides static utility methods for coloring the ui.
 * 
 * @author Stefan Brenner
 */
public final class DropletColors {
	
	public static final Color MINT = new Color(243, 252, 247);
	public static final Color BLACK = new Color(37, 38, 39);
	public static final Color DARK_GRAY = new Color(89, 89, 89);
	public static final Color GRAY = new Color(127, 127, 127);
	public static final Color LIGHT_GRAY = new Color(165, 165, 165);
	public static final Color WHITE = new Color(249, 249, 249);
	
	/** The background color for valves. */
	public static final Color BG_VALVE = new Color(219, 255, 221);
	/** The background color for flashes. */
	public static final Color BG_FLASH = new Color(224, 252, 255);
	/** The background color for cameras. */
	public static final Color BG_CAMERA = new Color(253, 255, 224);
	
	/**
	 * @param device
	 *            droplet device for which a color is requested
	 * @return the appropriate color for a device
	 */
	public static Color getBackgroundColor(final Class<? extends IDevice> device) {
		if (IValve.class.isAssignableFrom(device)) {
			return DropletColors.BG_VALVE;
		} else if (IFlash.class.isAssignableFrom(device)) {
			return DropletColors.BG_FLASH;
		} else if (ICamera.class.isAssignableFrom(device)) {
			return DropletColors.BG_CAMERA;
		}
		return Color.GRAY;
	}
	
	public static <E extends IDevice> Color getBackgroundColor(final E device) {
		return getBackgroundColor(device.getClass());
	}
	
	/**
	 * Empty default constructor.
	 */
	private DropletColors() {
		
	};
	
}
