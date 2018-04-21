package com.xz.cenco.test.baidu_police_vertify;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cenco.lib.common.BitmapUtil;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleDialogCallback;
import com.cenco.lib.common.log.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.xz.cenco.wisdom.R;
import com.xz.cenco.wisdom.util.C;

public class PoliceVertifyActivity extends Activity implements CameraView.CameraListener {

    private static final String BASE_URL = "https://aip.baidubce.com";

    private static final String ACCESS_TOEKN_URL = BASE_URL
            + "/oauth/2.0/token?";

    private static final String POLICE_VERTIFY_URL = BASE_URL
            + "/rest/2.0/face/v2/person/verify";
    private static final String MATCH_URL = BASE_URL
            + "/rest/2.0/face/v2/match";
    private static final String ALIVE_URL = BASE_URL
            + "/rest/2.0/face/v2/faceverify";

    private String token;

    private byte[] data;
    private Camera.Size size;

    private String cardBase64;
    private  ImageView cameraIv ;
    private TextView takeCompareTv;
    private TextView vertifyTv;
    private TextView aliveTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_vertify);

        CameraView cameraView = findViewById(R.id.cameraView);
        cameraView.addListener(this);
        cameraView.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        initToken();

        initView();
    }

    private void initView() {
        ImageView cardIv = findViewById(R.id.cardIv);
        cameraIv = findViewById(R.id.cameraIv);
        Bitmap bitmap = BitmapUtil.getBitmap(C.file.identifycard_path);
        cardIv.setImageBitmap(bitmap);
        cardBase64 = BitmapUtil.getBase64(C.file.identifycard_path);


        takeCompareTv = findViewById(R.id.takeCompareTv);
        vertifyTv = findViewById(R.id.vertifyTv);
        aliveTv = findViewById(R.id.aliveTv);


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

    /**
     * 参数见 https://ai.baidu.com/docs#/Face-PersonVerify/top
     * @param view
     */
    public void vertifyClick(View view) {
        StringBuilder sb = new StringBuilder(POLICE_VERTIFY_URL);
        sb.append("?access_token=").append(token);
        String url = sb.toString();
        HttpParams params = new HttpParams();

        String base64 = BitmapUtil.cameraPreByteToBase64(data, size);
        params.put("image", base64);
        params.put("id_card_number", "370983198904151355");
        params.put("name", "辛忠");
        params.put("ext_fields", "faceliveness");
        params.put("quality", "use");
        params.put("faceliveness", "use");

        HttpUtil.post(url, params, new SimpleDialogCallback<String>(this) {
            @Override
            public void onSuccess(String s) {
                vertifyTv.setText(s);
            }

            @Override
            public void onError(String s) {
                vertifyTv.setText(s);
            }
        });
    }

    @Override
    public void onCameraFrameData(byte[] data, Camera.Size previewSize) {
//        LogUtils.d("camera",data.toString());
        this.data = data;
        this.size = previewSize;
    }

    public void takeCompareClick(View view) {

        String cameraBase64 = BitmapUtil.cameraPreByteToBase64(data, size);
        Bitmap bitmap = BitmapUtil.base64ToBitmap(cameraBase64);
        cameraIv.setImageBitmap(bitmap);

        StringBuilder sb = new StringBuilder(MATCH_URL);
        sb.append("?access_token=").append(token);
        String url = sb.toString();

        String images = cardBase64+","+cameraBase64;
        String image_liveness= ",faceliveness";
        HttpParams params = new HttpParams();
        params.put("images",images);
        params.put("image_liveness",image_liveness);
        HttpUtil.post(url, params, new SimpleDialogCallback<String>(this) {
            @Override
            public void onSuccess(String s) {
                takeCompareTv.setText(s);
            }

            @Override
            public void onError(String s) {
                takeCompareTv.setText(s);
            }
        });


    }

    /**
     * 文档见 https://ai.baidu.com/docs#/Face-Liveness/top
     * @param view
     */
    public void aliveClick(View view) {

        //官方推荐阈值
        final double threshold= 0.393241;

        StringBuilder sb = new StringBuilder(ALIVE_URL);
        sb.append("?access_token=").append(token);
        String url = sb.toString();

        String cameraBase64 = BitmapUtil.cameraPreByteToBase64(data, size);

        HttpParams params = new HttpParams();
        params.put("image",cameraBase64);
        params.put("face_fields","faceliveness");

        HttpUtil.post(url, params, new SimpleDialogCallback<BAliveResult>(this) {

            @Override
            public void onSuccess(BAliveResult bAliveResult) {

                double faceliveness = bAliveResult.getResult().get(0).getFaceliveness();
                LogUtils.i("活体结果:"+bAliveResult.getResult().get(0).getFaceliveness());
                String str=null;
                if (faceliveness>=threshold){
                    LogUtils.w("是活体");
                    str="活体检测成功";
                }else {
                    LogUtils.e("不是活体");
                    str="活体检测失败";
                }
                aliveTv.setText(str);
            }

            @Override
            public void onError(String s) {

            }
        });

    }
}
