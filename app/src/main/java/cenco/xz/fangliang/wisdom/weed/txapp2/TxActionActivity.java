package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;

import java.text.DecimalFormat;
import java.util.Random;

import cenco.xz.fangliang.wisdom.weed.LogInfoActivity;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.IdentifyResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.IncomeResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.UserInfoResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.WithdrawResult;

/**
 * Created by Administrator on 2018/7/11.
 */

public class TxActionActivity  extends LogInfoActivity {

    TextView realnameEt;
    TextView phoneEt;
    TextView passEt;
    TextView balanceEt;
    Account account;
    LoginResult loginResult;
    Button identifyBtn;
    Button withdrawBtn;
    Button withdrawlistBtn;
    Button incomeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txapp2_activity_action);

        account = (Account) getIntent().getSerializableExtra("account");

        realnameEt = (TextView)findViewById(R.id.realnameEt);
        phoneEt = (TextView)findViewById(R.id.phoneEt);
        passEt = (TextView)findViewById(R.id.passEt);
        balanceEt = (TextView)findViewById(R.id.balanceEt);
        identifyBtn =(Button) findViewById(R.id.identifyBtn);
        withdrawBtn = (Button)findViewById(R.id.withdrawBtn);
        withdrawlistBtn = (Button)findViewById(R.id.withdrawlistBtn);
        incomeBtn = (Button)findViewById(R.id.incomeBtn);

        identifyBtn.setEnabled(false);
        withdrawBtn.setEnabled(false);
        withdrawlistBtn.setEnabled(false);
        incomeBtn.setEnabled(false);

        realnameEt.setText(account.getAliName());
        phoneEt.setText(account.getPhone());
        passEt.setText(account.getPass());

        loginClick(null);

    }

    public void loginClick(View view) {
        ApiService.login(account.getPhone(), account.getPass(), new SimpleCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult o) {
                loginResult = o;
                LoginResult.DataBean user = o.getData();
                showMessage(user.getDisplayName()+"<====>"+user.getOfficeTel());

                if (user.isVertify()){
                    identifyBtn.setEnabled(false);
                    withdrawBtn.setEnabled(true);
                    withdrawlistBtn.setEnabled(true);
                    incomeBtn.setEnabled(true);
                }else {
                    identifyBtn.setEnabled(true);
                    withdrawBtn.setEnabled(false);
                    withdrawlistBtn.setEnabled(false);
                    incomeBtn.setEnabled(false);
                }

                getUserInfo();


            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public void getUserInfo(){
        if (loginResult==null){
            ToastUtil.show(this,"请先登录");
            return;
        }
        LoginResult.DataBean user = loginResult.getData();
        int id = user.getId();
        String token = loginResult.getToken();
        ApiService.getUserInfo(id, token, new SimpleCallback<UserInfoResult>() {
            @Override
            public void onSuccess(UserInfoResult o) {
                balanceEt.setText(o.getData().getGroupTel());
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public void identifyClick(View view) {

        if (loginResult==null){
            ToastUtil.show(this,"请先登录");
            return;
        }

        LoginResult.DataBean user = loginResult.getData();

        if (user.isVertify()){
            ToastUtil.show(this,"该用户已经认证");
            return;
        }

        int id = user.getId();
        String token = loginResult.getToken();
        String officePhone = account.getAliPhone();
        String realname = account.getAliName();
        String sexTag = account.getSexTag();

        ApiService.identify(id,token,officePhone,realname,sexTag,new SimpleCallback<IdentifyResult>(){
            @Override
            public void onSuccess(IdentifyResult s) {
                if (s.isSuccess()){
                    identifyBtn.setEnabled(false);
                    withdrawBtn.setEnabled(true);
                    withdrawlistBtn.setEnabled(true);
                    incomeBtn.setEnabled(true);
                }
                ToastUtil.show(TxActionActivity.this,s.getData());
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public void withdrawClick(View view) {
        if (loginResult==null){
            ToastUtil.show(this,"请先登录");
            return;
        }

        LoginResult.DataBean user = loginResult.getData();
        if (!user.isVertify()){
            ToastUtil.show(this,"请先认证");
            return;
        }
        int id = user.getId();
        String token = loginResult.getToken();
        ApiService.withdraw(id, token, "0.5", "1", new SimpleCallback<WithdrawResult>() {
            @Override
            public void onSuccess(WithdrawResult s) {
                ToastUtil.show(TxActionActivity.this,s.getSuccessMessage());
            }

            @Override
            public void onError(String s) {
                ToastUtil.show(TxActionActivity.this,s);
            }
        });


    }

    public void withdrawListClick(View view) {
        if (loginResult==null){
            ToastUtil.show(this,"请先登录");
            return;
        }

        LoginResult.DataBean user = loginResult.getData();
        if (user.getOfficeTel()==null){
            ToastUtil.show(this,"请先认证");
            return;
        }
        int id = user.getId();
        String token = loginResult.getToken();
        Intent intent = new Intent(this, RecordListActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("token",token);
        startActivity(intent);

    }

    public void localWithdrawListClick(View view) {
        Intent intent = new Intent(this, LocalRecordListActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }

    private boolean income = false;
    public void incomeClick(View view) {

        if (loginResult==null){
            ToastUtil.show(this,"请先登录");
            return;
        }

        if (income){
            incomeBtn.setText("开始赚钱");
            income=false;
            balanceClick(null);
            return;
        }

        incomeBtn.setText("停止赚钱");
        income = true;
        LoginResult.DataBean user = loginResult.getData();
        final int id = user.getId();
        final String token = loginResult.getToken();

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                while (income){

                    double random = Math.random()/3+0.5;
                    DecimalFormat df = new DecimalFormat("0.00");
                    String format = df.format(random);

                    showMessage("随机数:"+format);

                    String s1 = ApiService.syncIncome(id, token, format);
                    IncomeResult result1 = GsonUtil.fromJson(s1, IncomeResult.class);
                    showMessage(result1.getSuccessMessage());
                    String s2 = ApiService.syncReward(id, token, format);
                    IncomeResult result2 = GsonUtil.fromJson(s2, IncomeResult.class);
                    showMessage(result2.getSuccessMessage());

                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void balanceClick(View view) {
        getUserInfo();
    }


}
