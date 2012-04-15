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
package com.stefanbrenner.droplet.ui.actions;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Stefan Brenner
 * 
 */
public class Messages {
	private static final String BUNDLE_NAME = "com.stefanbrenner.droplet.ui.actions.messages"; //$NON-NLS-1$
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);
	
	private Messages() {
	}
	
	/**
	 * Read localized message from resources and format it.
	 * 
	 * @see MessageFormat
	 */
	public static String getString(final String key, final Object... args) {
		try {
			String message = Messages.RESOURCE_BUNDLE.getString(key);
			MessageFormat format = new MessageFormat(message);
			return format.format(args);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
