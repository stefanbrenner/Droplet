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
package com.stefanbrenner.droplet.service.impl;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.model.internal.Action;
import com.stefanbrenner.droplet.model.internal.Camera;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.model.internal.DurationAction;
import com.stefanbrenner.droplet.model.internal.Flash;
import com.stefanbrenner.droplet.model.internal.Valve;
import com.stefanbrenner.droplet.service.IDropletMessageProtocol;

/**
 * @author Stefan Brenner
 * 
 */
public class DropletMessageProtocolV2Test {

	@Test
	public void testCreateStartMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();
		assertEquals("R;1^1", protocol.createStartMessage());
		assertEquals("R;1^1", protocol.createStartMessage(1, 0));
		assertEquals("R;1^1", protocol.createStartMessage(1, 250));
		assertEquals("R;5^5", protocol.createStartMessage(5, 0));
		assertEquals("R;5;250^255", protocol.createStartMessage(5, 250));
	}

	@Test
	public void testCreateInfoMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();
		assertEquals("I", protocol.createInfoMessage());
	}

	@Test
	public void testCreateResetMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();
		assertEquals("X", protocol.createResetMessage());
	}

	@Test
	public void testCreateSetMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();

		IDroplet droplet = new Droplet();

		// devices
		IValve valve1 = new Valve();
		IValve valve2 = new Valve();
		IFlash flash1 = new Flash();
		IFlash flash2 = new Flash();
		ICamera camera1 = new Camera();
		ICamera camera2 = new Camera();

		// actions
		DurationAction action1 = new DurationAction();
		DurationAction action2 = new DurationAction();
		DurationAction action3 = new DurationAction();
		Action action4 = new Action();
		Action action5 = new Action();

		// empty droplet
		assertEquals("", protocol.createSetMessage(droplet));

		droplet.addDevice(valve1);
		droplet.addDevice(valve2);

		// droplet with empty valves
		assertEquals("", protocol.createSetMessage(droplet));

		droplet.addDevice(flash1);
		droplet.addDevice(flash2);

		// droplet with empty flashes
		assertEquals("", protocol.createSetMessage(droplet));

		droplet.addDevice(camera1);
		droplet.addDevice(camera2);

		// droplet with empty cameras
		assertEquals("", protocol.createSetMessage(droplet));

		valve1.addAction(action1);
		valve1.addAction(action2);
		valve1.addAction(action3);

		assertEquals("S;1;V;0|0;0|0;0|0^0\n", protocol.createSetMessage(droplet));

		valve2.addAction(action3);
		valve2.addAction(action2);

		assertEquals("S;1;V;0|0;0|0;0|0^0\nS;2;V;0|0;0|0^0\n", protocol.createSetMessage(droplet));

		flash2.addAction(action4);
		flash2.addAction(action5);

		assertEquals("S;1;V;0|0;0|0;0|0^0\nS;2;V;0|0;0|0^0\nS;4;F;0;0^0\n", protocol.createSetMessage(droplet));

		camera1.addAction(action2);

		// droplet with actions that have no times
		assertEquals("S;1;V;0|0;0|0;0|0^0\nS;2;V;0|0;0|0^0\nS;4;F;0;0^0\nS;5;C;0|0^0\n",
				protocol.createSetMessage(droplet));

		action1.setOffset(0);
		action1.setDuration(20);
		action2.setOffset(20);
		action2.setDuration(40);
		action3.setOffset(80);
		action3.setDuration(100);
		action4.setOffset(5);
		action5.setOffset(105);

		// droplet with actions that have times
		assertEquals("S;1;V;0|20;20|40;80|100^260\nS;2;V;80|100;20|40^240\nS;4;F;5;105^110\nS;5;C;20|40^60\n",
				protocol.createSetMessage(droplet));

		// devices can have all types of actions
		camera2.addAction(action1);
		camera2.addAction(action5);

		assertEquals(
				"S;1;V;0|20;20|40;80|100^260\nS;2;V;80|100;20|40^240\nS;4;F;5;105^110\nS;5;C;20|40^60\nS;6;C;0|20;105^125\n",
				protocol.createSetMessage(droplet));

		// test disabled actions
		action2.setEnabled(false);

		assertEquals("S;1;V;0|20;80|100^200\nS;2;V;80|100^180\nS;4;F;5;105^110\nS;6;C;0|20;105^125\n",
				protocol.createSetMessage(droplet));

	}

	@Test
	public void testCreateOnMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();

		IDroplet droplet = new Droplet();

		// devices
		IValve valve1 = new Valve();
		IValve valve2 = new Valve();
		IFlash flash1 = new Flash();
		ICamera camera1 = new Camera();

		droplet.addDevice(valve1);
		droplet.addDevice(valve2);
		droplet.addDevice(flash1);
		droplet.addDevice(camera1);

		assertEquals("H;1", protocol.createDeviceOnMessage(droplet, valve1));
		assertEquals("H;2", protocol.createDeviceOnMessage(droplet, valve2));
		assertEquals("H;3", protocol.createDeviceOnMessage(droplet, flash1));
		assertEquals("H;4", protocol.createDeviceOnMessage(droplet, camera1));

	}

	@Test
	public void testCreateOffMessage() {
		IDropletMessageProtocol protocol = new DropletMessageProtocol();

		IDroplet droplet = new Droplet();

		// devices
		IValve valve1 = new Valve();
		IValve valve2 = new Valve();
		IFlash flash1 = new Flash();
		ICamera camera1 = new Camera();

		droplet.addDevice(valve1);
		droplet.addDevice(valve2);
		droplet.addDevice(flash1);
		droplet.addDevice(camera1);

		assertEquals("L;1", protocol.createDeviceOffMessage(droplet, valve1));
		assertEquals("L;2", protocol.createDeviceOffMessage(droplet, valve2));
		assertEquals("L;3", protocol.createDeviceOffMessage(droplet, flash1));
		assertEquals("L;4", protocol.createDeviceOffMessage(droplet, camera1));
	}

}
