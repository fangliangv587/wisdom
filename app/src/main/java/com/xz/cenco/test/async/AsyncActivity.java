package com.xz.cenco.test.async;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.xz.cenco.wisdom.R;

public class AsyncActivity extends Activity {

    private Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
    }

    public void taskClick(View view) {



    }


    private class TaskThread extends Thread{


        @Override
        public void run() {
            super.run();

        }
    }
}
