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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;
import com.stefanbrenner.droplet.service.impl.DropletMessageProtocol;

/**
 * @author Stefan Brenner
 */
class ConfigurationTest {
	
	@Test
	void testSerialCommunicationPrefs() {
		
		ISerialCommunicationService arduinoService = new ArduinoService();
		
		Configuration.setSerialCommProvider(arduinoService);
		
		ISerialCommunicationService serialCommProvider = Configuration.getSerialCommProvider();
		
		assertThat(serialCommProvider.getClass()).isEqualTo(arduinoService.getClass());
		
	}
	
	@Test
	void testMessageProtocolPrefs() {
		
		IDropletMessageProtocol messageProtocol = new DropletMessageProtocol();
		
		Configuration.setMessageProtocolProvider(messageProtocol);
		
		IDropletMessageProtocol msgProtocolProvider = Configuration.getMessageProtocolProvider();
		
		assertThat(msgProtocolProvider.getClass()).isEqualTo(messageProtocol.getClass());
		
	}
	
}
