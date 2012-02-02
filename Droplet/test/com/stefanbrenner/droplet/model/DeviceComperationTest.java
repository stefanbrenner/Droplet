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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.stefanbrenner.droplet.model.internal.AbstractDevice;
import com.stefanbrenner.droplet.model.internal.Camera;
import com.stefanbrenner.droplet.model.internal.Flash;
import com.stefanbrenner.droplet.model.internal.Valve;
import com.stefanbrenner.droplet.service.DropletDeviceComparator;

/**
 * @author Stefan Brenner
 * 
 */
public class DeviceComperationTest {

	@SuppressWarnings("serial")
	class NewDevice1 extends AbstractDevice {
		@Override
		public IAction createNewAction() {
			return null;
		}

		@Override
		protected String getDeviceType() {
			return null;
		}
	}

	class NewDevice1Comparator extends DropletDeviceComparator {
		/**
		 * Valve < NewDevice1 < ALL
		 */
		@Override
		public int compare(IDevice device1, IDevice device2) {
			if (device1 instanceof NewDevice1) {
				if (device2 instanceof NewDevice1) {
					return UNDEFINED_ORDER;
				}
				if (device2 instanceof IValve) {
					return GREATER;
				}
				return SMALLER;
			}
			if (device2 instanceof NewDevice1) {
				if (device1 instanceof IValve) {
					return SMALLER;
				}
				return GREATER;
			}
			return UNDEFINED_ORDER;
		}
	}

	@SuppressWarnings("serial")
	class NewDevice2 extends AbstractDevice {
		@Override
		public IAction createNewAction() {
			return null;
		}

		@Override
		protected String getDeviceType() {
			return null;
		}
	}

	class NewDevice2Comparator extends DropletDeviceComparator {
		/**
		 * ALL < NewDevice2 < Camera
		 */
		@Override
		public int compare(IDevice device1, IDevice device2) {
			if (device1 instanceof NewDevice2) {
				if (device2 instanceof NewDevice2) {
					return UNDEFINED_ORDER;
				}
				if (device2 instanceof ICamera) {
					return SMALLER;
				}
				return GREATER;
			}
			if (device2 instanceof NewDevice2) {
				if (device1 instanceof ICamera) {
					return GREATER;
				}
				return SMALLER;
			}
			return UNDEFINED_ORDER;
		}
	}

	@Test
	public void testDropletDeviceComparator() {

		IValve valve1 = new Valve();
		IFlash flash1 = new Flash();
		ICamera camera1 = new Camera();

		// normal comparator
		DropletDeviceComparator comp1 = new DropletDeviceComparator();

		assertEquals(0, comp1.compare(valve1, valve1));
		assertEquals(-1, comp1.compare(valve1, flash1));
		assertEquals(-1, comp1.compare(valve1, camera1));

		assertEquals(1, comp1.compare(flash1, valve1));
		assertEquals(0, comp1.compare(flash1, flash1));
		assertEquals(-1, comp1.compare(flash1, camera1));

		assertEquals(1, comp1.compare(camera1, valve1));
		assertEquals(1, comp1.compare(camera1, flash1));
		assertEquals(0, comp1.compare(camera1, camera1));

		comp1.registerComparator(new NewDevice1Comparator());

		NewDevice1 newDevice1 = new NewDevice1();

		assertEquals(0, comp1.compare(newDevice1, newDevice1));
		assertEquals(1, comp1.compare(newDevice1, valve1));
		assertEquals(-1, comp1.compare(newDevice1, flash1));
		assertEquals(-1, comp1.compare(newDevice1, camera1));
		assertEquals(-1, comp1.compare(valve1, newDevice1));
		assertEquals(1, comp1.compare(flash1, newDevice1));
		assertEquals(1, comp1.compare(camera1, newDevice1));

		comp1.registerComparator(new NewDevice2Comparator());

		NewDevice2 newDevice2 = new NewDevice2();

		assertEquals(0, comp1.compare(newDevice2, newDevice2));
		assertEquals(1, comp1.compare(newDevice2, valve1));
		assertEquals(1, comp1.compare(newDevice2, flash1));
		assertEquals(-1, comp1.compare(newDevice2, camera1));
		assertEquals(-1, comp1.compare(valve1, newDevice2));
		assertEquals(-1, comp1.compare(flash1, newDevice2));
		assertEquals(1, comp1.compare(camera1, newDevice2));

		// now compare both unknown objects
		assertEquals(-1, comp1.compare(newDevice1, newDevice2));
		assertEquals(1, comp1.compare(newDevice2, newDevice1));

	}

	@Test
	public void testDropletDeviceComparatorSorting() {
		// counters
		int valves = 0, flashes = 0, cameras = 0, newDevices1 = 0, newDevices2 = 0;

		List<IDevice> devices = new ArrayList<IDevice>();
		for (int i = 0; i < 100; i++) {
			if (i % 1 == 0) {
				devices.add(new Valve());
				valves++;
			}
			if (i % 2 == 0) {
				devices.add(new Camera());
				cameras++;
			}
			if (i % 2 == 1) {
				devices.add(new NewDevice1());
				newDevices1++;
			}
			if (i % 3 == 1) {
				devices.add(new Flash());
				flashes++;
			}
			if (i % 4 == 1) {
				devices.add(new NewDevice2());
				newDevices2++;
			}
		}
		// sort devices
		// order should be valve < newDevice1 < flash < newDevice2 < camera
		DropletDeviceComparator comp1 = new DropletDeviceComparator();
		comp1.registerComparator(new NewDevice1Comparator());
		comp1.registerComparator(new NewDevice2Comparator());
		Collections.sort(devices, comp1);

		int i = 0, j = 0;
		for (j += valves; i < valves; i++) {
			assertTrue(devices.get(i).getClass().equals(Valve.class));
		}
		for (j += newDevices1; i < j; i++) {
			assertTrue(devices.get(i).getClass().equals(NewDevice1.class));
		}
		for (j += flashes; i < j; i++) {
			assertTrue(devices.get(i).getClass().equals(Flash.class));
		}
		for (j += newDevices2; i < j; i++) {
			assertTrue(devices.get(i).getClass().equals(NewDevice2.class));
		}
		for (j += cameras; i < j; i++) {
			assertTrue(devices.get(i).getClass().equals(Camera.class));
		}
	}

}
