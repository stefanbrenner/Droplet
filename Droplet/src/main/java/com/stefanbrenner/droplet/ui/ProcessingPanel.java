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
package com.stefanbrenner.droplet.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.XMPSchemaRegistry;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.options.SerializeOptions;
import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.utils.DropletFonts;
import com.stefanbrenner.droplet.utils.UiUtils;

/**
 * Panel for setting metadata information for the pictures created with droplet.
 * 
 * @author Stefan Brenner
 */
// TODO brenner: bind to some model object
public class ProcessingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JCheckBox cbEnable;

	private final JFileChooser fileChooser;

	private final IDropletContext dropletContext;

	/**
	 * Create the panel.
	 */
	public ProcessingPanel(final IDropletContext dropletContext) {

		this.dropletContext = dropletContext;

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(Messages.getString("ProcessingPanel.title"))); //$NON-NLS-1$
		setMinimumSize(new Dimension(400, 200));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;

		cbEnable = new JCheckBox(Messages.getString("ProcessingPanel.writeMetadata")); //$NON-NLS-1$
		cbEnable.setSelected(false);
		cbEnable.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent event) {
				updateUi();
			}
		});
		UiUtils.editGridBagConstraints(gbc, 0, 0, 0, 0, GridBagConstraints.WEST);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(cbEnable, gbc);

		// reset gridwidth
		gbc.gridwidth = 1;

		// watch folder label
		UiUtils.editGridBagConstraints(gbc, 0, 1, 0, 0, GridBagConstraints.WEST);
		add(new JLabel(Messages.getString("ProcessingPanel.watchFolder")), gbc); //$NON-NLS-1$

		{
			JPanel watchFolderPanel = new JPanel();
			watchFolderPanel.setLayout(new BorderLayout());
			UiUtils.editGridBagConstraints(gbc, 1, 1, 1, 0);
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			add(watchFolderPanel, gbc);

			// reset gridwidth
			gbc.gridwidth = 1;

			// watch folder textbox
			JTextField txtWatchFolder = new JTextField();
			txtWatchFolder.setEditable(false);
			watchFolderPanel.add(txtWatchFolder, BorderLayout.CENTER);

			// file chooser for watch folder
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			// watch folder button
			JButton btnWatchFolder = new JButton("..."); //$NON-NLS-1$
			btnWatchFolder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent evt) {
					int returnVal = fileChooser.showOpenDialog(ProcessingPanel.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File directory = fileChooser.getSelectedFile();
						watchFolder(directory);
					}
				}
			});
			watchFolderPanel.add(btnWatchFolder, BorderLayout.EAST);
		}

		// comments label
		UiUtils.editGridBagConstraints(gbc, 0, 2, 0, 1, GridBagConstraints.NORTHEAST);
		add(new JLabel(Messages.getString("ProcessingPanel.comments")), gbc); //$NON-NLS-1$

		// comments textarea
		JTextArea txtComments = new JTextArea(4, 40);
		txtComments.setFont(DropletFonts.FONT_STANDARD_SMALL);
		txtComments.setLineWrap(true);
		UiUtils.disableTab(txtComments);
		JScrollPane commentsScrollPane = new JScrollPane(txtComments);
		UiUtils.editGridBagConstraints(gbc, 1, 2, 1, 1);
		commentsScrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		add(commentsScrollPane, gbc);

		// tag label
		UiUtils.editGridBagConstraints(gbc, 2, 2, 0, 1, GridBagConstraints.NORTHEAST);
		add(new JLabel(Messages.getString("ProcessingPanel.tags")), gbc); //$NON-NLS-1$

		// tag textarea
		JTextArea txtTag = new JTextArea(4, 30);
		txtComments.setFont(DropletFonts.FONT_STANDARD_SMALL);
		txtTag.setLineWrap(true);
		UiUtils.disableTab(txtTag);
		JScrollPane tagScrollPane = new JScrollPane(txtTag);
		UiUtils.editGridBagConstraints(gbc, 3, 2, 1, 1, GridBagConstraints.EAST);
		tagScrollPane.getVerticalScrollBar().putClientProperty("JComponent.sizeVariant", "mini"); //$NON-NLS-1$ //$NON-NLS-2$
		add(tagScrollPane, gbc);

		updateUi();
	}

	private void updateUi() {
		UiUtils.setEnabledRecursive(ProcessingPanel.this, cbEnable.isSelected(), cbEnable);
	}

	// TODO brenner: refactor to services and methods and move to model
	// TODO brenner: start/stop monitor
	private void watchFolder(final File directory) {

		final XMPSchemaRegistry registry = XMPMetaFactory.getSchemaRegistry();
		final String schemaDc = registry.getNamespaceURI("dc");
		final String schemaExif = registry.getNamespaceURI("exif");

		final long interval = 500;

		try {
			/* File filter for RAW files */
			IOFileFilter rawFiles = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
					FileFilterUtils.suffixFileFilter(".cr2"));

			FileAlterationObserver observer = new FileAlterationObserver(directory, rawFiles, IOCase.INSENSITIVE);
			observer.addListener(new FileAlterationListenerAdaptor() {

				@Override
				public void onFileCreate(final File file) {

					dropletContext.addLoggingMessage(Messages.getString("ProcessingPanel.newRAWFileFound",
							file.getName()));

					try {
						// create xmp file
						File xmpFile = com.stefanbrenner.droplet.utils.FileUtils.newFileBasedOn(file, "xmp");

						// fill with metadata
						XMPMeta xmpMeta = XMPMetaFactory.create();

						/* Keywords */
						// TODO brenner: parse tags and add
						xmpMeta.appendArrayItem(schemaDc, "dc:subject", new PropertyOptions().setArray(true),
								"Droplet Keyword", null);

						/* Title and Description */
						// TODO brenner: create readable description from
						// configuration
						// TODO brenner: really set title?
						xmpMeta.setLocalizedText(schemaDc, "dc:title", "x-default", "x-default", "Droplet Title");
						xmpMeta.setLocalizedText(schemaDc, "dc:description", "x-default", "x-default",
								"Droplet Description");

						/* UserComments */
						// TODO brenner: what is this for?
						xmpMeta.setProperty(schemaExif, "exif:UserComment",
								"This picture was created with the help of Droplet - Toolkit for Liquid Art Photographer");

						// write to file
						FileOutputStream fos = FileUtils.openOutputStream(xmpFile);
						XMPMetaFactory.serialize(xmpMeta, fos, new SerializeOptions(
								SerializeOptions.OMIT_PACKET_WRAPPER));
						fos.close();

						dropletContext.addLoggingMessage(Messages.getString("ProcessingPanel.newXMPFileCreated",
								xmpFile.getName()));

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (XMPException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

			});

			FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
			monitor.addObserver(observer);
			monitor.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
