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
package com.stefanbrenner.droplet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SpinnerAdapterFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IActionDevice;
import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.ui.components.MouseWheelSpinner;
import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Panel for action devices.
 * 
 * @param <T>
 *            type ot the action devices to be displayed in this panel
 * @author Stefan Brenner
 */
public class DevicePanel<T extends IDevice> extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// model objects
	T device;
	
	// UI components
	final JComponent parent;
	final JCheckBox cbEnable;
	final JTextField txtNumber;
	final JTextField txtName;
	
	final JPanel actionsPanel;
	
	/**
	 * Create the panel.
	 */
	public DevicePanel(final JComponent parent, final IDropletContext dropletContext, final T device) {
		
		this.parent = parent;
		
		setDevice(device);
		
		setLayout(new BorderLayout(0, 5));
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		BeanAdapter<T> adapter = new BeanAdapter<T>(device, true);
		
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, DropletColors.LIGHT_GRAY));
		headerPanel.setBackground(DropletColors.WHITE);
		
		JPanel numberColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		numberColorPanel.setOpaque(false);
		
		cbEnable = BasicComponentFactory.createCheckBox(adapter.getValueModel(IDevice.PROPERTY_ENABLED),
				StringUtils.EMPTY);
		cbEnable.setToolTipText(Messages.getString("DevicePanel.enableAction.tooltip")); //$NON-NLS-1$
		cbEnable.setFocusable(false);
		numberColorPanel.add(cbEnable);
		
		// device port
		txtNumber = BasicComponentFactory.createIntegerField(adapter.getValueModel(IDevice.PROPERTY_NUMBER));
		txtNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumber.setColumns(2);
		txtNumber.setToolTipText(Messages.getString("DevicePanel.number.tooltip"));
		numberColorPanel.add(txtNumber);
		
		// device color
		if (device instanceof IValve) {
			JButton colorPanel = new JButton();
			colorPanel.setBackground(((IValve) device).getColor());
			colorPanel.setOpaque(true);
			colorPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			colorPanel.setPreferredSize(new Dimension(15, 15));
			colorPanel.setToolTipText(Messages.getString("DevicePanel.color.tooltip")); //$NON-NLS-1$
			colorPanel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					Color c = JColorChooser.showDialog(null, "Choose a Color", ((IValve) device).getColor());
					if (c != null) {
						((IValve) device).setColor(c);
					}
				}
			});
			device.addPropertyChangeListener(IValve.PROPERTY_COLOR, new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					colorPanel.setBackground(((IValve) device).getColor());
				}
			});
			numberColorPanel.add(colorPanel, BorderLayout.LINE_START);
		}
		headerPanel.add(numberColorPanel, BorderLayout.LINE_START);
		
		// device name textfield
		txtName = BasicComponentFactory.createTextField(adapter.getValueModel(IDevice.PROPERTY_NAME));
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setColumns(1);
		txtName.setToolTipText(device.getName());
		adapter.addBeanPropertyChangeListener(IDevice.PROPERTY_NAME, new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				txtName.setToolTipText(device.getName());
			}
		});
		headerPanel.add(txtName, BorderLayout.CENTER);
		
		// device calibration value
		if (device instanceof IActionDevice) {
			MouseWheelSpinner calibration = new MouseWheelSpinner(true);
			calibration.setModel(SpinnerAdapterFactory
					.createNumberAdapter(adapter.getValueModel(IActionDevice.PROPERTY_CALIBRATION), 0, -999, 999, 1));
			((JSpinner.DefaultEditor) calibration.getEditor()).getTextField().setColumns(3);
			calibration.setToolTipText(Messages.getString("DevicePanel.calibration.tooltip")); //$NON-NLS-1$
			headerPanel.add(calibration, BorderLayout.LINE_END);
		}
		add(headerPanel, BorderLayout.NORTH);
		
		// actions panel with scroll pane
		actionsPanel = new JPanel();
		actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(actionsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// resize vertical scrollbar
		scrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		SwingUtilities.updateComponentTreeUI(scrollPane);
		// we need no border
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		add(scrollPane, BorderLayout.CENTER);
		
		add(new DevicePanelToolbar(dropletContext, device), BorderLayout.SOUTH);
		
		initializeListeners();
		updateBackgroundColor();
	}
	
	void initializeListeners() {
		device.addPropertyChangeListener(IDevice.PROPERTY_ENABLED, e -> updateBackgroundColor());
		device.addPropertyChangeListener(IDevice.PROPERTY_ENABLED, e -> {
			UiUtils.setEnabledRecursive(this, device.isEnabled(), cbEnable);
		});
	}
	
	void updateBackgroundColor() {
		Color bgColor = device.isEnabled() ? DropletColors.getBackgroundColor(device) : DropletColors.LIGHT_GRAY;
		setBackground(bgColor);
		actionsPanel.setBackground(getBackground());
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
	
	public void setDevice(final T device) {
		this.device = device;
	}
	
}
