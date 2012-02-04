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
package com.stefanbrenner.droplet.service.impl;

import org.mangosdk.spi.ProviderFor;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;

/**
 * This class implements the Droplet Serial Message Protocol.
 * 
 * @author Stefan Brenner
 */
@ProviderFor(IDropletMessageProtocol.class)
public class DropletMessageProtocol implements IDropletMessageProtocol {

	// meta characters
	public static final String FIELD_SEPARATOR = ";";
	public static final String BLOCK_SEPARATOR = "^";

	// commands
	public static final String COMMAND_RELEASE = "R";
	public static final String COMMAND_SEND = "S";
	public static final String COMMAND_INFO = "I";

	@Override
	public String getName() {
		return "Droplet Serial Communication Protocol";
	}

	@Override
	public String createStartMessage(int rounds, int delay) {
		return COMMAND_RELEASE + FIELD_SEPARATOR + rounds + FIELD_SEPARATOR + delay;
	}

	@Override
	public String createSendMessage(IDroplet droplet) {
		// TODO brenner implement
		return null;
	}

	@Override
	public String createInfoMessage() {
		return COMMAND_INFO;
	}

}
