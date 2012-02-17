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
package com.stefanbrenner.droplet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IAction;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.utils.DropletColors;

public class ActionDevicePanel<T extends IActionDevice> extends JPanel {

	private static final long serialVersionUID = 1L;

	// model objects
	private T device;

	// UI components
	private final JComponent parent;
	private final JTextField txtName;

	private JPanel actionsPanel;

	/**
	 * Create the panel.
	 */
	public ActionDevicePanel(final JComponent parent, final IDroplet droplet, final T device) {

		this.parent = parent;

		setDevice(device);

		setLayout(new BorderLayout(0, 5));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(DropletColors.getBackgroundColor(device));

		BeanAdapter<T> adapter = new BeanAdapter<T>(device, true);

		// device name textfield
		txtName = BasicComponentFactory.createTextField(adapter.getValueModel(IValve.PROPERTY_NAME));
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setColumns(1);
		txtName.setToolTipText(device.getName());
		txtName.setFocusable(false);
		adapter.addBeanPropertyChangeListener(IDevice.PROPERTY_NAME, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				txtName.setToolTipText(device.getName());
			}
		});
		add(txtName, BorderLayout.NORTH);

		// actions panel with scroll pane
		actionsPanel = new JPanel();
		actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
		actionsPanel.setBackground(getBackground());

		JScrollPane scrollPane = new JScrollPane(actionsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// resize vertical scrollbar
		scrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		SwingUtilities.updateComponentTreeUI(scrollPane);
		// we need no border
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);

		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(0, 1));

			// add button
			JButton btnAdd = new JButton(Messages.getString("ActionDevicePanel.addAction")); //$NON-NLS-1$
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent action) {
					addAction(getDevice().createNewAction());
				}
			});
			btnAdd.setFocusable(false);
			panel.add(btnAdd);

			// remove button
			JButton btnRemove = new JButton(Messages.getString("ActionDevicePanel.removeDevice")); //$NON-NLS-1$
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent action) {
					int retVal = JOptionPane.showConfirmDialog(
							ActionDevicePanel.this,
							Messages.getString("ActionDevicePanel.removeDevice") + " '" + device.getName() + "'?", StringUtils.EMPTY, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (retVal == JOptionPane.YES_OPTION) {
						droplet.removeDevice(device);
					}
				}
			});
			btnRemove.setFocusable(false);
			panel.add(btnRemove);

			add(panel, BorderLayout.SOUTH);
		}

		updateActionsPanel();

		initializeListeners();

	}

	private void initializeListeners() {
		device.addPropertyChangeListener(IValve.ASSOCIATION_ACTIONS, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				updateActionsPanel();
			}
		});
	}

	private void addAction(IAction action) {
		device.addAction(action);
	}

	private void updateActionsPanel() {
		// remove all components
		actionsPanel.removeAll();
		// add components for each action
		for (IAction action : device.getActions()) {
			ActionPanel<IAction> valveActionPanel = new ActionPanel<IAction>(device, action);
			actionsPanel.add(valveActionPanel);
		}
		// add fill
		actionsPanel.add(Box.createVerticalGlue());
		// redraw panel
		actionsPanel.revalidate();
		actionsPanel.repaint();
		// update parent if we add the first action or remove the last action
		if (device.getActions().size() < 2) {
			parent.revalidate();
			parent.repaint();
		}
	}

	@Override
	public Dimension getMaximumSize() {
		Dimension size = getPreferredSize();
		size.height = Short.MAX_VALUE;
		return size;
	}

	public T getDevice() {
		return device;
	}

	public void setDevice(T device) {
		this.device = device;
	}

}
