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
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * @author Stefan Brenner
 *
 */
class FileUtilsTest {
	
	@Test
	void testGetFilename(@TempDir final Path testDir) throws IOException {
		// assertEquals("", FileUtils.getFilename(testDir.newFile("")));
		// assertEquals("", FileUtils.getFilename(testDir.newFile(".")));
		assertThat(FileUtils.getFilename(testDir.resolve(".txt").toFile())).isEmpty();
		assertThat(FileUtils.getFilename(testDir.resolve("test").toFile())).isEqualTo("test");
		assertThat(FileUtils.getFilename(testDir.resolve("test.txt").toFile())).isEqualTo("test");
		assertThat(FileUtils.getFilename(testDir.resolve("test.txt.txt").toFile())).isEqualTo("test");
	}
	
	@Test
	void testNewFile(@TempDir final Path testDir) throws IOException {
		
		String testdirPath = testDir.toString() + File.separatorChar;
		
		assertThat(
				FileUtils.newFileBasedOn(Files.createFile(testDir.resolve(".abc")).toFile(), "txt").getAbsolutePath())
						.isEqualTo(testdirPath + ".txt");
		assertThat(
				FileUtils.newFileBasedOn(Files.createFile(testDir.resolve("test")).toFile(), "txt").getAbsolutePath())
						.isEqualTo(testdirPath + "test.txt");
		assertThat(FileUtils.newFileBasedOn(Files.createFile(testDir.resolve("test.abc")).toFile(), "txt")
				.getAbsolutePath()).isEqualTo(testdirPath + "test.txt");
		assertThat(FileUtils.newFileBasedOn(Files.createFile(testDir.resolve("test.a.b.c")).toFile(), "txt")
				.getAbsolutePath()).isEqualTo(testdirPath + "test.txt");
	}
	
	@Test
	void testRawFileFilter(@TempDir final Path testDir) throws IOException, NoSuchAlgorithmException {
		// Canon
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "cr2"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "CR2"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "crw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "CRW"))).isTrue();
		// Nikon
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "nef"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "NEF"))).isTrue();
		// Minolta
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "mrw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "MRW"))).isTrue();
		// Olympus
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "orf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "ORF"))).isTrue();
		// Fujifilm
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "raf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "RAF"))).isTrue();
		// Sony
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "arw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "ARW"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "srf"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "SRF"))).isTrue();
		// Pentax
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "pef"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "PEF"))).isTrue();
		// General
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "raw"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "RAW"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "dng"))).isTrue();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "DNG"))).isTrue();
		
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "bin"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "exe"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "txt"))).isFalse();
		assertThat(FileUtils.RAW_FORMAT_FILTER.accept(createTestFile(testDir, "zip"))).isFalse();
	}
	
	private File createTestFile(final Path path, final String suffix) throws IOException, NoSuchAlgorithmException {
		var random = SecureRandom.getInstanceStrong().nextLong();
		return Files.createFile(path.resolve("test" + random + "." + suffix)).toFile();
	}
	
}
