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

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.service.ISerialCommService;

/**
 * <p>
 * Service for serial communication with an Arduino microcontroller.
 * <p>
 * For more information on Arduino see {@link http://arduino.cc}
 * 
 * @author Stefan Brenner
 */
public class ArduinoService implements ISerialCommService, SerialPortEventListener {

	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port */
	private static final int DATA_RATE = 9600;

	/** connected port information **/
	private CommPortIdentifier connPortId = null;
	private SerialPort connSerialPort = null;

	/** input stream for sending data **/
	private InputStream input = null;
	/** output streams for receiving data **/
	private OutputStream output = null;

	/** flag that indicates if the service is currently connected to a port **/
	private boolean connected = false;

	private static final ArduinoService instance = new ArduinoService();

	private ArduinoService() {

	}

	public static ArduinoService getInstance() {
		return instance;
	}

	@Override
	public CommPortIdentifier[] getPorts() {
		List<CommPortIdentifier> ports = new ArrayList<CommPortIdentifier>();

		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			// add only serial ports
			if (currPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				ports.add(currPortId);
			}
		}

		return ports.toArray(new CommPortIdentifier[] {});
	}

	@Override
	public synchronized boolean connect(CommPortIdentifier portId) {
		try {
			connPortId = portId;
			// try to open a connection to the serial port
			connSerialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			connSerialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// set connected flag
			setConnected(true);

			// open the streams
			input = connSerialPort.getInputStream();
			output = connSerialPort.getOutputStream();

			// add event listeners
			connSerialPort.addEventListener(this);
			connSerialPort.notifyOnDataAvailable(true);

		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isConnected();
	}

	@Override
	public synchronized void close() {
		try {
			if (connSerialPort != null) {
				connSerialPort.removeEventListener();
				connSerialPort.close();
				input.close();
				output.close();
				setConnected(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	// TODO brenner: move listener directly to UI ?
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);

				// TODO brenner: handle resultMessage to UI
				String resultMessage = StringUtils.chomp(new String(chunk));
				System.out.print(resultMessage);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// TODO brenner: implement a two way synchronous communication protocol
	@Override
	public void sendData(String message) {
		try {
			if (output != null) {
				output.write(message.getBytes());
				output.flush();
			} else {
				throw new RuntimeException("Not connected to a port!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized boolean isConnected() {
		return connected;
	}

	private void setConnected(boolean connected) {
		this.connected = connected;
	}

}
