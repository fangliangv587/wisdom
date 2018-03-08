package com.example.hdmidemo;

import com.nkahoang.kernelswitchobserver.GenericHardwareObserver;
import com.nkahoang.kernelswitchobserver.HardwareNotFoundException;
import com.nkahoang.kernelswitchobserver.UEventStateChangeHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class HDMIDetectActivity extends Activity {

    private GenericHardwareObserver mObserver;
    private String tag="MainActivityHDIM";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startHdmiDetact();
    }

	private void startHdmiDetact() {
		try {
			mObserver = new GenericHardwareObserver("hdmi");
			mObserver.setOnUEventChangeHandler(new UEventStateChangeHandler() {
				@Override
				public void OnUEventStateChange(String NewState) {
					Toast.makeText(HDMIDetectActivity.this, mObserver.getHardwareName() + " " + NewState, Toast.LENGTH_SHORT).show();
					Log.d(tag, mObserver.getHardwareName() + " " + NewState);
				}
			});
			mObserver.start();
		} catch (HardwareNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mObserver.stop();
    }

   
}
