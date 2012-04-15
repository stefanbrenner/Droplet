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
package com.stefanbrenner.droplet.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.XMPSchemaRegistry;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.options.SerializeOptions;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.IMetadata;
import com.stefanbrenner.droplet.service.IMetadataProcessingService;
import com.stefanbrenner.droplet.ui.Messages;

/**
 * 
 * @author Stefan Brenner
 */
public class XMPMetadataService extends FileAlterationListenerAdaptor implements IMetadataProcessingService {
	
	/**
	 * Interval in milliseconds for the directory monitor.
	 */
	private static final int INTERVAL = 500;
	
	private FileAlterationMonitor monitor;
	
	private FileAlterationObserver observer;
	
	private IMetadata metadata;
	private IDropletContext dropletContext;
	
	private URI outputFolderURI;
	
	private static final XMPSchemaRegistry REGISTRY = XMPMetaFactory.getSchemaRegistry();
	
	private static final String SCHEMA_DC = REGISTRY.getNamespaceURI("dc");
	private static final String SCHEMA_EXIF = REGISTRY.getNamespaceURI("exif");
	
	private boolean running = false;
	
	/**
	 * Creates a new xmp metadata service.
	 * 
	 * @param metadata
	 *            to be used
	 * @param dropletContext
	 *            to be used
	 */
	public XMPMetadataService(final IMetadata metadata, final IDropletContext dropletContext) {
		this.metadata = metadata;
		this.dropletContext = dropletContext;
		this.monitor = new FileAlterationMonitor(INTERVAL);
	}
	
	@Override
	public void setWatchFolder(final URI uri) {
		if (observer != null) {
			monitor.removeObserver(observer);
		}
		
		File watchFolder = new File(uri);
		if (watchFolder.exists()) {
			observer = new FileAlterationObserver(watchFolder,
					com.stefanbrenner.droplet.utils.FileUtils.RAW_FORMAT_FILTER);
			try {
				observer.initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
			observer.addListener(this);
		} else {
			observer = null;
		}
		
		if (observer != null) {
			monitor.addObserver(observer);
		}
	}
	
	@Override
	public void setOutputFolder(final URI uri) {
		this.outputFolderURI = uri;
	}
	
	@Override
	public void setMetadata(final IMetadata metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public void start() {
		try {
			if (!running) {
				monitor.start();
				running = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		try {
			if (running) {
				monitor.stop();
				running = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A new raw file was created in the watched folder. A new xmp file with the
	 * same filename as the raw file is created and the metadata gets added to
	 * it. If an output folder is set, both files are finally moved to that
	 * folder.
	 * 
	 * @param rawFile
	 *            the new raw file
	 */
	@Override
	public void onFileCreate(final File rawFile) {
		
		dropletContext.addLoggingMessage(Messages.getString("ProcessingPanel.newRAWFileFound", rawFile.getName()));
		
		try {
			// create xmp file
			File xmpFile = com.stefanbrenner.droplet.utils.FileUtils.newFileBasedOn(rawFile, "xmp");
			
			// fill with metadata
			XMPMeta xmpMeta = XMPMetaFactory.create();
			
			/* Keywords */
			for (String tag : StringUtils.split(metadata.getTags(), ',')) {
				xmpMeta.appendArrayItem(SCHEMA_DC, "dc:subject", new PropertyOptions().setArray(true),
						StringUtils.trim(tag), null);
			}
			
			/* Title and Description */
			// TODO brenner: create readable description from configuration
			// xmpMeta.setLocalizedText(schemaDc, "dc:title",
			// "x-default", "x-default", "Droplet Title");
			xmpMeta.setLocalizedText(SCHEMA_DC, "dc:description", "x-default", "x-default",
					StringUtils.trim(metadata.getDescription()));
			
			/* UserComments */
			// TODO brenner: what is this for?
			xmpMeta.setProperty(SCHEMA_EXIF, "exif:UserComment",
					"This picture was created with the help of Droplet - Toolkit for Liquid Art Photographer");
			
			// write to file
			FileOutputStream fos = FileUtils.openOutputStream(xmpFile);
			XMPMetaFactory.serialize(xmpMeta, fos, new SerializeOptions(SerializeOptions.OMIT_PACKET_WRAPPER));
			fos.close();
			
			dropletContext
					.addLoggingMessage(Messages.getString("ProcessingPanel.newXMPFileCreated", xmpFile.getName()));
			
			if (outputFolderURI != null) {
				File outputFolder = new File(outputFolderURI);
				FileUtils.moveFileToDirectory(xmpFile, outputFolder, false);
				FileUtils.moveFileToDirectory(rawFile, outputFolder, false);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
