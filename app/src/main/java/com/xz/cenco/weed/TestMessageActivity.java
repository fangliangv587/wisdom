package com.xz.cenco.weed;

import android.view.View;
import android.widget.TextView;

import com.xz.cenco.wisdom.R;

/**
 * Created by Administrator on 2018/6/22.
 */

public class TestMessageActivity extends LogInfoActivity {

    private int position;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.activity_message_test);
        TextView tv =(TextView) findViewById(R.id.text);
        tv.setText("hhhh");

    }

    public void btnClick(View view) {
        position++;
        showMessage("click"+position);
    }
}
