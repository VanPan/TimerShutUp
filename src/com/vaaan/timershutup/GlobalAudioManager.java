package com.vaaan.timershutup;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioManager;

public class GlobalAudioManager {
	private static AudioManager am;
	private static int oldVolume;
	
	private static synchronized void initAudioManager(ContextWrapper cw){
		if(am!=null) return;
		am=(AudioManager)cw.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public static void mute(ContextWrapper cw){
		initAudioManager(cw);
		int vol=am.getStreamVolume(AudioManager.STREAM_RING);
		oldVolume=vol<=0?oldVolume:vol;
		am.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
	}
	
	public static void unmute(ContextWrapper cw){
		initAudioManager(cw);
		am.setStreamVolume(AudioManager.STREAM_RING, oldVolume>0?oldVolume:5, 0);
	}
}
