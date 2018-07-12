package cenco.xz.fangliang.wisdom.weed.txapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.xz.cenco.wisdom.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.IncomeResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.WithdrawResult;

/**
 * Created by Administrator on 2018/7/11.
 */

public class TxAppActivity extends Activity implements TimerHelper.TimerListener {

    List<Account> userlist;

    private final static int type_register = 0x0001;
    private final static int type_login = 0x0002;

    private int type;
    private Button addMoneyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.txapp2_activity_main);

        addMoneyBtn = findViewById(R.id.initBtn);
        userlist = getUsers();

    }

    private List<Account> getUsers(){
        return  Utils.getAccount();
    }

    private void showUsers(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(layout);

        for (int i=0;i<userlist.size();i++){
            Account account = userlist.get(i);
            TextView textView = new TextView(this);
            textView.setPadding(10,10,10,10);
            textView.setText(account.getIndentify());
            textView.setTag(account);
            layout.addView(textView);
            textView.setOnClickListener(userClickListener);
        }

        builder.setView(scrollView);
        builder.create().show();
    }

    private View.OnClickListener userClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Account account  = (Account) v.getTag();

            if (type == type_register){
                if (account.isRegister()){
                    ToastUtil.show(TxAppActivity.this,"该用户已经注册");
                    return;
                }
                Intent intent = new Intent(TxAppActivity.this, RegisterActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }else if (type==type_login){
                if (!account.isRegister()){
                    ToastUtil.show(TxAppActivity.this,"该用户未注册");
                    return;
                }
                Intent intent = new Intent(TxAppActivity.this, TxActionActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        }
    };

    public void registerClick(View view) {
        type = type_register;
        showUsers();

    }

    public void loginClick(View view) {
        type = type_login;
        showUsers();
    }

    private boolean isAddMoney = false;
    public void addMoneyClick(View view) {

        if (isAddMoney){
            isAddMoney =  false;
            addMoneyBtn.setText("开始增加余额");
            return;
        }
        addMoneyBtn.setText("停止增加余额");
        isAddMoney = true;
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<userlist.size();i++){
                    Account account = userlist.get(i);
                    String s = ApiService.syncLogin(account.getPhone(), account.getPass());
                    LoginResult result = GsonUtil.fromJson(s, LoginResult.class);
                    LoginResult.DataBean user = result.getData();
                    int id = user.getId();
                    String token = result.getToken();
                    account.setId(id);
                    account.setToken(token);

                    try {
                        Thread.sleep((long) (Math.random()*10 * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (isAddMoney){
                    for (int i=0;i<userlist.size();i++){
                        Account account = userlist.get(i);
                        int id = account.getId();
                        String token = account.getToken();
                        double random = Math.random()/3+0.5;
                        DecimalFormat df = new DecimalFormat("0.00");
                        String format = df.format(random);

                        LogUtils.v("随机数:"+format);

                        String s1 = ApiService.syncIncome(id, token, format);
                        IncomeResult result1 = GsonUtil.fromJson(s1, IncomeResult.class);
                        LogUtils.d(result1.getSuccessMessage());
                        String s2 = ApiService.syncReward(id, token, format);
                        IncomeResult result2 = GsonUtil.fromJson(s2, IncomeResult.class);
                        LogUtils.i(result2.getSuccessMessage());

                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    public void actionClick(View view) {

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<userlist.size();i++){
                    Account account = userlist.get(i);

                    String path = Utils.getFilePath(account);
                    File file = new File(path);
                    if (file.exists()){
                        LogUtils.w(account.getIndentify()+" 已经提现过了！");
                        continue;
                    }


                    String s = ApiService.syncLogin(account.getPhone(), account.getPass());
                    LoginResult result = GsonUtil.fromJson(s, LoginResult.class);
                    LoginResult.DataBean user = result.getData();
                    int id = user.getId();
                    String token = result.getToken();
                    account.setId(id);
                    account.setToken(token);

                    try {
                        Thread.sleep(4*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String money = "0.5";
                    String s1 = ApiService.syncWithdraw(id, token, money, "1");
                    WithdrawResult result1 = GsonUtil.fromJson(s1, WithdrawResult.class);
                    String message = result1.getSuccessMessage();
                    account.setWithdrawStatus(result1.getState());
                    account.setTxmoney(money);

                    LogUtils.w(account.getIndentify()+message);


                    if (!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    String json = GsonUtil.toJson(account);
                    IOUtils.writeFileFromString(file,json);

                    try {
                        Thread.sleep((long) ((Math.random()*10+10) * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void timerClick(View view) {

        TimerHelper helper = new TimerHelper(this);
        TxMoneyHelper moneyHelper = new TxMoneyHelper();
        helper.addListener(moneyHelper);
        helper.start();
    }

    @Override
    public void onTimerRunning(int i, int i1, boolean b) {

    }
}
