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

package org.catrobat.catroid.test.content.bricks;

import com.badlogic.gdx.utils.GdxNativesLoader;

import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.AddItemToUserListBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.UserList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GdxNativesLoader.class)
public class UserDefinedBrickTest {

	@Before
	public void setUp() {
		PowerMockito.mockStatic(GdxNativesLoader.class);
	}

	@Test
	public void testStartScript() {

		Sprite sprite = new SingleSprite("sprite");
		Script script = new StartScript();
		UserList userList = new UserList("userlist");

		AddItemToUserListBrick addBrick_1 = new AddItemToUserListBrick(0.0d);
		addBrick_1.setUserList(userList);

		UserDefinedBrick our_brick = new UserDefinedBrick();
		for(int cnt = 3; cnt > 0; cnt--){
			AddItemToUserListBrick brick1 = new AddItemToUserListBrick(cnt);
			brick1.setUserList(userList);
			our_brick.addBrick(brick1);
		}

		script.addBrick(addBrick_1);
		script.addBrick(our_brick);

		sprite.addScript(script);
		sprite.initializeEventThreads(EventId.START);

		while (!sprite.look.haveAllThreadsFinished()) {
			sprite.look.act(1.0f);
		}

		assertEquals(4, userList.getValue().size());
	}
}
