package com.xz.cenco.test.baidu_police_vertify;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;

import com.cenco.lib.common.BitmapUtil;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleDialogCallback;
import com.cenco.lib.common.log.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.xz.cenco.wisdom.R;

public class PoliceVertifyActivity extends Activity implements CameraView.CameraListener {

    private static final String BASE_URL = "https://aip.baidubce.com";

    private static final String ACCESS_TOEKN_URL = BASE_URL
            + "/oauth/2.0/token?";

    private static final String POLICE_VERTIFY_URL = BASE_URL
            + "/rest/2.0/face/v2/person/verify";

    private String token;

    private byte[] data;
    private Camera.Size size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_vertify);

        CameraView cameraView = findViewById(R.id.cameraView);
        cameraView.addListener(this);
        cameraView.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        initToken();
    }

    private void initToken() {
        HttpParams params = new HttpParams();
        params.put("client_id", "sTtgxGj54Hzcz7CUefn3Ib5v");
        params.put("client_secret", "bNQZqS3tfe6S3IMgeneU5Y2aKdLV2t5q");
        params.put("grant_type", "client_credentials");
        HttpUtil.post(ACCESS_TOEKN_URL, params, new SimpleDialogCallback<AccessToken>(this) {
            @Override
            public void onSuccess(AccessToken s) {
                LogUtils.d("api",s.getAccess_token());
                token = s.getAccess_token();
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public void vertifyClick(View view) {
        StringBuilder sb = new StringBuilder(POLICE_VERTIFY_URL);
        sb.append("?access_token=").append(token);
        String url = sb.toString();
        HttpParams params = new HttpParams();

        String base64 = BitmapUtil.cameraPreByteToBase64(data, size);
        params.put("image", base64);
        params.put("id_card_number", "370983198904151355");
        params.put("name", "辛忠");
        params.put("ext_fields", "qualities");
        params.put("quality", "use");

        HttpUtil.post(url, params, new SimpleDialogCallback<String>(this) {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(String s) {

            }
        });
    }

    @Override
    public void onCameraFrameData(byte[] data, Camera.Size previewSize) {
//        LogUtils.d("camera",data.toString());
        this.data = data;
        this.size = previewSize;
    }
}
