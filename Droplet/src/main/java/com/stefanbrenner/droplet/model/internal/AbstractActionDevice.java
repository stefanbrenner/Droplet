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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;

/**
 * Abstract base class for all droplet action devices.
 * 
 * @author Stefan Brenner
 */
public abstract class AbstractActionDevice extends AbstractDevice implements
		IActionDevice {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Action", type = Action.class)
	@XmlElementWrapper(name = "Actions")
	private List<IAction> actions = new ArrayList<IAction>();

	@Override
	public List<IAction> getActions() {
		return actions;
	}

	@Override
	public List<IAction> getEnabledActions() {
		List<IAction> enabledActions = new ArrayList<IAction>();
		for (IAction action : getActions()) {
			if (action.isEnabled()) {
				enabledActions.add(action);
			}
		}
		return enabledActions;
	}

	@Override
	public void setActions(final List<IAction> actions) {
		firePropertyChange(ASSOCIATION_ACTIONS, this.actions,
				this.actions = actions);
	}

	@Override
	public void addAction(final IAction action) {
		List<IAction> oldValue = actions;
		actions = new ArrayList<IAction>(this.actions);
		actions.add(action);
		firePropertyChange(ASSOCIATION_ACTIONS, oldValue, actions);
	}

	@Override
	public void removeAction(final IAction action) {
		List<IAction> oldValue = actions;
		actions = new ArrayList<IAction>(this.actions);
		actions.remove(action);
		firePropertyChange(ASSOCIATION_ACTIONS, oldValue, actions);
	}

	@Override
	public void reset() {
		super.reset();
		actions.clear();
	}

}
