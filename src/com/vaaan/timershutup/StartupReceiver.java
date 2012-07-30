package com.vaaan.timershutup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, ShutUpService.class));
		Toast.makeText(context, "定时静音服务启动", Toast.LENGTH_LONG).show();
	}

}
