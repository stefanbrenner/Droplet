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
package com.stefanbrenner.droplet.utils;

import java.awt.Font;

/**
 * Droplet font utilities.
 * <p>
 * This class provides static utility methods for the font in the ui.
 * 
 * @author Stefan Brenner
 */
public final class DropletFonts {
	
	/** The default font face. */
	private static final String FONT_FACE = Font.SANS_SERIF;
	
	/** A x-large font size. */
	private static final int SIZE_XLARGE = 15;
	/** A large font size. */
	private static final int SIZE_LARGE = 14;
	/** The standard font size. */
	private static final int SIZE_STANDARD = 13;
	/** A small font size. */
	private static final int SIZE_SMALL = 12;
	/** A very small font size. */
	private static final int SIZE_MINI = 10;
	
	/** The standard font. */
	public static final Font FONT_STANDARD = new Font(DropletFonts.FONT_FACE, Font.PLAIN, DropletFonts.SIZE_STANDARD);
	/** The small standard font. */
	public static final Font FONT_STANDARD_SMALL = new Font(DropletFonts.FONT_FACE, Font.PLAIN, DropletFonts.SIZE_SMALL);
	/** The very small standard font. */
	public static final Font FONT_STANDARD_MINI = new Font(DropletFonts.FONT_FACE, Font.PLAIN, DropletFonts.SIZE_MINI);
	/** The small logging font. */
	public static final Font FONT_LOGGING_SMALL = new Font(Font.MONOSPACED, Font.PLAIN, DropletFonts.SIZE_SMALL);
	/** The large header font. */
	public static final Font FONT_HEADER_LARGE = new Font(DropletFonts.FONT_FACE, Font.BOLD, DropletFonts.SIZE_LARGE);
	
	/**
	 * Empty default constructor.
	 */
	private DropletFonts() {
		
	}
}
