package cenco.xz.fangliang.wisdom.weed;

import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;

import cenco.xz.fangliang.wisdom.weed.qutoutiao.QuNewsHelper;

/**
 * Created by Administrator on 2018/6/22.
 */

public class TestMessageActivity extends LogInfoActivity {

    private static final String TAG = TestMessageActivity.class.getSimpleName();

    private int position;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.activity_message_test);
        TextView tv =(TextView) findViewById(R.id.text);
        tv.setText("hhhh");



    }

    public void btnClick(View view) {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {




            }
        });
    }
}
