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
package com.stefanbrenner.droplet.ui.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * JSpinner that registers a {@link MouseWheelListener} to navigate through the
 * values of the model.
 * 
 * @author Stefan Brenner
 */
// TODO brenner: only allow numeric input
// TODO brenner: add keylistener cmd+UP/DOWN = +/-10ms
// TODO brenner: select all on focus gained
public class MouseWheelSpinner extends JSpinner {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new spinner that can be scrolled with the mouse wheel.
	 */
	public MouseWheelSpinner() {
		this(false);
	}
	
	/**
	 * Creates a new spinner that can be scrolled with the mouse wheel.
	 * 
	 * @param selectOnFocus
	 *            select the text of the spinner if the spinner gets the focus
	 */
	public MouseWheelSpinner(final boolean selectOnFocus) {
		super();
		registerMouseWheelListener();
		if (selectOnFocus) {
			registerFocusListener();
		}
	}
	
	// TODO brenner: not working as intended
	// need to implement own editor to be able to listen to events
	private void registerFocusListener() {
		final JFormattedTextField textField = ((JSpinner.DefaultEditor) getEditor()).getTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(final FocusEvent event) {
				textField.selectAll();
				super.focusGained(event);
			}
		});
	}
	
	private void registerMouseWheelListener() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(final MouseWheelEvent event) {
				final JFormattedTextField textField = ((JSpinner.DefaultEditor) getEditor()).getTextField();
				if (textField.hasFocus()) {
					try {
						SpinnerModel model = getModel();
						int add = -(event.getWheelRotation());
						for (int i = 0; i < Math.abs(add); i++) {
							if (add > 0) {
								model.setValue(model.getNextValue());
							} else {
								model.setValue(model.getPreviousValue());
							}
						}
					} catch (IllegalArgumentException e) {
						// thrown if we try to set a value that is not allowed
						// in the model
					}
				} else {
					// we don't consume the event, so handle it to the parent
					if (getParent() != null) {
						getParent().dispatchEvent(event);
					}
				}
			}
		});
	}
	
}
