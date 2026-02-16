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
package com.stefanbrenner.droplet.service;

import java.net.URI;

import com.stefanbrenner.droplet.model.IMetadata;

/**
 * Describes a service that can automatically process metadata into new pictures
 * in a monitored folder.
 * 
 * @author Stefan Brenner
 */
public interface IMetadataProcessingService {
	
	/**
	 * @param uri
	 *            of the folder to be watched by this service
	 */
	void setWatchFolder(URI uri);
	
	/**
	 * @param uri
	 *            of the output folder
	 */
	void setOutputFolder(URI uri);
	
	/**
	 * @param metadata
	 *            to be used
	 */
	void setMetadata(IMetadata metadata);
	
	/**
	 * Start automatically processing new pictures in the watch folder.
	 */
	void start();
	
	/**
	 * Stop automatically processing new pictures in the watch folder.
	 */
	void stop();
	
}
