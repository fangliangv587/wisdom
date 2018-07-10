package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.cenco.lib.common.http.SimpleCallback;
import com.xz.cenco.wisdom.R;

/**
 * Created by Administrator on 2018/7/10.
 */

public class RegisterActivity extends Activity {

    EditText realnameEt;
    EditText phoneEt;
    EditText passEt;
    EditText macEt;
    EditText codeEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txapp2_activity_register);
        Account account = Utils.getAccount().get(0);

        realnameEt = findViewById(R.id.realnameEt);
        phoneEt = findViewById(R.id.phoneEt);
        passEt = findViewById(R.id.passEt);
        macEt = findViewById(R.id.macEt);
        codeEt = findViewById(R.id.codeEt);

        realnameEt.setText(account.getRealname());
        phoneEt.setText(account.getPhone());
        passEt.setText(account.getPass());
        macEt.setText(account.getMac());

    }

    public void getCodeClick(View view) {
        String phone = phoneEt.getText().toString();

        ApiService.getCode(phone,new SimpleCallback<String>(){
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public void registerClick(View view) {
    }
}
