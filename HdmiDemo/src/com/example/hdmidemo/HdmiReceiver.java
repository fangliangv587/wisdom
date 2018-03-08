package com.example.hdmidemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HdmiReceiver extends BroadcastReceiver {

	private static final String tag = "HdmiReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(tag, "HdmiReceiver onReceive");
	}

}
