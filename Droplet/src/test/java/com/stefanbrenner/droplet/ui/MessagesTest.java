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
package com.stefanbrenner.droplet.ui;

import static junit.framework.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

/**
 * @author Stefan Brenner
 * 
 */
public class MessagesTest {

	private static final String BUNDLE_NAME = "com.stefanbrenner.droplet.ui.testmessages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Read localized message from resources and format it.
	 * 
	 * @see MessageFormat
	 */
	public static String getString(String key, Object... args) {
		try {
			String message = RESOURCE_BUNDLE.getString(key);
			MessageFormat format = new MessageFormat(message);
			return format.format(args);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	@Test
	public void testGetMessage() {
		assertEquals("Basic", MessagesTest.getString("MessagesTest"));
		assertEquals("Simple", MessagesTest.getString("MessagesTest.Simple"));
		assertEquals("Advanced {0}", MessagesTest.getString("MessagesTest.Advanced"));

		assertEquals("Advanced Test", MessagesTest.getString("MessagesTest.Advanced", "Test"));
		assertEquals("Advanced Test with multiple Parameters !",
				MessagesTest.getString("MessagesTest.Multiple.Ascending", "Test", "multiple", "!"));
		assertEquals("Advanced Test with multiple Parameters !",
				MessagesTest.getString("MessagesTest.Multiple.Unsorted", "multiple", "!", "Test"));
		assertEquals("Advanced Test with Test", MessagesTest.getString("MessagesTest.Multiple.Repeat", "Test"));

		Object[] arguments = { new Integer(7), new GregorianCalendar(2012, 0, 1).getTime(),
				"a disturbance in the Force" };
		assertEquals("At 12:00:00 AM on Jan 1, 2012, there was a disturbance in the Force on planet 7.",
				MessagesTest.getString("MessagesTest.Number", arguments));

	}

}
