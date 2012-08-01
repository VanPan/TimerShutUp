package com.vaaan.timershutup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vaaan.timershutup.adapter.MuteConfigItemListAdapter;
import com.vaaan.timershutup.entity.MuteConfigItem;
import com.vaaan.timershutup.manage.OnAddConfigItemButtonClickListener;
import com.vaaan.timershutup.manage.OnSaveButtonClickListener;

public class MainActivity extends Activity {

	private List<MuteConfigItem> configList = new ArrayList<MuteConfigItem>();

	public List<MuteConfigItem> getConfigList() {
		return configList;
	}

	public void setConfigList(List<MuteConfigItem> configList) {
		this.configList = configList;
	}

	private OnAddConfigItemButtonClickListener onAddConfigItemButtonClickListener;
	private OnSaveButtonClickListener onSaveButtonClickListener;
	private MuteConfigItemListAdapter muteConfigItemListAdapter;
	private MainActivity _self;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		_self = this;
		// add mute button click event
		Button btnMute = (Button) findViewById(R.id.btnMute);
		btnMute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalAudioManager.mute(_self);
				Toast.makeText(_self, "已静音", Toast.LENGTH_SHORT).show();
			}
		});
		// add unmute button click event
		Button btnUnMute = (Button) findViewById(R.id.btnUnMute);
		btnUnMute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalAudioManager.unmute(_self);
				Toast.makeText(_self, "已发声", Toast.LENGTH_SHORT).show();
			}
		});
		// add click event
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		onAddConfigItemButtonClickListener = new OnAddConfigItemButtonClickListener(
				this);
		btnAdd.setOnClickListener(onAddConfigItemButtonClickListener);
		// add click event
		Button btnSave = (Button) findViewById(R.id.btnSave);
		onSaveButtonClickListener = new OnSaveButtonClickListener(this);
		btnSave.setOnClickListener(onSaveButtonClickListener);
		// add list adapter
		ListView listView = (ListView) findViewById(R.id.lvContent);
		muteConfigItemListAdapter = new MuteConfigItemListAdapter(this);
		listView.setAdapter(muteConfigItemListAdapter);
		// load save data
		SharedPreferences settings = this.getSharedPreferences("TIMER_SHUTUP",
				0);
		configList.clear();
		for (int i = 0; i < settings.getInt("count", 0); i++) {
			MuteConfigItem mci = new MuteConfigItem();
			try {
				Calendar calendar = Calendar.getInstance();
				String[] values = settings.getString(
						String.format("time%s", i), "").split(":");
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(values[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(values[1]));
				mci.setFireTime(calendar.getTime());
				mci.setMute(settings.getBoolean(String.format("is%smute", i),
						false));
				configList.add(mci);
			} catch (Exception e) {
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miAdd:
			new OnAddConfigItemButtonClickListener(this).onClick(null);
			break;
		case R.id.miSave:
			new OnSaveButtonClickListener(this).onClick(null);
			break;
		case R.id.miMute:
			GlobalAudioManager.mute(_self);
			Toast.makeText(_self, "已静音", Toast.LENGTH_SHORT).show();
			break;
		case R.id.miUnMute:
			GlobalAudioManager.unmute(_self);
			Toast.makeText(_self, "已发声", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}

	public void RefreshDataList() {
		muteConfigItemListAdapter.notifyDataSetChanged();
	}
}
