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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;
import com.stefanbrenner.droplet.service.impl.DropletMessageProtocol;

/**
 * @author Stefan Brenner
 */
public class ConfigurationTest {

	@Test
	public void testSerialCommunicationPrefs() {

		ISerialCommService arduinoService = new ArduinoService();

		Configuration.setSerialCommProvider(arduinoService);

		ISerialCommService serialCommProvider = Configuration.getSerialCommProvider();

		assertEquals(arduinoService.getClass(), serialCommProvider.getClass());

	}

	@Test
	public void testMessageProtocolPrefs() {

		IDropletMessageProtocol messageProtocol = new DropletMessageProtocol();

		Configuration.setMessageProtocolProvider(messageProtocol);

		IDropletMessageProtocol msgProtocolProvider = Configuration.getMessageProtocolProvider();

		assertEquals(messageProtocol.getClass(), msgProtocolProvider.getClass());

	}

}
