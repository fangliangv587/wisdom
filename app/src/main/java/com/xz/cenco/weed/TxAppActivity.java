package com.xz.cenco.weed;

import android.app.Activity;
import android.os.Bundle;

import com.cenco.lib.common.ThreadManager;
import com.xz.cenco.weed.txapp.DBHelper;
import com.xz.cenco.weed.txapp.Function;
import com.xz.cenco.wisdom.R;

public class TxAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_app);

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                DBHelper helper = new DBHelper();

                helper.getNotCashUserAtToday();
            }
        });



    }
}
