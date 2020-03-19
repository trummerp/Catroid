/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2020 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.content.bricks;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedBrick extends BrickBaseType {

	private static final long serialVersionUID = 1L;
	private List<Brick> brickList = new ArrayList<>();
	private List<Object> paramList = new ArrayList<>();

	public UserDefinedBrick() {
	}

	@Override
	public int getViewResource() {
		throw new java.lang.UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
		for (Brick brick : brickList) {
			brick.addActionToSequence(sprite, sequence);
		}
	}

	//TODO: add further functions for deleting bricks etc.
	public void addBrick(Brick brick){
		brickList.add((brick));
	}

	//TODO: add functions to handle parameter list
}
