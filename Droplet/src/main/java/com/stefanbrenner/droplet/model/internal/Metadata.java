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
package com.stefanbrenner.droplet.model.internal;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IMetadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Wrapper class for all metadata informations that can be added to pictures by
 * droplet.
 * 
 * @author Stefan Brenner
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Metadata extends AbstractModelObject implements IMetadata {
	
	private static final long serialVersionUID = 1L;
	
	private String description = StringUtils.EMPTY;
	private String tags = StringUtils.EMPTY;
	
	@Override
	public final void setDescription(final String description) {
		firePropertyChange(IMetadata.PROPERTY_DESCRIPTION, this.description, this.description = description);
	}
	
	@Override
	public final void setTags(final String tags) {
		firePropertyChange(IMetadata.PROPERTY_TAGS, this.tags, this.tags = tags);
	}
	
}
