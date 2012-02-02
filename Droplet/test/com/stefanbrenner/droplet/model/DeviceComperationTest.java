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

/**
 * @author Stefan Brenner
 * 
 */
public class DeviceComperationTest {

	@Test
	public void testCompareOfDevices() {

		IValve valve1 = new Valve();
		IFlash flash1 = new Flash();
		ICamera camera1 = new Camera();

		assertEquals(0, valve1.compareTo(valve1));
		assertEquals(-1, valve1.compareTo(flash1));
		assertEquals(-1, valve1.compareTo(camera1));

		assertEquals(1, flash1.compareTo(valve1));
		assertEquals(0, flash1.compareTo(flash1));
		assertEquals(-1, flash1.compareTo(camera1));

		assertEquals(1, camera1.compareTo(valve1));
		assertEquals(1, camera1.compareTo(flash1));
		assertEquals(0, camera1.compareTo(camera1));

	}

	@Test
	public void testSortOrder() {
		// device counter
		int valves = 0, flashes = 0, cameras = 0;

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
			if (i % 3 == 1) {
				devices.add(new Flash());
				flashes++;
			}
		}
		// sort devices
		Collections.sort(devices);

		int i = 0;
		for (; i < valves; i++) {
			assertTrue(devices.get(i).getClass().equals(Valve.class));
		}
		for (; i < valves + flashes; i++) {
			assertTrue(devices.get(i).getClass().equals(Flash.class));
		}
		for (; i < valves + flashes + cameras; i++) {
			assertTrue(devices.get(i).getClass().equals(Camera.class));
		}
	}

	@Test
	public void testNewDevice() {

		@SuppressWarnings("serial")
		class NewDevice extends AbstractDevice {
			@Override
			public int compareTo(IDevice o) {
				if (o instanceof IValve) {
					return 1;
				} else if (o instanceof IFlash || o instanceof ICamera) {
					return -1;
				}
				return 0;
			}

			@Override
			public IAction createNewAction() {
				return null;
			}

			@Override
			protected String getDeviceType() {
				return null;
			}
		}

		// create new device between valve and flash
		IDevice newDevice = new NewDevice();

		IValve valve1 = new Valve();
		IFlash flash1 = new Flash();
		ICamera camera1 = new Camera();

		assertEquals(0, newDevice.compareTo(newDevice));
		assertEquals(1, newDevice.compareTo(valve1));
		assertEquals(-1, newDevice.compareTo(flash1));
		assertEquals(-1, newDevice.compareTo(camera1));

		assertEquals(-1, valve1.compareTo(newDevice));
		assertEquals(1, flash1.compareTo(newDevice));
		assertEquals(1, camera1.compareTo(newDevice));

		// device counter
		int valves = 0, flashes = 0, cameras = 0, newDevices = 0;

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
				devices.add(new NewDevice());
				newDevices++;
			}
			if (i % 3 == 1) {
				devices.add(new Flash());
				flashes++;
			}
		}
		// sort devices
		Collections.sort(devices);

		int i = 0;
		for (; i < valves; i++) {
			assertTrue(devices.get(i).getClass().equals(Valve.class));
		}
		for (; i < valves + newDevices; i++) {
			assertTrue(devices.get(i).getClass().equals(NewDevice.class));
		}
		for (; i < valves + newDevices + flashes; i++) {
			assertTrue(devices.get(i).getClass().equals(Flash.class));
		}
		for (; i < valves + newDevices + flashes + cameras; i++) {
			assertTrue(devices.get(i).getClass().equals(Camera.class));
		}

	}

	// TODO brenner: how can we compare two objects who's classes are unknown at
	// compile time
	@Test
	public void compareTwoUnknownObjects() {
		@SuppressWarnings("serial")
		class NewDevice1 extends AbstractDevice {
			@Override
			public int compareTo(IDevice o) {
				if (o instanceof IValve) {
					return 1;
				} else if (o instanceof IFlash || o instanceof ICamera) {
					return -1;
				}
				if (o instanceof NewDevice1) {
					return 0;
				}
				return -o.compareTo(this);
			}

			@Override
			public IAction createNewAction() {
				return null;
			}

			@Override
			protected String getDeviceType() {
				return null;
			}
		}
		@SuppressWarnings("serial")
		class NewDevice2 extends AbstractDevice {
			@Override
			public int compareTo(IDevice o) {
				if (o instanceof IValve) {
					return 1;
				} else if (o instanceof IFlash || o instanceof ICamera) {
					return -1;
				}
				if (o instanceof NewDevice2) {
					return 0;
				}
				return -o.compareTo(this);
			}

			@Override
			public IAction createNewAction() {
				return null;
			}

			@Override
			protected String getDeviceType() {
				return null;
			}
		}

		NewDevice1 newDevice1 = new NewDevice1();
		NewDevice2 newDevice2 = new NewDevice2();

		assertEquals(0, newDevice1.compareTo(newDevice2));
		assertEquals(0, newDevice2.compareTo(newDevice1));
	}

}
