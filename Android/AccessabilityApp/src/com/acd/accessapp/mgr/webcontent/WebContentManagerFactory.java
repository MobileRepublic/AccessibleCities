/*
 * Copyleft 2012 - Alessandro Staniscia
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package com.acd.accessapp.mgr.webcontent;

import com.acd.accessapp.MixContext;

/**
 * Factory Of  WebContentManager
 */
public class WebContentManagerFactory {
	/**
	 * Hide implementation Of WebContentManager
	 * @param mixContext
	 * @return WebContentManager
	 */
	public static WebContentManager makeWebContentManager(MixContext mixContext){
		return new WebPageMgrImpl(mixContext);
	}

}
