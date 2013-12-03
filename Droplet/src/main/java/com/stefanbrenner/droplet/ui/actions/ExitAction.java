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
package com.stefanbrenner.droplet.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.IMetadata;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Action to exit and close the droplet application.
 * 
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractDropletAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExitAction.class);
	
	public ExitAction(final JFrame parent, final IDropletContext dropletContext) {
		super(parent, dropletContext, Messages.getString("ExitAction.title")); //$NON-NLS-1$
		
		putValue(Action.MNEMONIC_KEY, UiUtils.getMnemonic(Messages.getString("ExitAction.mnemonic"))); //$NON-NLS-1$
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		// TODO brenner: warn about unsaved changes
		
		LOGGER.debug("Shutting down ...");
		
		// save data to configuration
		IMetadata metadata = getDropletContext().getMetadata();
		Configuration.setMetadataComments(metadata.getDescription());
		Configuration.setMetadataTags(metadata.getTags());
		Configuration.setSerialCommPort(getDropletContext().getPort());
		
		LOGGER.debug("Successfully saved settings");
		
		getFrame().dispose();
		System.exit(0);
	}
	
}
