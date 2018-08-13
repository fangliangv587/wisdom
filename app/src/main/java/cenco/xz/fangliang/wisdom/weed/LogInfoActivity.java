package cenco.xz.fangliang.wisdom.weed;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        createControlView(layout);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.addView(relativeLayout,params1);

        contentLayout = new LinearLayout(this);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(contentLayout,params2);

        messageTv = new TextView(this);
        messageTv.setVisibility(View.INVISIBLE);
        messageTv.setBackgroundColor(Color.parseColor("#99666666"));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        messageTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        relativeLayout.addView(messageTv,params);

        return layout;
    }

    private void createControlView(LinearLayout layout) {
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        LinearLayout.LayoutParams paramsRelative = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getMessageHeight());
//        layout.addView(relativeLayout,paramsRelative);
//
//        messageTv = new TextView(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        messageTv.setMovementMethod(ScrollingMovementMethod.getInstance());
//        messageTv.setBackgroundColor(Color.YELLOW);
//
//        relativeLayout.addView(messageTv,params);


        Button button = new Button(this);
        button.setText("显隐日志");
        layout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageTv.getVisibility()==View.VISIBLE){
                    messageTv.setVisibility(View.INVISIBLE);
                }else {
                    messageTv.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    protected int getMessageHeight(){
        return 300;
    }


    public void setContentView(int res){
        contentView = LayoutInflater.from(this).inflate(res, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);
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
