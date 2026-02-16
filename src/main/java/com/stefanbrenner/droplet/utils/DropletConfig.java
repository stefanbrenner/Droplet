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

import com.tngtech.configbuilder.annotation.valueextractor.DefaultValue;
import com.tngtech.configbuilder.annotation.valueextractor.SystemPropertyValue;

/**
 * Simple Droplet Configuration class.
 * 
 * @author Stefan Brenner
 */
public final class DropletConfig {
	
	private DropletConfig() {
	}
	
	@SystemPropertyValue("droplet.autoLoadPorts")
	@DefaultValue("true")
	private static boolean autoLoadPorts;
	
	/**
	 * Defines whether the list of available communication ports should be
	 * monitored and updated automatically.
	 * 
	 * @return <code>true</code> if the list of ports should be updated
	 *         automatically
	 */
	public static boolean isAutoLoadPorts() {
		return autoLoadPorts;
	}
	
}
