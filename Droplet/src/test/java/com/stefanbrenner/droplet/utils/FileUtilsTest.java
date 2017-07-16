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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
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
		assertThat(FileUtils.getFilename(testDir.newFile(".txt"))).isEmpty();
		assertThat(FileUtils.getFilename(testDir.newFile("test"))).isEqualTo("test");
		assertThat(FileUtils.getFilename(testDir.newFile("test.txt"))).isEqualTo("test");
		assertThat(FileUtils.getFilename(testDir.newFile("test.txt.txt"))).isEqualTo("test");
	}
	
	@Test
	public void testNewFile() throws IOException {
		
		String testdirPath = testDir.getRoot().getAbsolutePath() + File.separatorChar;
		
		assertThat(FileUtils.newFileBasedOn(testDir.newFile(".abc"), "txt").getAbsolutePath())
				.isEqualTo(testdirPath + ".txt");
		assertThat(FileUtils.newFileBasedOn(testDir.newFile("test"), "txt").getAbsolutePath())
				.isEqualTo(testdirPath + "test.txt");
		assertThat(FileUtils.newFileBasedOn(testDir.newFile("test.abc"), "txt").getAbsolutePath())
				.isEqualTo(testdirPath + "test.txt");
		assertThat(FileUtils.newFileBasedOn(testDir.newFile("test.a.b.c"), "txt").getAbsolutePath())
				.isEqualTo(testdirPath + "test.txt");
	}
	
	@Test
	public void testRawFileFilter() throws IOException {
		// Canon
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.cr2"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.CR2"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.crw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.CRW"))).isTrue();
		// Nikon
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.nef"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.NEF"))).isTrue();
		// Minolta
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.mrw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.MRW"))).isTrue();
		// Olympus
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.orf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.ORF"))).isTrue();
		// Fujifilm
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.raf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.RAF"))).isTrue();
		// Sony
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.arw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.ARW"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.srf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.SRF"))).isTrue();
		// Pentax
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.pef"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.PEF"))).isTrue();
		// General
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.raw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.RAW"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.dng"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test2.DNG"))).isTrue();
		
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.bin"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.exe"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.txt"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(testDir.newFile("test.zip"))).isFalse();
	}
	
}
