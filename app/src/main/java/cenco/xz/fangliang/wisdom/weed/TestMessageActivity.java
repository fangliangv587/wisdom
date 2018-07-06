package cenco.xz.fangliang.wisdom.weed;

import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.xz.cenco.wisdom.R;

import cenco.xz.fangliang.wisdom.weed.qutoutiao.QuNewsHelper;

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

        QuNewsHelper helper = new QuNewsHelper();
        helper.start();

    }

    public void btnClick(View view) {
        position++;




        showMessage("click"+position);
    }
}
