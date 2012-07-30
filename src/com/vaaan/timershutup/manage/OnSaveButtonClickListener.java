package com.vaaan.timershutup.manage;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.vaaan.timershutup.MainActivity;
import com.vaaan.timershutup.entity.MuteConfigItem;
import com.vaaan.timershutup.ShutUpService;

public class OnSaveButtonClickListener implements OnClickListener {

	private MainActivity mainActivity;
	
	public OnSaveButtonClickListener(MainActivity mainActivity){
		this.mainActivity=mainActivity;
	}

	private static SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
	
	@Override
	public void onClick(View v) {
		SharedPreferences settings = mainActivity.getSharedPreferences("TIMER_SHUTUP", 0);
		Editor editor=settings.edit();
		editor.clear();
		editor.putInt("count", mainActivity.getConfigList().size());
		int index=0;
		for(MuteConfigItem mci:mainActivity.getConfigList()){
			editor.putString(String.format("time%s", index), sdf.format(mci.getFireTime()));
			editor.putBoolean(String.format("is%smute", index), mci.isMute());
			index++;
		}
		editor.commit();
		mainActivity.startService(new Intent(mainActivity, ShutUpService.class));
		Toast.makeText(mainActivity, "±£´æ³É¹¦", Toast.LENGTH_LONG).show();
	}

}
