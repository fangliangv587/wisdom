package com.xz.cenco.weed;

import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.xz.cenco.weed.qutoutiao.QuNewsHelper;
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

        QuNewsHelper helper = new QuNewsHelper();
        helper.start();

    }

    public void btnClick(View view) {
        position++;


        String str ="QTk1ODAxM0I3MkZEOTQyQkQyMUFBNzE2OEI4MDM2MjkuY0dGeVlXMGZNVGMxT1dVNE1UVXRNbVZpTnkwME4yWmtMVGhpWmpJdE9UQTFaalF6WldNNVltWTJIblpsY25OcGIyNGZNUjV3YkdGMFptOXliUjloYm1SeWIybGsuCaslUoXWXUwyjIr0ESkg8anyZ%2FLusV96OmBH8XxRPDODULN4HK8Cw3f%2FJjf1xqigjlFwRJHOj9AFuNTlXU4DcvvhNOqOi0DvmZre2ULXQeEZCjQ94Gyhge1005YaKIOeyyLDZPwuMooi%2FNz9v91EcPHAOGoaKfnDwkWDK3L9pf74zhIMnjLo1nEPzJ7oB1CaESNKsyB%2Bpr49cFfeCf4SfKgbHwSSbwnDp1tNLUowgFP1NpQ965d%2Bk%2FoetmKx605T4W4%2FR%2BHYVIJME5pWbmWovrYjpYnMhi14sVbpioGc6cPVe%2FpKjU%2BSGh9mJ1gLY%2BfM72TCybrOXpFoA%2BtExpXeI%2B8%2FbtNJbP3x7eyDfist%2BR2THRfqUbEyb0egfeAJMMmJtnbt9tqCngGhWKk0yJQArQ0LJ9i%2B2FYb5BM8%2F7XQgLHrILqY77D1ArHNDJr45Tith8NgFbD5NgFgX3aXOsSA3PnDRqxyV1B%2BeKeFiS3JigoDt17%2BqFENisdjqp0bi1d9txV7QFklVNwKew5JCOlMcpd1Rzrm3QKiiNy6hFoQSev9Da2sgDSNan89h%2FbuRyrukKcaKt0LfvTRcAwp6UxB6pzLMRxkj6kbA7uc7CDazkvh";


        byte[] decode = Base64.decode(str,0);
        String s = new String(decode);

        showMessage("click"+s);
    }
}
