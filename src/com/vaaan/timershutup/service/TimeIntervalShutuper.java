package com.vaaan.timershutup.service;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

import com.vaaan.timershutup.ShutUpService;
import com.vaaan.timershutup.entity.MuteConfigItem;

public class TimeIntervalShutuper implements Runnable {

	private MuteConfigItem mci;
	private Calendar standarCalendar;
	private ShutUpService service;

	public TimeIntervalShutuper(MuteConfigItem mci, ShutUpService service) {
		this.mci = mci;
		this.service=service;
		standarCalendar = Calendar.getInstance();
		standarCalendar.setTime(mci.getFireTime());
	}

	@Override
	public void run() {
		if (mci == null)
			return;
		while (true) {
			try {
				Thread.sleep(getSleepTime());
		    	AudioManager mAudioManager = (AudioManager) service.getSystemService(Context.AUDIO_SERVICE);
				mAudioManager.setStreamMute(AudioManager.STREAM_RING, mci.isMute());
		    	if(mci.isMute())
		    		service.handler.post(new Runnable() {
						@Override
						public void run() {
				    		Toast.makeText(service.getApplicationContext(), "�Զ�����", Toast.LENGTH_SHORT).show();
						}
					});
		    	else
		    		service.handler.post(new Runnable() {
						@Override
						public void run() {
				    		Toast.makeText(service.getApplicationContext(), "�Զ�����", Toast.LENGTH_SHORT).show();
						}
					});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private long getSleepTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, standarCalendar.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, standarCalendar.get(Calendar.MINUTE));
		long time = c.getTime().getTime() - new Date().getTime();
		if (time > 0)
			return time;
		c.add(Calendar.DAY_OF_MONTH, 1);
		time = c.getTime().getTime() - new Date().getTime();
		return time;
	}

}
