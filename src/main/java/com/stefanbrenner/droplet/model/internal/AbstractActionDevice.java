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
import java.util.stream.Collectors;

import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IValve;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Abstract base class for all droplet action devices.
 * 
 * @author Stefan Brenner
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractActionDevice extends AbstractDevice implements IActionDevice {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "Action",
			type = Action.class)
	@XmlElementWrapper(name = "Actions")
	private List<IAction> actions = new ArrayList<IAction>();
	@XmlAttribute(name = "Calibration")
	private int calibration;
	
	@Override
	public List<IAction> getEnabledActions() {
		return getActions().stream().filter(a -> a.isEnabled()).collect(Collectors.toList());
	}
	
	@Override
	public void setActions(final List<IAction> actions) {
		firePropertyChange(IActionDevice.ASSOCIATION_ACTIONS, this.actions, this.actions = actions);
	}
	
	@Override
	public void addAction(final IAction action) {
		List<IAction> oldValue = actions;
		actions = new ArrayList<IAction>(this.actions);
		actions.add(action);
		firePropertyChange(IActionDevice.ASSOCIATION_ACTIONS, oldValue, actions);
	}
	
	@Override
	public void removeAction(final IAction action) {
		List<IAction> oldValue = actions;
		actions = new ArrayList<IAction>(this.actions);
		actions.remove(action);
		firePropertyChange(IActionDevice.ASSOCIATION_ACTIONS, oldValue, actions);
	}
	
	@Override
	public void reset() {
		super.reset();
		actions.clear();
	}
	
	@Override
	public void setCalibration(final int calibration) {
		firePropertyChange(IValve.PROPERTY_CALIBRATION, this.calibration, this.calibration = calibration);
	}
	
	@Override
	public IAction createNewAction() {
		IAction newAction = createNewActionInternal();
		addAction(newAction);
		return newAction;
	}
	
	protected abstract IAction createNewActionInternal();
	
}
