package cenco.xz.fangliang.wisdom.weed.coohua;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import com.coohua.android.jni.NativeJni;
import com.xz.cenco.wisdom.R;

/**
 * Created by Administrator on 2018/5/4.
 */

public class CoohuaActivity  extends Activity {

    private String tag ="coohua";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coohua);

//        CoohuaHelper helper = new CoohuaHelper(this);
//        helper.start();

    }


}
