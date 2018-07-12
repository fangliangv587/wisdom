package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;

import cenco.xz.fangliang.wisdom.weed.LogInfoActivity;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.CodeResult;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txapp2_activity_register);
//        Account account = Utils.getAccount().get(0);

        Account account = (Account) getIntent().getSerializableExtra("account");

        realnameEt = (EditText)findViewById(R.id.realnameEt);
        phoneEt = (EditText)findViewById(R.id.phoneEt);
        passEt = (EditText)findViewById(R.id.passEt);
        macEt =(EditText) findViewById(R.id.macEt);
        codeResultEt = (EditText)findViewById(R.id.codeResultEt);
        codeEt =(EditText) findViewById(R.id.codeEt);

        realnameEt.setText(account.getAliName());
        phoneEt.setText(account.getPhone());
        passEt.setText(account.getPass());
        macEt.setText(account.getMac());

    }

    public void getCodeClick(View view) {
        String phone = phoneEt.getText().toString();

        ApiService.getCode(phone,new SimpleCallback<CodeResult>(){
            @Override
            public void onSuccess(CodeResult s) {
                showMessage("onSuccess:"+s);
                codeResultEt.setText(s.getTelCode());
            }

            @Override
            public void onError(String s) {
                LogUtils.w(TAG,"onError:"+s);
            }
        });
    }

    public void registerClick(View view) {
        String phone = phoneEt.getText().toString();
        String pass = passEt.getText().toString();
        String mac = macEt.getText().toString();
        String codeResult = codeResultEt.getText().toString();
        String code = codeEt.getText().toString();
        ApiService.register(phone,pass,mac,codeResult,code,new SimpleCallback<RegisterResult>(){
            @Override
            public void onSuccess(RegisterResult s) {
                ToastUtil.show(RegisterActivity.this,"注册成功");
            }

            @Override
            public void onError(String s) {

            }
        });
    }
}
