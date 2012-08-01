package com.vaaan.timershutup;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioManager;

public class GlobalAudioManager {
	private static AudioManager am;
	
	private static synchronized void initAudioManager(ContextWrapper cw){
		if(am!=null) return;
		am=(AudioManager)cw.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public static void mute(ContextWrapper cw){
		initAudioManager(cw);
		am.setStreamMute(AudioManager.STREAM_RING, true);
	}
	
	public static void unmute(ContextWrapper cw){
		initAudioManager(cw);
		am.setStreamMute(AudioManager.STREAM_RING, false);
	}
}
