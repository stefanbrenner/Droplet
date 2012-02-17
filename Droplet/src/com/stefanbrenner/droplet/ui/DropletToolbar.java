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

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import com.jgoodies.binding.adapter.SpinnerAdapterFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.ui.actions.CancelAction;
import com.stefanbrenner.droplet.ui.actions.SendAction;
import com.stefanbrenner.droplet.ui.actions.ShowAction;
import com.stefanbrenner.droplet.ui.actions.StartAction;
import com.stefanbrenner.droplet.ui.components.MouseWheelSpinner;

// TODO brenner: use JToolBar
public class DropletToolbar extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DropletToolbar(JFrame frame, IDropletContext dropletContext) {

		setLayout(new FlowLayout(FlowLayout.RIGHT));

		// show button
		JButton btnShow = new JButton(new ShowAction(frame, dropletContext));
		add(btnShow);

		// send button
		JButton btnSend = new JButton(new SendAction(frame, dropletContext));
		add(btnSend);

		BeanAdapter<IDroplet> adapter = new BeanAdapter<IDroplet>(dropletContext.getDroplet(), true);

		// rounds spinner
		add(new JLabel(Messages.getString("DropletToolbar.rounds"))); //$NON-NLS-1$
		JSpinner spRounds = new MouseWheelSpinner(true);
		spRounds.setModel(SpinnerAdapterFactory.createNumberAdapter(adapter.getValueModel(IDroplet.PROPERTY_ROUNDS), 1,
				1, 9999, 1));
		((JSpinner.DefaultEditor) spRounds.getEditor()).getTextField().setColumns(4);
		add(spRounds);

		// rounds spinner
		add(new JLabel(Messages.getString("DropletToolbar.delay"))); //$NON-NLS-1$
		JSpinner spRoundDelay = new MouseWheelSpinner(true);
		spRoundDelay.setModel(SpinnerAdapterFactory.createNumberAdapter(
				adapter.getValueModel(IDroplet.PROPERTY_ROUND_DELAY), 1000, 1, 999999, 1));
		((JSpinner.DefaultEditor) spRoundDelay.getEditor()).getTextField().setColumns(6);
		add(spRoundDelay);

		// start button
		JButton btnStart = new JButton(new StartAction(frame, dropletContext));
		add(btnStart);

		// cancel button
		JButton btnCancel = new JButton(new CancelAction(frame, dropletContext));
		add(btnCancel);

	}

}
