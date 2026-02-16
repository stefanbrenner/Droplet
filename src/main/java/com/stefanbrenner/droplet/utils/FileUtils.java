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

import java.io.File;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * File manipulation utils.
 * <p>
 * This class provides static utility methods for file manipulations.
 * 
 * @author Stefan Brenner
 */
public final class FileUtils {
	
	/** File filter that filters raw image files case insensitively. */
	public static final IOFileFilter RAW_FORMAT_FILTER = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
			FileFilterUtils.or(
					// Canon
					FileFilterUtils.suffixFileFilter("CRW", IOCase.INSENSITIVE),
					FileFilterUtils.suffixFileFilter("CR2", IOCase.INSENSITIVE),
					// Nikon
					FileFilterUtils.suffixFileFilter("NEF", IOCase.INSENSITIVE),
					// Minolta
					FileFilterUtils.suffixFileFilter("MRW", IOCase.INSENSITIVE),
					// Olympus
					FileFilterUtils.suffixFileFilter("ORF", IOCase.INSENSITIVE),
					// Fujifilm
					FileFilterUtils.suffixFileFilter("RAF", IOCase.INSENSITIVE),
					// Sony
					FileFilterUtils.suffixFileFilter("ARW", IOCase.INSENSITIVE),
					FileFilterUtils.suffixFileFilter("SRF", IOCase.INSENSITIVE),
					// Pentax
					FileFilterUtils.suffixFileFilter("PEF", IOCase.INSENSITIVE),
					// General
					FileFilterUtils.suffixFileFilter("RAW", IOCase.INSENSITIVE),
					FileFilterUtils.suffixFileFilter("DNG", IOCase.INSENSITIVE)));
	
	/**
	 * Empty default constructor.
	 */
	private FileUtils() {
		
	}
	
	/**
	 * Returns the name of the file without any file extensions.
	 * 
	 * @param file
	 *            of which the filename without extensions should be returned
	 * @param absolute
	 *            whether the absolute filepath or just the filename is used
	 * @return the name of the file without any extensions
	 */
	public static String getFilename(final File file, final boolean absolute) {
		String fileNameWithOutExtension = absolute ? file.getAbsolutePath() : file.getName();
		int index = fileNameWithOutExtension.indexOf('.');
		if (index != -1) {
			fileNameWithOutExtension = fileNameWithOutExtension.substring(0, index);
		}
		return fileNameWithOutExtension;
	}
	
	/**
	 * Returns the name of the file without any file extensions.
	 * 
	 * @param file
	 *            file
	 * @return the name of the file without any extensions
	 * @see #getFilename(File, boolean)
	 */
	public static String getFilename(final File file) {
		return FileUtils.getFilename(file, false);
	}
	
	/**
	 * Returns a new file with the same name as a given file but with a new
	 * extension.
	 * 
	 * @param file
	 *            for which a new file with a new file extension should be
	 *            created
	 * @param extension
	 *            file extension of the new file
	 * @return a new file with the same name as a given file but with a new
	 *         extension
	 */
	public static File newFileBasedOn(final File file, final String extension) {
		return new File(FileUtils.getFilename(file, true) + '.' + extension);
	}
	
}
