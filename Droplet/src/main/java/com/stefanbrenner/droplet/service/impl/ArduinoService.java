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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.service.ISerialCommunicationService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Service for serial communication with an Arduino microcontroller.
 * <p>
 * For more information on Arduino see <a
 * href="http://arduino.cc">http://arduino.cc</a>
 *
 * @author Stefan Brenner
 */
@Slf4j
public class ArduinoService implements ISerialCommunicationService, SerialPortMessageListener {
	
	private static final char NEWLINE = '\n';
	
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	/** connected port information. **/
	private static SerialPort connSerialPort = null;
	
	/** output streams for receiving data. **/
	private static DataOutputStream output = null;
	
	private static IDropletContext dropletContext;
	
	@Override
	public String getName() {
		return "Arduino Service";
	}
	
	@Override
	public Set<String> getPorts() {
		return Arrays.asList(SerialPort.getCommPorts()) //
				.stream().map(SerialPort::getSystemPortPath) //
				.collect(Collectors.toSet());
	}
	
	@Override
	public synchronized boolean connect(final String portId, final IDropletContext context) {
		dropletContext = context;
		
		log.debug("Connect to port: " + portId);
		
		// try to open a connection to the serial port
		var connSerialPortOpt = Arrays.asList(SerialPort.getCommPorts()) //
				.stream() //
				.filter(p -> StringUtils.equals(p.getSystemPortPath(), portId)) //
				.findFirst();
		
		if (connSerialPortOpt.isEmpty()) {
			log.error("Error connecting to port {}", portId);
			return false;
		}
		
		connSerialPort = connSerialPortOpt.get();
		connSerialPort.setBaudRate(DATA_RATE);
		
		if (!connSerialPort.openPort()) {
			log.error("Error connecting to port {}", portId);
			return false;
		}
		
		// open the streams
		output = new DataOutputStream(connSerialPort.getOutputStream());
		
		// add event listeners
		connSerialPort.addDataListener(this);
		
		log.info("Connection to port " + portId + " successful established");
		
		return true;
	}
	
	@Override
	public synchronized void close() {
		if (connSerialPort != null) {
			
			log.debug("close connection to port {}", connSerialPort.getPortDescription());
			
			connSerialPort.removeDataListener();
			connSerialPort.closePort();
		}
	}
	
	@Override
	public void serialEvent(final SerialPortEvent event) {
		byte[] newData = event.getReceivedData();
		int numRead = newData.length;
		
		String message = new String(newData, 0, numRead).trim();
		
		log.debug("Received message: {}", message);
		
		dropletContext.addLoggingMessage(message);
	}
	
	// TODO brenner: implement a two way synchronous communication protocol
	@Override
	public void sendData(final String message) {
		try {
			if (output != null) {
				
				// split message by newline
				for (String msg : StringUtils.split(message, NEWLINE)) {
					
					log.debug("send message: {}", msg);
					
					// send byte by byte
					// for (byte b : msg.getBytes()) {
					// output.write(b);
					// output.flush();
					// }
					output.write(msg.getBytes());
					
					// finally send newline
					output.write(NEWLINE);
					output.flush();
					
					// wait for Arduino to process input before we send next
					// message
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					
				}
				
				// remove control characters
				// message = StringUtils.trim(message);
				// output.write(message.getBytes());
				// append newline
				// if (!message.endsWith("\n")) {
				// output.write('\n');
				// }
				// output.flush();
			} else {
				throw new RuntimeException("Not connected to a port!"); //$NON-NLS-1$
			}
		} catch (IOException e) {
			log.error("Error sending data", e);
		}
	}
	
	@Override
	public synchronized boolean isConnected() {
		return connSerialPort != null && connSerialPort.isOpen();
	}
	
	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
	}
	
	@Override
	public byte[] getMessageDelimiter() {
		return new byte[] { '\n' };
	}
	
	@Override
	public boolean delimiterIndicatesEndOfMessage() {
		return true;
	}
	
}
