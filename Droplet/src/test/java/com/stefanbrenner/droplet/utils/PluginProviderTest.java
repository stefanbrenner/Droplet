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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mangosdk.spi.ProviderFor;

/**
 * @author Stefan Brenner
 * 
 */
public class PluginProviderTest {

	/**
	 * Test interface with on simple method
	 */
	public interface ITestService {
		String getServiceName();
	}

	@ProviderFor(ITestService.class)
	public static class TestServiceProvider1 implements ITestService {
		@Override
		public String getServiceName() {
			return "TestServiceProvider1";
		}
	}

	@ProviderFor(ITestService.class)
	public static class TestServiceProvider2 implements ITestService {
		@Override
		public String getServiceName() {
			return "TestServiceProvider2";
		}
	}

	@Test
	public void testPluginLoader() {
		// find both service provider
		assertEquals(2, PluginLoader.getPlugins(ITestService.class).size());

		List<String> results = new ArrayList<String>();
		for (ITestService service : PluginLoader.getPlugins(ITestService.class)) {
			results.add(service.getServiceName());
		}

		assertTrue(results.contains("TestServiceProvider1"));
		assertTrue(results.contains("TestServiceProvider2"));

	}

}
