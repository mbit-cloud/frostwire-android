/*
 * Created on Feb 13, 2009
 * Created by Paul Gardner
 * 
 * Copyright 2009 Vuze, Inc.  All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License only.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */


package com.aelitis.azureus.core.devices;

public interface 
TranscodeTargetListener 
{
	public static final int	CT_PROPERTY		= 1;		// data = String property
	
	public void
	fileAdded(
		TranscodeFile		file );
	
	public void
	fileChanged(
		TranscodeFile		file,
		int					type,
		Object				data );
	
	public void
	fileRemoved(
		TranscodeFile		file );
}
