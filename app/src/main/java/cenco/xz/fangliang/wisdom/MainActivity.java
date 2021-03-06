package cenco.xz.fangliang.wisdom;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cenco.lib.common.AssetUtil;
import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.PermissionManager;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.lzy.okgo.model.HttpParams;
import com.mysql.jdbc.Field;
import com.xz.cenco.wisdom.BuildConfig;

import cenco.xz.fangliang.wisdom.core.BaseActivity;

import com.xz.cenco.wisdom.R;
import com.yanzhenjie.alertdialog.AlertDialog;

import java.io.File;
import java.util.Date;

import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.core.SettingActivity;
import cenco.xz.fangliang.wisdom.core.WisdomHelper;
import cenco.xz.fangliang.wisdom.weed.TimerService;
import cenco.xz.fangliang.wisdom.weed.WeedListActivity;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.AccessToken;
import ezy.assist.compat.SettingsCompat;

public class MainActivity extends BaseActivity {

    private TextView trackStateTv;
    private TextView timerStateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wp);
        initView();
        commonPermission();

    }

    private void actionReady() {
        AssetUtil.copyFiles(this, "card", C.file.card_path);

        initBaiduOrcToken();
    }

    private void initBaiduOrcToken() {
//Observable<AccessToken> bdtoken = request.baiduInit("cqGMtaescoBvbh8svWxKnqPV", "V5tTFeY869h4RaOcP7oG31OZw05MtVjq", "client_credentials");

        File file = new File(C.file.bd_ocr);
        if (file.exists()){
            long lastModified = file.lastModified();
            long curTime = new Date().getTime();
            long  dis = curTime - lastModified;
            //30天期限 29天校验
            if (dis<29 * 24 * 3600 *1000){
                App.bdOrcToken = IOUtils.readFile2String(file);
                return;
            }

        }

        String url  = "https://aip.baidubce.com/oauth/2.0/token";
        HttpParams params = new HttpParams();
        params.put("client_id","cqGMtaescoBvbh8svWxKnqPV");
        params.put("client_secret","V5tTFeY869h4RaOcP7oG31OZw05MtVjq");
        params.put("grant_type","client_credentials");
        HttpUtil.post(url, params, new SimpleCallback<AccessToken>() {
            @Override
            public void onSuccess(AccessToken o) {
                String token = o.getAccess_token();
                App.bdOrcToken = token;
                IOUtils.writeFileFromString(C.file.bd_ocr,token);
            }

            @Override
            public void onError(String reason) {

            }
        });
    }

    // 此方法用来判断当前应用的辅助功能服务是否开启
    public boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    private void commonPermission() {
        PermissionManager manager = new PermissionManager(this);
        manager.requestPermission(new PermissionManager.PermissionCallback(){
            @Override
            public void onGrant() {
                actionReady();
                startTimer();
            }

            @Override
            public void onDeny() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackStateTv.setText(isAccessibilitySettingsOn(this) ? "已开启" : "未开启");
    }

    private void initView() {
        trackStateTv = findViewById(R.id.track_state);
        timerStateTv = findViewById(R.id.timerStateTv);
    }


    public void recordClick(View view) {

        if (!isAccessibilitySettingsOn(this)) {
            AlertDialog.newBuilder(this).setCancelable(false).setTitle(com.cenco.lib.common.R.string.title_dialog).setMessage("需要开启辅助权限才能使用此功能，您要开启此功能吗？").setPositiveButton(com.cenco.lib.common.R.string.resume, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivityForResult(intent, C.request.permission_assist);
                }
            }).setNegativeButton(com.cenco.lib.common.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return;
        }
    }

    public void contentClick(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            AlertDialog.newBuilder(this).setCancelable(false).setTitle(com.cenco.lib.common.R.string.title_dialog).setMessage("需要开启悬浮框权限才能使用此功能，您要开启此功能吗？").setPositiveButton(com.cenco.lib.common.R.string.resume, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SettingsCompat.manageDrawOverlays(mContext);
                }
            }).setNegativeButton(com.cenco.lib.common.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            return;
        }

        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }


    public void backupClick(View view) {
    }

    public void restoreClick(View view) {
    }

    public void doctorClick(View view) {
    }


    private void startTimer() {
//        if (BuildConfig.DEBUG) {
//            return;
//        }
        Intent intent = new Intent(MainActivity.this, TimerService.class);
        startService(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == C.request.permission_dialog) {
            contentClick(null);
            return;
        }
        if (requestCode == C.request.permission_assist) {
            recordClick(null);
            return;
        }

    }

    public void testClick(View view) {


    }

    public void weedClick(View view) {

        Intent intent = new Intent(this, WeedListActivity.class);
        startActivity(intent);
    }
}
