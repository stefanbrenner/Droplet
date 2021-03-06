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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Simple plugin-loader based on Java's {@link ServiceLoader}.
 * 
 * @author Stefan Brenner
 * @see <a
 *      href=
 *      "http://java.sun.com/developer/technicalArticles/javase/extensible/">http://java.sun.com/developer/technicalArticles/javase/extensible/</a>
 */
public final class PluginLoader {
	
	/**
	 * Empty default constructor.
	 */
	private PluginLoader() {
	}
	
	/**
	 * Returns a list of all service implementations that are found on the
	 * classpath.
	 * 
	 * @param <T>
	 *            type of requested service implementations
	 * @param serviceInterface
	 *            for which implementations should be found
	 * @return a list of all service implementations that are found on the
	 *         classpath
	 */
	public static <T> List<T> getPlugins(final Class<T> serviceInterface) {
		List<T> providers = new ArrayList<T>();
		
		ServiceLoader<T> load = ServiceLoader.load(serviceInterface);
		Iterator<T> iterator = load.iterator();
		while (iterator.hasNext()) {
			providers.add(iterator.next());
		}
		
		return providers;
	}
	
}
