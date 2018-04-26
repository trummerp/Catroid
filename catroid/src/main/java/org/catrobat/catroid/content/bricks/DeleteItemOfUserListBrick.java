/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
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

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.adapter.DataAdapter;
import org.catrobat.catroid.ui.adapter.UserListAdapterWrapper;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

import java.util.List;

public class DeleteItemOfUserListBrick extends UserListBrick {

	private static final long serialVersionUID = 1L;

	public DeleteItemOfUserListBrick() {
		addAllowedBrickField(BrickField.LIST_DELETE_ITEM);
	}

	public DeleteItemOfUserListBrick(Formula userListFormula, UserList userList) {
		initializeBrickFields(userListFormula);
		this.userList = userList;
	}

	public DeleteItemOfUserListBrick(Integer value) {
		initializeBrickFields(new Formula(value));
	}

	private void initializeBrickFields(Formula listAddItemFormula) {
		addAllowedBrickField(BrickField.LIST_DELETE_ITEM);
		setFormulaWithBrickField(BrickField.LIST_DELETE_ITEM, listAddItemFormula);
	}

	@Override
	public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createDeleteItemOfUserListAction(sprite,
				getFormulaWithBrickField(BrickField.LIST_DELETE_ITEM), userList));
		return null;
	}

	@Override
	public View getView(final Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}

		view = View.inflate(context, R.layout.brick_delete_item_of_userlist, null);
		view = BrickViewProvider.setAlphaOnView(view, alphaValue);
		setCheckboxView(R.id.brick_delete_item_of_userlist_checkbox);
		TextView textField = (TextView) view.findViewById(R.id.brick_delete_item_of_userlist_edit_text);
		getFormulaWithBrickField(BrickField.LIST_DELETE_ITEM).setTextFieldId(R.id.brick_delete_item_of_userlist_edit_text);
		getFormulaWithBrickField(BrickField.LIST_DELETE_ITEM).refreshTextField(view);

		textField.setOnClickListener(this);

		Spinner userListSpinner = (Spinner) view.findViewById(R.id.delete_item_of_userlist_spinner);
		DataAdapter dataAdapter = ProjectManager.getInstance().getCurrentScene().getDataContainer()
				.createDataAdapter(context, ProjectManager.getInstance().getCurrentSprite());
		UserListAdapterWrapper userListAdapterWrapper = new UserListAdapterWrapper(context, dataAdapter);
		userListAdapterWrapper.setItemLayout(android.R.layout.simple_spinner_item, android.R.id.text1);

		userListSpinner.setAdapter(userListAdapterWrapper);

		setSpinnerSelection(userListSpinner, null);

		userListSpinner.setOnTouchListener(createSpinnerOnTouchListener());
		userListSpinner.setOnItemSelectedListener(createListSpinnerItemSelectedListener());
		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		View prototypeView = View.inflate(context, R.layout.brick_delete_item_of_userlist, null);
		Spinner userListSpinner = (Spinner) prototypeView.findViewById(R.id.delete_item_of_userlist_spinner);

		DataAdapter dataAdapter = ProjectManager.getInstance().getCurrentScene().getDataContainer()
				.createDataAdapter(context, ProjectManager.getInstance().getCurrentSprite());

		UserListAdapterWrapper userListAdapterWrapper = new UserListAdapterWrapper(context, dataAdapter);

		userListAdapterWrapper.setItemLayout(android.R.layout.simple_spinner_item, android.R.id.text1);
		userListSpinner.setAdapter(userListAdapterWrapper);
		setSpinnerSelection(userListSpinner, null);

		TextView textAddItemToList = (TextView) prototypeView
				.findViewById(R.id.brick_delete_item_of_userlist_edit_text);
		textAddItemToList.setText(formatNumberForPrototypeView(BrickValues.DELETE_ITEM_OF_USERLIST));

		return prototypeView;
	}

	@Override
	public void onNewList(UserList userList) {
		Spinner spinner = view.findViewById(R.id.delete_item_of_userlist_spinner);
		setSpinnerSelection(spinner, userList);
	}

	@Override
	public Brick clone() {
		DeleteItemOfUserListBrick clonedBrick = new DeleteItemOfUserListBrick(getFormulaWithBrickField(BrickField.LIST_DELETE_ITEM).clone(), userList);
		clonedBrick.setBackPackedData(new BackPackedListData(backPackedData));
		return clonedBrick;
	}

	@Override
	public void showFormulaEditorToEditFormula(View view) {
		FormulaEditorFragment.showFragment(view, this, BrickField.LIST_DELETE_ITEM);
	}

	@Override
	public void updateReferenceAfterMerge(Scene into, Scene from) {
		super.updateUserListReference(into, from);
	}
}
