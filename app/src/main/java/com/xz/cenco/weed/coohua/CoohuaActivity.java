package com.xz.cenco.weed.coohua;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;

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

        CoohuaHelper helper = new CoohuaHelper();
        helper.start();
    }

    private void testt() {

        final String fact = "-VvuTFKa7abXO2B6kjO_kNmM5NWmGsFP1fUDlHNuh64XjrYlWXrccy-7xherz_n6aRDtepC7bKdAdlNx__UJJg==";

        byte[] commonKey = NativeJni.getCommonKey();
        LogUtils.d(tag,commonKey.toString());

        String version = "5.2.3.0";
        String userid="8965486";
        String ticket="bbdea1097bb78412faed52ba8f4d3059";
        String str= version+"^"+userid+"^"+ticket;

        byte[] adddd = EncodeUtil.adddd(str.getBytes(), commonKey);
        byte[] encode = Base64.encode(adddd, 10);
        String result = new String(encode);
        LogUtils.i(tag,result);

        LogUtils.w(tag,fact.equalsIgnoreCase(result)+"");

    }
}
