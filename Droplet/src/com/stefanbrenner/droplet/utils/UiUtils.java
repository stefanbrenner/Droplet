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
package com.stefanbrenner.droplet.utils;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

/**
 * Utility class for user interface
 * 
 * @author Stefan Brenner
 */
public class UiUtils {

	public static int getMnemonic(String key) {
		return KeyStroke.getKeyStroke(key).getKeyCode();
	}

	/**
	 * Returns the KeyStroke for a key combined with the platform dependent menu
	 * shortcut key
	 */
	public static KeyStroke getAccelerator(int key) {
		return KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	}

	/**
	 * Returns the KeyStroke for a key combined with the platform dependent menu
	 * shortcut key and another key
	 */
	public static KeyStroke getAccelerator(int key, int combinator) {
		return KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | combinator);
	}

	/**
	 * Enables/Disables a component and all its children recursively
	 */
	public static void setEnabledRecursive(JComponent comp, boolean enabled, JComponent... except) {
		comp.setEnabled(enabled);
		for (Component child : comp.getComponents()) {
			if (!(Arrays.asList(except).contains(child))) {
				UiUtils.setEnabledRecursive((JComponent) child, enabled, except);
			}
		}
	}

	/**
	 * Enables/Disables a component and all its direct children
	 */
	public static void setEnabled(JComponent comp, boolean enabled, JComponent... except) {
		comp.setEnabled(enabled);
		for (Component child : comp.getComponents()) {
			if (!(Arrays.asList(except).contains(child))) {
				child.setEnabled(enabled);
			}
		}
	}

	/**
	 * Disable the tab key for text components.
	 */
	public static void disableTab(JTextComponent txtComp) {
		txtComp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		txtComp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
	}

	/**
	 * Creates a new {@link GridBagConstraints} with all parameters set to their
	 * standard value
	 */
	public static GridBagConstraints createGridBagConstraints() {
		return createGridBagConstraints(0, 0);
	}

	/**
	 * Creates a new {@link GridBagConstraints} with the given parameter
	 */
	public static GridBagConstraints createGridBagConstraints(int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		return gbc;
	}

	/**
	 * Creates a new {@link GridBagConstraints} with the given parameter
	 */
	public static GridBagConstraints createGridBagConstraints(int gridx, int gridy, int weightx, int weighty) {
		GridBagConstraints gbc = createGridBagConstraints(gridx, gridy);
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		return gbc;
	}

	/**
	 * Creates a new {@link GridBagConstraints} with the given parameter
	 */
	public static GridBagConstraints createGridBagConstraints(int gridx, int gridy, int weightx, int weighty, int anchor) {
		GridBagConstraints gbc = createGridBagConstraints(gridx, gridy, weightx, weighty);
		gbc.anchor = GridBagConstraints.WEST;
		return gbc;
	}

	/**
	 * Updates a {@link GridBagConstraints} with the given parameter. <br>
	 * {@link GridBagConstraints#weightx}, {@link GridBagConstraints#weighty}
	 * and {@link GridBagConstraints#anchor} are set to there standard values
	 */
	public static void editGridBagConstraints(GridBagConstraints gbc, int gridx, int gridy) {
		editGridBagConstraints(gbc, gridx, gridy, 0, 0);
	}

	/**
	 * Updates a {@link GridBagConstraints} with the given parameter. <br>
	 * {@link GridBagConstraints#anchor} is set to its standard value.
	 */
	public static void editGridBagConstraints(GridBagConstraints gbc, int gridx, int gridy, double weightx,
			double weighty) {
		editGridBagConstraints(gbc, gridx, gridy, weightx, weighty, GridBagConstraints.CENTER);
	}

	/**
	 * Updates a {@link GridBagConstraints} with the given parameter.
	 */
	public static void editGridBagConstraints(GridBagConstraints gbc, int gridx, int gridy, double weightx,
			double weighty, int anchor) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.anchor = anchor;
	}

}
