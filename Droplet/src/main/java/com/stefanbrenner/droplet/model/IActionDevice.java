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

import java.util.List;

/**
 * Base interface for all action devices that can be used in droplet.
 * 
 * @author Stefan Brenner
 */
public interface IActionDevice extends IDevice {
	
	/** Association name for actions of this device. */
	String ASSOCIATION_ACTIONS = "actions"; //$NON-NLS-1$
	
	/**
	 * @param actions
	 *            list of actions for this device
	 */
	void setActions(List<IAction> actions);
	
	/**
	 * @return all actions of this device
	 */
	List<IAction> getActions();
	
	/**
	 * @return all enabled actions of this device
	 */
	List<IAction> getEnabledActions();
	
	/**
	 * @param action
	 *            action to be removed from this device
	 */
	void removeAction(IAction action);
	
	/**
	 * @param action
	 *            action to add to this device
	 */
	void addAction(IAction action);
	
}
