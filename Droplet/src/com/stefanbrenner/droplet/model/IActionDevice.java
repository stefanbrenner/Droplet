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
package com.stefanbrenner.droplet.model;

import java.util.List;

/**
 * @author Stefan Brenner
 */
public interface IActionDevice<T extends IAction> extends IDevice {

	public static final String ASSOCIATION_ACTIONS = "actions";

	public abstract void setActions(List<T> actions);

	public abstract List<T> getActions();

	public abstract void removeAction(T action);

	public abstract void addAction(T action);

	public abstract T createNewAction();

}
