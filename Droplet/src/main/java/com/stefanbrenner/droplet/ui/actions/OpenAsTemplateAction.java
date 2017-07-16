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
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Action to open a droplet configuration as template for a new configuration.
 * <p>
 * The devices of the new configuration are equal to the one from the template,
 * but all device actions are removed.
 *
 * @author Stefan Brenner
 */
@SuppressWarnings("serial")
public class OpenAsTemplateAction extends OpenFileAction {
	
	public OpenAsTemplateAction(final JFrame frame, final JFileChooser fileChooser,
			final IDropletContext dropletContext) {
		super(frame, fileChooser, dropletContext, Messages.getString("OpenAsTemplateAction.title")); //$NON-NLS-1$
		
		putValue(Action.ACCELERATOR_KEY, null);
		putValue(Action.MNEMONIC_KEY, UiUtils.getMnemonic(Messages.getString("OpenAsTemplateAction.mnemonic"))); //$NON-NLS-1$
		putValue(Action.SHORT_DESCRIPTION, Messages.getString("OpenAsTemplateAction.description")); //$NON-NLS-1$
	}
	
	@Override
	public void actionPerformed(final ActionEvent event) {
		open(true);
	}
	
}
