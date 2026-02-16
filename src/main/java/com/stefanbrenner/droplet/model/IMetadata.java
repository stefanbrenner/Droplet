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
package com.stefanbrenner.droplet.model;

/**
 * Interface for all metadata that droplet can add to pictures.
 * 
 * @author Stefan Brenner
 */
public interface IMetadata extends INotificationSupport {
	
	/** Property name for the description of a picture. */
	String PROPERTY_DESCRIPTION = "description"; //$NON-NLS-1$
	/** Property name for the tags of a picture. */
	String PROPERTY_TAGS = "tags"; //$NON-NLS-1$
	
	/**
	 * @return the description
	 */
	String getDescription();
	
	/**
	 * @param description
	 *            the description to set
	 */
	void setDescription(String description);
	
	/**
	 * @return the comma separated tags
	 */
	String getTags();
	
	/**
	 * @param tags
	 *            comma separated list of tags to set
	 */
	void setTags(String tags);
}
