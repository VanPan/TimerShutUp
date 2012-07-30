package com.vaaan.timershutup.service;

import java.util.Calendar;
import java.util.Date;

import android.media.AudioManager;

import com.vaaan.timershutup.entity.MuteConfigItem;

public class TimeIntervalShutuper implements Runnable {

	private MuteConfigItem mci;
	private Calendar standarCalendar;
	private AudioManager mAudioManager;

	public TimeIntervalShutuper(MuteConfigItem mci, AudioManager mAudioManager) {
		this.mci = mci;
		this.mAudioManager = mAudioManager;
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
				mAudioManager.setStreamMute(AudioManager.STREAM_RING,
						mci.isMute());
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
