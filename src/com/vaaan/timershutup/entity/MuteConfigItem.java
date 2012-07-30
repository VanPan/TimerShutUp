package com.vaaan.timershutup.entity;

import java.util.Date;

public class MuteConfigItem {
	private Date fireTime=new Date();
	private boolean isMute=true;
	
	public Date getFireTime() {
		return fireTime;
	}
	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}
	public boolean isMute() {
		return isMute;
	}
	public void setMute(boolean isMute) {
		this.isMute = isMute;
	}
}
