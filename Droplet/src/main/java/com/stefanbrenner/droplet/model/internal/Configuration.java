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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.service.IDropletMessageProtocol;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;
import com.stefanbrenner.droplet.service.impl.ArduinoService;
import com.stefanbrenner.droplet.service.impl.DropletMessageProtocol;
import com.stefanbrenner.droplet.utils.PluginLoader;

/**
 * Simple preferences wrapper.
 * 
 * @author Stefan Brenner
 */
public class Configuration {
	
	public static final String CONF_SERIAL_COMM_PROVIDER = "Configuration.SerialCommProvider"; //$NON-NLS-1$
	
	public static final String CONF_MESSAGE_PROTOCOL_PROVIDER = "Configuration.MessageProtocolProvider"; //$NON-NLS-1$
	
	private static final Preferences PREFS = Preferences.userNodeForPackage(Configuration.class);
	
	private static final String PREF_SERIAL_COMM_PROVIDER = "Droplet.SerialCommunicationProvider"; //$NON-NLS-1$
	
	private static final ISerialCommunicationService DEFAULT_SERIAL_COMM_PROVIDER = new ArduinoService();
	
	private Configuration() {
		// no need to instantiate this class
	}
	
	public static ISerialCommunicationService getSerialCommProvider() {
		String string = Configuration.PREFS.get(Configuration.PREF_SERIAL_COMM_PROVIDER, null);
		List<ISerialCommunicationService> plugins = PluginLoader.getPlugins(ISerialCommunicationService.class);
		for (ISerialCommunicationService commService : plugins) {
			if (StringUtils.equals(commService.getClass().getCanonicalName(), string)) {
				return commService;
			}
		}
		// if no provider was found we use our own arduino service instead
		return Configuration.DEFAULT_SERIAL_COMM_PROVIDER;
	}
	
	public static void setSerialCommProvider(final ISerialCommunicationService commService) {
		ISerialCommunicationService oldService = Configuration.getSerialCommProvider();
		Configuration.PREFS.put(Configuration.PREF_SERIAL_COMM_PROVIDER, commService.getClass().getCanonicalName());
		Configuration.support.firePropertyChange(Configuration.CONF_SERIAL_COMM_PROVIDER, oldService, commService);
	}
	
	private static final String PREF_MESSAGE_PROTOCOL = "Droplet.MessageProtocolProvider"; //$NON-NLS-1$
	
	private static final IDropletMessageProtocol DEFAULT_MESSAGE_PROTOCOL_PROVIDER = new DropletMessageProtocol();
	
	public static IDropletMessageProtocol getMessageProtocolProvider() {
		String string = Configuration.PREFS.get(Configuration.PREF_MESSAGE_PROTOCOL, null);
		List<IDropletMessageProtocol> plugins = PluginLoader.getPlugins(IDropletMessageProtocol.class);
		for (IDropletMessageProtocol messageService : plugins) {
			if (StringUtils.equals(messageService.getClass().getCanonicalName(), string)) {
				return messageService;
			}
		}
		// if no provider was found we use our own message protocol provider
		return Configuration.DEFAULT_MESSAGE_PROTOCOL_PROVIDER;
	}
	
	public static void setMessageProtocolProvider(final IDropletMessageProtocol messageProtocol) {
		IDropletMessageProtocol oldProtocol = Configuration.getMessageProtocolProvider();
		Configuration.PREFS.put(Configuration.PREF_MESSAGE_PROTOCOL, messageProtocol.getClass().getCanonicalName());
		Configuration.support.firePropertyChange(Configuration.CONF_MESSAGE_PROTOCOL_PROVIDER, oldProtocol,
				messageProtocol);
	}
	
	// simple static notification support
	
	private static PropertyChangeSupport support = new PropertyChangeSupport(new Configuration());
	
	public static void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		Configuration.support.addPropertyChangeListener(propertyName, listener);
	}
	
	public static void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		Configuration.support.removePropertyChangeListener(propertyName, listener);
	}
	
}
