package com.vaaan.timershutup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.vaaan.timershutup.entity.MuteConfigItem;
import com.vaaan.timershutup.service.TimeIntervalShutuper;

public class ShutUpService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<Thread> timeIntervalThreadList=new ArrayList<Thread>();
 
    @Override
    public void onStart(Intent intent, int startId) {
    	SharedPreferences settings = this.getSharedPreferences("TIMER_SHUTUP", 0);
    	if(settings==null) {
    		stopAllThread();
    		return;
    	}
    	// start all thread
    	for(int i=0;i< settings.getInt("count", 0);i++){
    		MuteConfigItem mci=new MuteConfigItem();
    		try {
				Calendar calendar=Calendar.getInstance();
				String[] values=settings.getString(String.format("time%s", i), "").split(":");
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(values[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(values[1]));
				mci.setFireTime(calendar.getTime());
				mci.setMute(settings.getBoolean(String.format("is%smute", i), false));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			}
    		Thread thread=new Thread(new TimeIntervalShutuper(mci,this));
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
