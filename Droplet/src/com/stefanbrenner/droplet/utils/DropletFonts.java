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
package com.stefanbrenner.droplet.utils;

import java.awt.Font;

/**
 * @author Stefan Brenner
 */
public class DropletFonts {

	private static final String FONT_FACE = Font.SANS_SERIF;

	private static final int SIZE_STANDARD = 13;

	private static final int SIZE_SMALL = 12;

	private static final int SIZE_MINI = 10;

	public static final Font FONT_STANDARD = new Font(FONT_FACE, Font.PLAIN, SIZE_STANDARD);

	public static final Font FONT_STANDARD_SMALL = new Font(FONT_FACE, Font.PLAIN, SIZE_SMALL);

	public static final Font FONT_STANDARD_MINI = new Font(FONT_FACE, Font.PLAIN, SIZE_MINI);

	public static final Font FONT_LOGGING_SMALL = new Font(Font.MONOSPACED, Font.PLAIN, SIZE_SMALL);

}
