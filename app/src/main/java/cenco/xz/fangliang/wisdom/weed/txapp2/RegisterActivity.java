package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;

import java.text.DecimalFormat;
import java.util.Random;

import cenco.xz.fangliang.wisdom.weed.LogInfoActivity;
import cenco.xz.fangliang.wisdom.weed.MsgCodeHelper;
import cenco.xz.fangliang.wisdom.weed.thumber.Util;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.CodeResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.IncomeResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.RegisterResult;

/**
 * Created by Administrator on 2018/7/10.
 */

public class RegisterActivity extends LogInfoActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText realnameEt;
    EditText phoneEt;
    EditText passEt;
    EditText macEt;
    EditText codeResultEt;
    EditText codeEt;
    EditText visitEt;
    TextView msgInfoTv;
    TextView msgErrorTv;
    TextView registeCountTv;

    Account account;
    boolean virtual;

    int registerCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txapp2_activity_register);

        account = (Account) getIntent().getSerializableExtra("account");
        virtual = getIntent().getBooleanExtra("virtual", false);

        realnameEt = (EditText) findViewById(R.id.realnameEt);
        phoneEt = (EditText) findViewById(R.id.phoneEt);
        passEt = (EditText) findViewById(R.id.passEt);
        macEt = (EditText) findViewById(R.id.macEt);
        codeResultEt = (EditText) findViewById(R.id.codeResultEt);
        codeEt = (EditText) findViewById(R.id.codeEt);
        visitEt = (EditText) findViewById(R.id.visitEt);
        msgInfoTv = (TextView) findViewById(R.id.msgInfoTv);
        msgErrorTv = (TextView) findViewById(R.id.msgErrorTv);
        registeCountTv = (TextView) findViewById(R.id.registeCountTv);

        if (virtual) {
            realnameEt.setText(account.getAliName() + " 增加推广");
//            phoneEt.setEnabled(true);
            passEt.setText(getRandomPass());
            macEt.setText(getRandomMac());
            visitEt.setText(account.getVisitCode());
        } else {
            realnameEt.setText(account.getAliName());
            phoneEt.setText(account.getPhone());
            passEt.setText(account.getPass());
            macEt.setText(account.getMac());
        }

//        msgPhoneClick(null);

    }

    private static String getRandomPass() {
        StringBuffer sb1 = new StringBuffer();
        //a-z
        for (char i = 97; i < 123; i++) {
            sb1.append(i);
        }
        char[] chars = sb1.toString().toCharArray();
        char[] numbers = "0123456789".toCharArray();

        Random random = new Random();
        int[] maxs = {9, 10};
        int[] charLength = {4, 5};
        int max = maxs[random.nextInt(2)];
        int cl = charLength[random.nextInt(2)];
        int nl = max - cl;


        //pass:cl+nl=max
        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < cl; i++) {
            pass.append(chars[random.nextInt(chars.length)]);
        }
        for (int i = 0; i < nl; i++) {
            pass.append(numbers[random.nextInt(numbers.length)]);
        }

        return pass.toString();
    }

    /**
     * 获取随机mac
     *
     * @return
     */
    public static String getRandomMac() {
        String[] str = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "b", "d", "e", "f"};
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int next = random.nextInt(str.length);
            sb.append(str[next]);
        }
        return sb.toString();

    }

    public void getCodeClick(View view) {
        String phone = phoneEt.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            return;
        }

        ApiService.getCode(phone, new SimpleCallback<CodeResult>() {
            @Override
            public void onSuccess(CodeResult s) {
                showMessage("发送验证码:"+(s.isSuccess()?"成功":"失败"));
                codeResultEt.setText(s.getTelCode());
            }

            @Override
            public void onError(String s) {
                LogUtils.w(TAG, "onError:" + s);
            }
        });
    }

    public void registerClick(View view) {
        final String phone = phoneEt.getText().toString();
        final String pass = passEt.getText().toString();
        String mac = macEt.getText().toString();
        String codeResult = codeResultEt.getText().toString();
        String code = codeEt.getText().toString();
        String visitCode = visitEt.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(mac) || TextUtils.isEmpty(codeResult) || TextUtils.isEmpty(code)) {
            ToastUtil.show(this, "必填项为空");
            return;
        }
        ApiService.register(phone, pass, mac, codeResult, code, visitCode, new SimpleCallback<RegisterResult>() {
            @Override
            public void onSuccess(RegisterResult s) {
                ToastUtil.show(RegisterActivity.this, s.getData());
                if (virtual && s.isSuccess()) {
                    showMessage("账户注册成功");
                    registerCount++;
                    virtualUserAction(phone, pass);
                }
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    private void saveVirtualRegister(double money) {
        String phone = phoneEt.getText().toString();
        String pass = passEt.getText().toString();
        String mac = macEt.getText().toString();
        String visitCode = visitEt.getText().toString();

        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format(money);

        String content = "账号:" + phone + ",密码:" + pass + ",MAC:" + mac + ",邀请码:" + visitCode + ",金额:" + format + "\r\n";

        showMessage(content);

        String path = Utils.getVirtualRegisterFilePath(account);
        IOUtils.writeFileFromString(path, content, true);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registeCountTv.setText("注册人数:"+registerCount);
            }
        });


    }

    private void virtualUserAction(final String phone, final String pass) {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {


                String s = ApiService.syncLogin(phone, pass);
                LoginResult result = GsonUtil.fromJson(s, LoginResult.class);
                LoginResult.DataBean user = result.getData();
                int id = user.getId();
                String token = result.getToken();


                try {
                    Thread.sleep((long) (Math.random() * 10 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Random random1 = new Random();
                int count = random1.nextInt(3)+1;
                LogUtils.d(TAG, "金币增加次数:" + count);
                showMessage("金币增加次数:"+count+"，总等待时间:"+(12*count)+"秒");
                double money = 0;
                for (int i = 0; i < count; i++) {

                    double random = Math.random() / 3 + 0.5;
                    money += random;
                    DecimalFormat df = new DecimalFormat("0.00");
                    String format = df.format(random);


                    String s1 = ApiService.syncIncome(id, token, format);
                    IncomeResult result1 = GsonUtil.fromJson(s1, IncomeResult.class);

                    String s2 = ApiService.syncReward(id, token, format);
                    IncomeResult result2 = GsonUtil.fromJson(s2, IncomeResult.class);


                    try {
                        Thread.sleep(12 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                saveVirtualRegister(money);


                try {
                    Random random = new Random();
                    int min = 10 + random.nextInt(5);
                    int second = random.nextInt(60);
                    Thread.sleep((min * 60 + second )* 1000);
                    LogUtils.i(TAG,"等待"+min+"分"+second+"秒");
                    showMessage("等待"+min+"分"+second+"秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                msgPhoneClick(null);

            }
        });
    }

    public void msgPhoneClick(View view) {
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                MsgCodeHelper instant = MsgCodeHelper.getInstant();
                instant.startSingleTask(new MsgCodeHelper.MsgCodeListener() {
                    @Override
                    public void onMsgCodePhoneFail(final String reason) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                msgErrorTv.setText(reason);
                            }
                        });
                    }

                    @Override
                    public void onMsgCodePhoneSuccess(final String phone) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("获取到手机号:"+phone);
                                phoneEt.setText(phone);
                                getCodeClick(null);
                            }
                        });
                    }

                    @Override
                    public void onMsgCodeFail(final String reason) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                msgErrorTv.setText(reason);
                            }
                        });
                    }

                    @Override
                    public void onMsgCodeSuccess(final String code) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("获取到验证码:"+code);
                                codeEt.setText(code);
                                registerClick(null);
                            }
                        });
                    }

                    @Override
                    public void onMsgCodeUserInfo(final String info) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                msgInfoTv.setText(info);
                            }
                        });
                    }
                });
            }
        });
    }

    public void loopRegisterClick(View view) {
        msgPhoneClick(null);
    }
}
