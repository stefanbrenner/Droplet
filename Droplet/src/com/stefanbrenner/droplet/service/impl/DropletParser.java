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

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IDurationAction;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.service.ISerialCommService;

/**
 * @author stefan
 * 
 */
public class DropletParser {

	/*
	 * Process a given command. run - start one run set111222333444555666 - set
	 * times in ms 111 ... duration 1 222 ... delay 1 333 ... duration 2 444 ...
	 * delay 2 555 ... duration 3 666 ... delay camera show - show time settings
	 * open - open valve 1 close - close valve 1
	 */

	// TODO brenner: REMOVE this stupid silly parser
	public static void sendConfiguration(IDroplet droplet, ISerialCommService commService) {

		String message = "set";

		IValve valve1 = droplet.getValves().get(0);

		IDurationAction action1 = valve1.getActions().get(0);

		int dur1 = action1.getDuration();

		IDurationAction action2 = valve1.getActions().get(1);

		int del1 = Math.abs(action2.getOffset() - action1.getOffset());
		int dur2 = action2.getDuration();

		IDurationAction action3 = valve1.getActions().get(2);

		int del2 = Math.abs(action3.getOffset() - action2.getOffset());
		int dur3 = action3.getDuration();

		int delCam = Math.abs(droplet.getCameras().get(0).getActions().get(0).getOffset() - action3.getOffset());

		message += StringUtils.leftPad(String.valueOf(dur1), 3, "0");
		message += StringUtils.leftPad(String.valueOf(del1), 3, "0");
		message += StringUtils.leftPad(String.valueOf(dur2), 3, "0");
		message += StringUtils.leftPad(String.valueOf(del2), 3, "0");
		message += StringUtils.leftPad(String.valueOf(dur3), 3, "0");
		message += StringUtils.leftPad(String.valueOf(delCam), 3, "0");

		commService.sendData(message + "\n");

	}

	public static void start(ISerialCommService commService) {
		commService.sendData("run\n");
	}

	public static void show(ISerialCommService commService) {
		commService.sendData("show\n");
	}

}
