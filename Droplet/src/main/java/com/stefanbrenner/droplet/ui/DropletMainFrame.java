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

import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import com.stefanbrenner.droplet.model.IDropletContext;
import com.stefanbrenner.droplet.model.internal.Configuration;
import com.stefanbrenner.droplet.model.internal.Droplet;
import com.stefanbrenner.droplet.model.internal.DropletContext;
import com.stefanbrenner.droplet.ui.actions.ExitAction;
import com.stefanbrenner.droplet.ui.actions.PreferencesAction;
import com.stefanbrenner.droplet.ui.actions.StartAction;
import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.DropletConfig;
import com.stefanbrenner.droplet.utils.DropletFonts;
import com.stefanbrenner.droplet.utils.Messages;
import com.stefanbrenner.droplet.utils.UiUtils;
import com.tngtech.configbuilder.ConfigBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Mainframe of the droplet application.
 *
 * @author Stefan Brenner
 */
@Slf4j
public class DropletMainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	// model objects
	private final IDropletContext dropletContext;
	
	// ui components
	private final DropletMenu dropletMenu;
	private final JPanel contentPane;
	private final CommunicationPanel commPanel;
	private final DeviceSetupPanel configPanel;
	private final ProcessingPanel processingPanel;
	private final LoggingPanel loggingPanel;
	private final DropletToolbar toolbarPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		
		log.info("Droplet - Toolkit for High-Speed-Photography");
		log.info("Version {}", Messages.getString("Version"));
		log.info("Open Source Project created by Stefan Brenner 2012");
		log.info("http://www.droplet.at");
		log.info("==================================================");
		log.info("Starting ...");
		
		// log system properties
		Properties properties = System.getProperties();
		Collections.list(properties.keys()).forEach(prop -> {
			log.debug("{}: {}", prop, properties.get(prop));
		});
		
		if (UiUtils.isMacOS()) {
			// put jmenubar on mac menu bar
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			// use smoother fonts
			System.setProperty("apple.awt.textantialiasing", "true");
			// set the brushed metal look and feel, if desired
			System.setProperty("apple.awt.brushMetalLook", "true");
			// use the system look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			customizeOsxTheme();
		} else {
			try {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						// TODO: customize Nimbus Theme for darkness
						break;
					}
				}
			} catch (Exception e) {
				// use the system look and feel
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		}
		
		// initialize DropletConfig
		log.info("initialize config");
		new ConfigBuilder<DropletConfig>(DropletConfig.class).build();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DropletMainFrame frame = new DropletMainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					log.error("A fatal error occured: ", e);
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public DropletMainFrame() {
		
		log.info("start UI");
		
		// create new context
		dropletContext = new DropletContext();
		
		// create new droplet model and add to context
		Droplet droplet = new Droplet();
		droplet.initializeWithDefaults();
		dropletContext.setDroplet(droplet);
		
		// basic frame setup
		updateTitle();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(new ImageIcon("icons/droplet_icon_48x48.png").getImage());
		setBackground(java.awt.Color.BLACK);
		
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		// create menu
		dropletMenu = new DropletMenu(this, dropletContext);
		setJMenuBar(dropletMenu);
		
		commPanel = new CommunicationPanel(dropletContext);
		contentPane.add(commPanel, BorderLayout.NORTH);
		
		{
			configPanel = new DeviceSetupPanel(this, dropletContext);
			
			processingPanel = new ProcessingPanel(dropletContext);
			loggingPanel = new LoggingPanel(dropletContext);
			
			JTabbedPane tabbedPane = new JTabbedPane();
			
			FontIcon processingIcon = FontIcon.of(FontAwesome.COGS, 16);
			processingIcon.setIconSize(14);
			processingIcon.setIconColor(java.awt.Color.GRAY);
			tabbedPane.addTab(Messages.getString("ProcessingPanel.title"), processingIcon, processingPanel);
			
			FontIcon loggingIcon = FontIcon.of(FontAwesome.DESKTOP, 16);
			loggingIcon.setIconSize(14);
			loggingIcon.setIconColor(java.awt.Color.GRAY);
			tabbedPane.addTab(Messages.getString("LoggingPanel.title"), loggingIcon, loggingPanel);
			
			FontIcon cleaningIcon = FontIcon.of(FontAwesome.PAINT_BRUSH, 16);
			cleaningIcon.setIconSize(14);
			cleaningIcon.setIconColor(java.awt.Color.GRAY);

			JSplitPane splitPaneMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT, configPanel, tabbedPane);
			splitPaneMain.setBorder(BorderFactory.createCompoundBorder());
			splitPaneMain.setOneTouchExpandable(false);
			splitPaneMain.setDividerLocation(0.5d);
			splitPaneMain.setResizeWeight(0.5d);
			splitPaneMain.setContinuousLayout(true);
			contentPane.add(splitPaneMain, BorderLayout.CENTER);
		}
		
		toolbarPanel = new DropletToolbar(this, dropletContext);
		contentPane.add(toolbarPanel, BorderLayout.SOUTH);
		
		// remove focus from text components if user clicks outside
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				requestFocusInWindow();
				super.mouseClicked(e);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent event) {
				handleQuit();
			}
		});
		
		// register action shortcuts
		log.debug("Register action shortcuts");
		contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), "start"); //$NON-NLS-1$ //$NON-NLS-2$
		contentPane.getActionMap().put("start", new StartAction(this, dropletContext)); //$NON-NLS-1$
		
		// set default fonts
		log.debug("set default fonts");
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, DropletFonts.FONT_STANDARD_SMALL);
			}
		}
		
		// register handlers for mac events
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.APP_ABOUT)) {
			desktop.setAboutHandler(e -> SwingUtilities.invokeLater(this::handleAbout));
		}
		if (desktop.isSupported(Desktop.Action.APP_PREFERENCES)) {
			desktop.setPreferencesHandler(e -> SwingUtilities.invokeLater(this::handlePrefs));
		}
		if (desktop.isSupported(Desktop.Action.APP_QUIT_HANDLER)) {
			desktop.setQuitHandler((e, response) -> SwingUtilities.invokeLater(this::handleQuit));
		}

		// finishing frame settings
		// make frame as small as possible
		pack();
		// set minimum size to packed size
		setMinimumSize(getSize());
		// set to full screen mode
		setExtendedState(Frame.MAXIMIZED_BOTH);
		// update ui for nimbus component resizing
		SwingUtilities.updateComponentTreeUI(this);
		
		dropletContext.addPropertyChangeListener(IDropletContext.PROPERTY_FILE, new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent event) {
				updateTitle();
			}
		});
		
		// set to fullscreen if configured
		dispose();
		setUndecorated(Configuration.isFullscreenEnabled());
		setVisible(true);
		
		// add welcome message to logging panel
		dropletContext.addLoggingMessage("Welcome to Droplet v" + Messages.getString("Version") + "!");
		
		log.info("Droplet started successfully!");
		
	}
	
	/**
	 * Updates the frame title.
	 * <p>
	 * Additionally displays the absolute file name of an opened or saved file.
	 */
	private void updateTitle() {
		File file = dropletContext.getFile();
		String title = Messages.getString("DropletMainFrame.title"); //$NON-NLS-1$
		if (file != null) {
			title += " - " + file.getAbsolutePath();
		}
		setTitle(title);
	}

	public void handlePrefs() throws IllegalStateException {
		new PreferencesAction(this, dropletContext).actionPerformed(null);
	}

	public void handleQuit() {
		new ExitAction(this, dropletContext).actionPerformed(null);
	}

	public void handleAbout() {
		new AboutDialog(this).setVisible(true);
	}
	
	private static void customizeOsxTheme() {
		
		// print UI defaults
		UIManager.getLookAndFeelDefaults().entrySet().forEach(System.out::println);
		
		UIManager.put("Panel.background", DropletColors.BLACK);
		UIManager.put("Panel.foreground", DropletColors.WHITE);
		
		UIManager.put("ToolBar.background", DropletColors.DARK_GRAY);
		UIManager.put("ToolBar.foreground", DropletColors.WHITE);
		
		UIManager.put("SplitPane.dividerSize", 3);
		UIManager.put("SplitPane.background", DropletColors.BLACK);
		
		UIManager.put("TabbedPane.background", DropletColors.GRAY);
		UIManager.put("TabbedPane.foreground", DropletColors.BLACK);
		
		UIManager.put("Label.foreground", DropletColors.WHITE);
		UIManager.put("CheckBox.foreground", DropletColors.WHITE);
		UIManager.put("CheckBox.select", DropletColors.WHITE);
		
		UIManager.put("TextArea.background", DropletColors.WHITE);
		UIManager.put("TextArea.border", new EmptyBorder(5, 5, 5, 5));
		
		UIManager.put("TextField.background", DropletColors.WHITE);
		UIManager.put("TextField.border", new EmptyBorder(5, 5, 5, 5));
		
		UIManager.put("TitledBorder.titleColor", DropletColors.LIGHT_GRAY);
		UIManager.put("TitledBorder.font", DropletFonts.FONT_HEADER_LARGE);
		UIManager.put("TitledBorder.border", new LineBorder(DropletColors.GRAY, 1, true));
		
		UIManager.put("RadioButton.foreground", DropletColors.WHITE);
		
		UIManager.put("ColorChooser.background", DropletColors.BLACK);
		UIManager.put("ColorChooser.foreground", DropletColors.WHITE);
		
		UIManager.put("OptionPane.background", DropletColors.BLACK);
		UIManager.put("OptionPane.messageForeground", DropletColors.WHITE);
		
		// set font
		Font defaultFont = new Font("Calibri", Font.PLAIN, 12);
		
		UIManager.put("Button.font", defaultFont);
		UIManager.put("ToggleButton.font", defaultFont);
		UIManager.put("RadioButton.font", defaultFont);
		UIManager.put("CheckBox.font", defaultFont);
		UIManager.put("ColorChooser.font", defaultFont);
		UIManager.put("ComboBox.font", defaultFont);
		UIManager.put("Label.font", defaultFont);
		UIManager.put("List.font", defaultFont);
		UIManager.put("MenuBar.font", defaultFont);
		UIManager.put("MenuItem.font", defaultFont);
		UIManager.put("RadioButtonMenuItem.font", defaultFont);
		UIManager.put("CheckBoxMenuItem.font", defaultFont);
		UIManager.put("Menu.font", defaultFont);
		UIManager.put("PopupMenu.font", defaultFont);
		UIManager.put("OptionPane.font", defaultFont);
		UIManager.put("Panel.font", defaultFont);
		UIManager.put("ProgressBar.font", defaultFont);
		UIManager.put("ScrollPane.font", defaultFont);
		UIManager.put("Viewport.font", defaultFont);
		UIManager.put("TabbedPane.font", defaultFont);
		UIManager.put("Table.font", defaultFont);
		UIManager.put("TableHeader.font", defaultFont);
		UIManager.put("TextField.font", defaultFont);
		UIManager.put("PasswordField.font", defaultFont);
		UIManager.put("TextArea.font", defaultFont);
		UIManager.put("TextPane.font", defaultFont);
		UIManager.put("EditorPane.font", defaultFont);
		UIManager.put("TitledBorder.font", defaultFont);
		UIManager.put("ToolBar.font", defaultFont);
		UIManager.put("ToolTip.font", defaultFont);
		UIManager.put("Tree.font", defaultFont);
		
	}
	
}
