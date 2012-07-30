package com.vaaan.timershutup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.vaaan.timershutup.adapter.MuteConfigItemListAdapter;
import com.vaaan.timershutup.entity.MuteConfigItem;
import com.vaaan.timershutup.manage.OnAddConfigItemButtonClickListener;
import com.vaaan.timershutup.manage.OnSaveButtonClickListener;

public class MainActivity extends Activity {
	
	private List<MuteConfigItem> configList=new ArrayList<MuteConfigItem>();
	

    public List<MuteConfigItem> getConfigList() {
		return configList;
	}

	public void setConfigList(List<MuteConfigItem> configList) {
		this.configList = configList;
	}
	
	private OnAddConfigItemButtonClickListener onAddConfigItemButtonClickListener;
	private OnSaveButtonClickListener onSaveButtonClickListener;
	private MuteConfigItemListAdapter muteConfigItemListAdapter;
	private static SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

	@Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        // add mute button click event
        Button btnMute = (Button)findViewById(R.id.btnMute);
        btnMute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				mAudioManager.setStreamMute(AudioManager.STREAM_RING,true);
			}
		});
        // add unmute button click event
        Button btnUnMute = (Button)findViewById(R.id.btnUnMute);
        btnUnMute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		    	AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				mAudioManager.setStreamMute(AudioManager.STREAM_RING,false);
			}
		});
        // add click event
        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        onAddConfigItemButtonClickListener=new OnAddConfigItemButtonClickListener(this);
        btnAdd.setOnClickListener(onAddConfigItemButtonClickListener);
        // add click event
        Button btnSave = (Button)findViewById(R.id.btnSave);
        onSaveButtonClickListener=new OnSaveButtonClickListener(this);
        btnSave.setOnClickListener(onSaveButtonClickListener);
        // add list adapter
        ListView listView = (ListView)findViewById(R.id.lvContent);
        muteConfigItemListAdapter=new MuteConfigItemListAdapter(this);
        listView.setAdapter(muteConfigItemListAdapter);
        // load save data
        SharedPreferences settings = this.getSharedPreferences("TIMER_SHUTUP", 0);
        configList.clear();
    	for(int i=0;i< settings.getInt("count", 0);i++){
    		MuteConfigItem mci=new MuteConfigItem();
    		try {
				mci.setFireTime(sdf.parse(settings.getString(String.format("time%s", i), "")));
				mci.setMute(settings.getBoolean(String.format("is%smute", i), false));
				configList.add(mci);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				continue;
			}
    	}
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	public void RefreshDataList(){
		muteConfigItemListAdapter.notifyDataSetChanged();
	}
}
