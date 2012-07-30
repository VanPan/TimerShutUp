package com.vaaan.timershutup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.IBinder;

import com.vaaan.timershutup.entity.MuteConfigItem;
import com.vaaan.timershutup.service.TimeIntervalShutuper;

public class ShutUpService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private static SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
	private static List<Thread> timeIntervalThreadList=new ArrayList<Thread>();
 
    @Override
    public void onStart(Intent intent, int startId) {
    	SharedPreferences settings = this.getSharedPreferences("TIMER_SHUTUP", 0);
    	if(settings==null) {
    		stopAllThread();
    		return;
    	}
    	AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    	// start all thread
    	for(int i=0;i< settings.getInt("count", 0);i++){
    		MuteConfigItem mci=new MuteConfigItem();
    		try {
				mci.setFireTime(sdf.parse(settings.getString(String.format("time%s", i), "")));
				mci.setMute(settings.getBoolean(String.format("is%smute", i), false));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				continue;
			}
    		Thread thread=new Thread(new TimeIntervalShutuper(mci,mAudioManager));
    		timeIntervalThreadList.add(thread);
    		thread.start();
    	}
    }
    
    private void stopAllThread(){
    	for(Thread thread:timeIntervalThreadList){
    		thread.interrupt();
    	}
    	timeIntervalThreadList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAllThread();
    }

}
