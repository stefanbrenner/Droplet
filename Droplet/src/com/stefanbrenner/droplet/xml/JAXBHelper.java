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
package com.stefanbrenner.droplet.xml;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.model.internal.Action;
import com.stefanbrenner.droplet.model.internal.Camera;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.model.internal.DurationAction;
import com.stefanbrenner.droplet.model.internal.Flash;
import com.stefanbrenner.droplet.model.internal.Valve;
import com.stefanbrenner.droplet.utils.DropletColors;

/**
 * @author Stefan Brenner
 */
public class JAXBHelper {

	/** Output encoding of marshalled XML data. */
	private static final String ENCODING = "UTF-8";

	private static JAXBContext getJAXBContext() throws JAXBException {
		// TODO brenner: can we make this with reflection
		JAXBContext newInstance = JAXBContext.newInstance(Droplet.class, Valve.class, Flash.class, Camera.class,
				Action.class, DurationAction.class);
		return newInstance;
	}

	public void toXml(Object obj, OutputStream os) {
		try {
			Marshaller marshaller = getJAXBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(obj, os);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public String toXml(Object obj) throws JAXBException {
		Marshaller marshaller = getJAXBContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		String xml = writer.toString();

		return xml;
	}

	public <T> T fromXml(String xml, Class<T> type) {
		return type.cast(fromXml(xml));
	}

	public Object fromXml(String xml) {
		try {
			Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new StringReader(xml));
			return obj;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws JAXBException {
		IDroplet droplet = Droplet.getInstance();

		droplet.setName("My first Configuration");
		droplet.setDescription("This is my first configuration with Droplet");

		for (IValve v : droplet.getValves()) {
			v.setColor(DropletColors.getBackgroundColor(v));
		}
		for (IFlash v : droplet.getFlashes()) {
			v.setColor(DropletColors.getBackgroundColor(v));
		}

		JAXBHelper jaxbHelper = new JAXBHelper();
		String xml = jaxbHelper.toXml(droplet);
		System.out.println(xml);

		Droplet droplet2 = (Droplet) jaxbHelper.fromXml(xml);
		String xml2 = jaxbHelper.toXml(droplet2);
		System.out.println(xml2);

	}

}
