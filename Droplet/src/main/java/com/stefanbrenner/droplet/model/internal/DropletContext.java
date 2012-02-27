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
package com.stefanbrenner.droplet.model.internal;

import gnu.io.CommPortIdentifier;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;

/**
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class DropletContext extends AbstractModelObject implements IDropletContext {

	private File file;

	private CommPortIdentifier port;

	private IDroplet droplet;

	private Integer rounds = 1;

	private Integer roundDelay = 1000;

	private List<String> loggingMessages = new ArrayList<String>();

	private String lastSetMessage = StringUtils.EMPTY;

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(final File file) {
		firePropertyChange(PROPERTY_FILE, this.file, this.file = file);
	}

	@Override
	public CommPortIdentifier getPort() {
		return port;
	}

	@Override
	public void setPort(final CommPortIdentifier port) {
		firePropertyChange(PROPERTY_PORT, this.port, this.port = port);
	}

	@Override
	public IDroplet getDroplet() {
		return droplet;
	}

	@Override
	public void setDroplet(final IDroplet droplet) {
		firePropertyChange(PROPERTY_DROPLET, this.droplet, this.droplet = droplet);
	}

	@Override
	public String getLoggingMessages() {
		return StringUtils.join(loggingMessages, '\n');
	}

	@Override
	public void addLoggingMessage(final String message) {
		String oldValue = getLoggingMessages();
		loggingMessages = new ArrayList<String>(loggingMessages);

		// add timestamp to message
		DateFormat format = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault());
		String timestamp = format.format(new Date(System.currentTimeMillis()));
		String logEntry = timestamp + ": " + message; //$NON-NLS-1$

		loggingMessages.add(logEntry);

		firePropertyChange(PROPERTY_LOGGING, oldValue, getLoggingMessages());
	}

	@Override
	public void clearLoggingMessages() {
		String oldValue = getLoggingMessages();
		loggingMessages = new ArrayList<String>();
		firePropertyChange(PROPERTY_LOGGING, oldValue, getLoggingMessages());
	}

	@Override
	public String getLastSetMessage() {
		return lastSetMessage;
	}

	@Override
	public void setLastSetMessage(String lastSetMessage) {
		this.lastSetMessage = lastSetMessage;
	}

	@Override
	public Integer getRounds() {
		return rounds;
	}

	@Override
	public void setRounds(Integer rounds) {
		firePropertyChange(PROPERTY_ROUNDS, this.rounds, this.rounds = rounds);
	}

	@Override
	public Integer getRoundDelay() {
		return roundDelay;
	}

	@Override
	public void setRoundDelay(Integer roundDelay) {
		firePropertyChange(PROPERTY_ROUND_DELAY, this.roundDelay, this.roundDelay = roundDelay);
	}

}
