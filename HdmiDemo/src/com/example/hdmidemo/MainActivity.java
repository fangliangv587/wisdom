package com.example.hdmidemo;

import java.io.File;
import java.util.Scanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private static final String tag = "HdmiReceiver";
	
	private String action1 = "android.intent.action.HDMI_PLUGGED";
	private String action2 = "com.sonyericsson.intent.action.HDMI_EVENT";

	private  BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(tag, "onReceive ");
			String action = intent.getAction();
			Log.d(tag, "action="+action);
			if (action.equals(action1)) {
				boolean state = intent.getBooleanExtra("state", false);
				Log.d(tag, "onReceive state=" + state);
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(action1);
		intentFilter.addAction(action2);
		intentFilter.addAction(Intent.ACTION_PASTE);
		registerReceiver(mReceiver, intentFilter);
		
		boolean hdmiSwitchSet = isHdmiSwitchSet();
		Log.d(tag, "hdmiSwitchSet=" + hdmiSwitchSet);
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
	public void hdmiClick(View v){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PASTE);
//		intent.setAction(actionString);
		sendBroadcast(intent);
	}
	
	
	private static boolean isHdmiSwitchSet() {
	    // The file '/sys/devices/virtual/switch/hdmi/state' holds an int -- if it's 1 then an HDMI device is connected.
	    // An alternative file to check is '/sys/class/switch/hdmi/state' which exists instead on certain devices.
	    File switchFile = new File("/sys/devices/virtual/switch/hdmi/state");
	    if (!switchFile.exists()) {
	        switchFile = new File("/sys/class/switch/hdmi/state");
	    }
	    try {
	        Scanner switchFileScanner = new Scanner(switchFile);
	        int switchValue = switchFileScanner.nextInt();
	        switchFileScanner.close();
	        return switchValue > 0;
	    } catch (Exception e) {
	        return false;
	    }
	}

}
