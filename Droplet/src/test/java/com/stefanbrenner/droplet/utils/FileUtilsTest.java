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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Stefan Brenner
 * 
 */
public class FileUtilsTest {
	
	@Rule
	public TemporaryFolder testDir = new TemporaryFolder();
	
	@Test
	public void testGetFilename() throws IOException {
		// assertEquals("", FileUtils.getFilename(testDir.newFile("")));
		// assertEquals("", FileUtils.getFilename(testDir.newFile(".")));
		assertEquals("", FileUtils.getFilename(testDir.newFile(".txt")));
		assertEquals("test", FileUtils.getFilename(testDir.newFile("test")));
		assertEquals("test", FileUtils.getFilename(testDir.newFile("test.txt")));
		assertEquals("test", FileUtils.getFilename(testDir.newFile("test.txt.txt")));
	}
	
	@Test
	public void testNewFile() throws IOException {
		
		String testdirPath = testDir.getRoot().getAbsolutePath() + "/";
		
		assertEquals(testdirPath + ".txt", FileUtils.newFileBasedOn(testDir.newFile(".abc"), "txt").getAbsolutePath());
		assertEquals(testdirPath + "test.txt", FileUtils.newFileBasedOn(testDir.newFile("test"), "txt")
				.getAbsolutePath());
		assertEquals(testdirPath + "test.txt", FileUtils.newFileBasedOn(testDir.newFile("test.abc"), "txt")
				.getAbsolutePath());
		assertEquals(testdirPath + "test.txt", FileUtils.newFileBasedOn(testDir.newFile("test.a.b.c"), "txt")
				.getAbsolutePath());
	}
	
	@Test
	public void testRawFileFilter() throws IOException {
		// Canon
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.cr2")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.CR2")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.crw")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.CRW")));
		// Nikon
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.nef")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.NEF")));
		// Minolta
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.mrw")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.MRW")));
		// Olympus
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.orf")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.ORF")));
		// Fujifilm
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.raf")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.RAF")));
		// Sony
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.arw")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.ARW")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.srf")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.SRF")));
		// Pentax
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.pef")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.PEF")));
		// General
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.raw")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.RAW")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.dng")));
		assertTrue(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.DNG")));
		
		assertFalse(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.bin")));
		assertFalse(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.exe")));
		assertFalse(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.txt")));
		assertFalse(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.zip")));
	}
	
}
