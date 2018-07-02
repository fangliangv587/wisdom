package com.xz.cenco.weed;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;

/**
 * Created by Administrator on 2018/6/22.
 */

public class LogInfoActivity extends Activity {

    private TextView messageTv;
    private LinearLayout contentLayout;
    private View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(createView());
        onCreate();
    }

    protected void onCreate(){

    }

    private View createView(){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        messageTv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getMessageHeight());
        messageTv.setLayoutParams(params);
        messageTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        messageTv.setBackgroundColor(Color.YELLOW);

        layout.addView(messageTv);

        contentLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(params1);
        layout.addView(contentLayout);

        return layout;
    }

    protected int getMessageHeight(){
        return 100;
    }


    public void setContentView(int res){
        contentView = LayoutInflater.from(this).inflate(res, null);
        contentLayout.addView(contentView);
    }

    public View findViewById(int res){
        return contentView.findViewById(res);
    }


    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageTv.setText(messageTv.getText().toString() + "\n " + DateUtil.getDateString()+"  " +message);
                int offset = messageTv.getLineCount() * messageTv.getLineHeight();
                if (offset > messageTv.getHeight()) {
                    messageTv.scrollTo(0, offset - messageTv.getHeight());
                }
            }
        });

    }


}
