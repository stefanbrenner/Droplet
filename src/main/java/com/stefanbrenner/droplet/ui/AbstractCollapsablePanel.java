package com.stefanbrenner.droplet.ui;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import com.stefanbrenner.droplet.utils.DropletColors;
import com.stefanbrenner.droplet.utils.DropletFonts;

public abstract class AbstractCollapsablePanel extends JPanel {
	
	private static final long serialVersionUID = -4119290766151991826L;
	
	private final FontIcon toggleIconClosed = FontIcon.of(FontAwesome.ARROW_RIGHT, 16);
	private final FontIcon toggleIconOpen = FontIcon.of(FontAwesome.ARROW_DOWN, 16);
	
	private JButton btnToggle;
	private JComponent contentContainer;
	
	public AbstractCollapsablePanel(final String title, final boolean open) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(createHeader(title));
		add(createContainer(open));
		add(new JSeparator());
		
		update();
	}
	
	private JComponent createHeader(final String title) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btnToggle = new JButton();
		btnToggle.setBorderPainted(false);
		btnToggle.addActionListener(this::toggle);
		
		panel.add(btnToggle);
		
		JLabel lbTitle = new JLabel(title);
		lbTitle.setFont(DropletFonts.FONT_HEADER_LARGE);
		
		panel.add(lbTitle);
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				toggle(e);
			}
		});
		
		return panel;
	}
	
	private JComponent createContainer(final boolean open) {
		contentContainer = new JPanel();
		contentContainer.setBackground(DropletColors.GRAY);
		contentContainer.setBorder(BorderFactory.createLineBorder(DropletColors.GRAY));
		contentContainer.setVisible(open);
		
		createContent(contentContainer);
		
		return contentContainer;
	}
	
	protected abstract void createContent(final JComponent container);
	
	private void toggle(final AWTEvent e) {
		contentContainer.setVisible(!contentContainer.isVisible());
		update();
	}
	
	private void update() {
		FontIcon toggleIcon = (contentContainer.isVisible()) ? toggleIconOpen : toggleIconClosed;
		toggleIcon.setIconColor(DropletColors.LIGHT_GRAY);
		btnToggle.setIcon(toggleIcon);
	}
	
}
