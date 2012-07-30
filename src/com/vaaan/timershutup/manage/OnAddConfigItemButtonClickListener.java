package com.vaaan.timershutup.manage;

import android.view.View;
import android.view.View.OnClickListener;

import com.vaaan.timershutup.MainActivity;
import com.vaaan.timershutup.entity.MuteConfigItem;

public class OnAddConfigItemButtonClickListener implements OnClickListener {

	private MainActivity mainActivity;
	
	public OnAddConfigItemButtonClickListener(MainActivity mainActivity){
		this.mainActivity=mainActivity;
	}
	
	@Override
	public void onClick(View v) {
		mainActivity.getConfigList().add(new MuteConfigItem());
		mainActivity.RefreshDataList();
	}

}
