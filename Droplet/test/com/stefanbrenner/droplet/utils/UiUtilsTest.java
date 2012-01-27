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

import static junit.framework.Assert.assertEquals;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

/**
 * @author Stefan Brenner
 */
public class UiUtilsTest {

	@Test
	public void testSetEnabledRecursive() {
		JPanel testPanel = new JPanel();

		JButton btnTest = new JButton();
		testPanel.add(btnTest);
		JTextField txtTest = new JTextField();
		testPanel.add(txtTest);

		JPanel testPanel2 = new JPanel();
		JCheckBox chTest = new JCheckBox();
		testPanel2.add(chTest);

		testPanel.add(testPanel2);

		List<JComponent> allComps = Arrays.asList(new JComponent[] { testPanel, btnTest, txtTest, testPanel2, chTest });

		// disable all components
		UiUtils.setEnabledRecursive(testPanel, false);
		for (JComponent comp : allComps) {
			assertEquals(false, comp.isEnabled());
		}
		// re-enable all components
		UiUtils.setEnabledRecursive(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except txtTest
		UiUtils.setEnabledRecursive(testPanel, false, txtTest);
		for (JComponent comp : allComps) {
			if (ObjectUtils.notEqual(comp, txtTest)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabledRecursive(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except chTest in testPanel2
		UiUtils.setEnabledRecursive(testPanel, false, chTest);
		for (JComponent comp : allComps) {
			if (ObjectUtils.notEqual(comp, chTest)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabledRecursive(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except txtTest in testPanel and chTest in testPanel2
		JComponent[] except = new JComponent[] { txtTest, chTest };
		UiUtils.setEnabledRecursive(testPanel, false, except);
		for (JComponent comp : allComps) {
			if (!Arrays.asList(except).contains(comp)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabledRecursive(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

	}

	@Test
	public void testSetEnabled() {
		JPanel testPanel = new JPanel();

		JButton btnTest = new JButton();
		testPanel.add(btnTest);
		JTextField txtTest = new JTextField();
		testPanel.add(txtTest);

		JPanel testPanel2 = new JPanel();
		JCheckBox chTest = new JCheckBox();
		testPanel2.add(chTest);

		testPanel.add(testPanel2);

		List<JComponent> allComps = Arrays.asList(new JComponent[] { testPanel, btnTest, txtTest, testPanel2, chTest });
		List<JComponent> childComps = Arrays.asList(new JComponent[] { testPanel, btnTest, txtTest, testPanel2 });

		// disable all components
		UiUtils.setEnabled(testPanel, false);
		for (JComponent comp : allComps) {
			if (childComps.contains(comp)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabled(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except txtTest
		UiUtils.setEnabled(testPanel, false, txtTest);
		for (JComponent comp : allComps) {
			if (childComps.contains(comp) && ObjectUtils.notEqual(comp, txtTest)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabled(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except chTest in testPanel2
		UiUtils.setEnabled(testPanel, false, chTest);
		for (JComponent comp : allComps) {
			if (childComps.contains(comp) && ObjectUtils.notEqual(comp, chTest)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabled(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

		// disable all except txtTest in testPanel and chTest in testPanel2
		JComponent[] except = new JComponent[] { txtTest, chTest };
		UiUtils.setEnabled(testPanel, false, except);
		for (JComponent comp : allComps) {
			if (childComps.contains(comp) && !Arrays.asList(except).contains(comp)) {
				assertEquals(false, comp.isEnabled());
			} else {
				assertEquals(true, comp.isEnabled());
			}
		}
		// re-enable all components
		UiUtils.setEnabled(testPanel, true);
		for (JComponent comp : allComps) {
			assertEquals(true, comp.isEnabled());
		}

	}

	@Test
	public void testCreateGridBagConstraints() {

		GridBagConstraints gbc = UiUtils.createGridBagConstraints(3, 4);
		assertEquals(3, gbc.gridx);
		assertEquals(4, gbc.gridy);
		assertEquals(1, gbc.gridwidth);
		assertEquals(1, gbc.gridheight);
		assertEquals(0.0, gbc.weightx);
		assertEquals(0.0, gbc.weighty);
		assertEquals(GridBagConstraints.CENTER, gbc.anchor);
		assertEquals(GridBagConstraints.NONE, gbc.fill);

		gbc = UiUtils.createGridBagConstraints(5, 6, 7, 8);
		assertEquals(5, gbc.gridx);
		assertEquals(6, gbc.gridy);
		assertEquals(1, gbc.gridwidth);
		assertEquals(1, gbc.gridheight);
		assertEquals(7.0, gbc.weightx);
		assertEquals(8.0, gbc.weighty);
		assertEquals(GridBagConstraints.CENTER, gbc.anchor);
		assertEquals(GridBagConstraints.NONE, gbc.fill);

		gbc = UiUtils.createGridBagConstraints(9, 10, 11, 12, GridBagConstraints.WEST);
		assertEquals(9, gbc.gridx);
		assertEquals(10, gbc.gridy);
		assertEquals(1, gbc.gridwidth);
		assertEquals(1, gbc.gridheight);
		assertEquals(11.0, gbc.weightx);
		assertEquals(12.0, gbc.weighty);
		assertEquals(GridBagConstraints.WEST, gbc.anchor);
		assertEquals(GridBagConstraints.NONE, gbc.fill);

	}

	@Test
	public void testEditGridBagConstraints() {
		GridBagConstraints gbc = new GridBagConstraints(1, 2, 3, 4, 5.0, 6.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(1, 2, 3, 4), 9, 0);

		UiUtils.editGridBagConstraints(gbc, 11, 12);
		assertEquals(11, gbc.gridx);
		assertEquals(12, gbc.gridy);
		assertEquals(3, gbc.gridwidth);
		assertEquals(4, gbc.gridheight);
		assertEquals(0.0, gbc.weightx);
		assertEquals(0.0, gbc.weighty);
		assertEquals(GridBagConstraints.CENTER, gbc.anchor);
		assertEquals(GridBagConstraints.BOTH, gbc.fill);

		UiUtils.editGridBagConstraints(gbc, 13, 14, 15.0, 16.0);
		assertEquals(13, gbc.gridx);
		assertEquals(14, gbc.gridy);
		assertEquals(3, gbc.gridwidth);
		assertEquals(4, gbc.gridheight);
		assertEquals(15.0, gbc.weightx);
		assertEquals(16.0, gbc.weighty);
		assertEquals(GridBagConstraints.CENTER, gbc.anchor);
		assertEquals(GridBagConstraints.BOTH, gbc.fill);

		UiUtils.editGridBagConstraints(gbc, 17, 18, 19.0, 20.0, GridBagConstraints.WEST);
		assertEquals(17, gbc.gridx);
		assertEquals(18, gbc.gridy);
		assertEquals(3, gbc.gridwidth);
		assertEquals(4, gbc.gridheight);
		assertEquals(19.0, gbc.weightx);
		assertEquals(20.0, gbc.weighty);
		assertEquals(GridBagConstraints.WEST, gbc.anchor);
		assertEquals(GridBagConstraints.BOTH, gbc.fill);

	}

}
