package cenco.xz.fangliang.wisdom.weed.thumber;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xz.cenco.wisdom.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cenco.xz.fangliang.wisdom.App;
import cenco.xz.fangliang.wisdom.weed.LogInfoActivity;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.Account;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.BDOrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.BetAccount;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.BetResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.RecordNumResult;
import cenco.xz.fangliang.wisdom.weed.thumber.sign.Base64Util;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/25.
 */

public class TumblerActivity extends LogInfoActivity implements TimerHelper.TimerListener {

    private ThumberApiService request;
    private List<Account> users;
    private String TAG = TumblerActivity.class.getSimpleName();
    TextView textTv;
    LinearLayout accountlayout;
    TimerHelper timerHelper;
    CheckBox forceNetCB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tumbler);

        init();


    }

    ProgressDialog dialog;

    private void dismissProgressDialog(){
        if (dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }
    public void init() {

        textTv = (TextView)findViewById(R.id.textTv);
        accountlayout = (LinearLayout)findViewById(R.id.layout);
        forceNetCB = (CheckBox)findViewById(R.id.forceNetCB);

        timerHelper = new TimerHelper(this);
        timerHelper.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ybol.vip/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        request = retrofit.create(ThumberApiService.class);
        users = Util.getUsers();

        action();
    }

    private void action() {
        dialog= ProgressDialog.show(this,"","请稍后...");
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                if (forceNetCB.isChecked()){
                    for (int i=0;i<users.size();i++){
                        Account account = users.get(i);
                        sign(account);
                    }
                }
                LogUtils.w(TAG,"所有账户登录成功");


                File file = new File(Util.getPath());
                if (!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()){
                    LogUtils.d(TAG,"保存操作记录");
                    String s = GsonUtil.toJson(users);
                    IOUtils.writeFileFromString(file,s);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        showUserInfo();
                    }
                });
            }
        });
    }

    private void updateAccount(Account account){
        File file = new File(Util.getPath());
        String s = IOUtils.readFile2String(file);
        List<Account> users = GsonUtil.fromJson(s,new TypeToken<List<Account>>(){}.getType());
        for (int i=0;i<users.size();i++){
            Account account1 = users.get(i);
            if (account.getUsername().equals(account1.getUsername())){
                users.set(i,account);
                break;
            }
        }

        String s1 = GsonUtil.toJson(users);
        IOUtils.writeFileFromString(file,s1);

    }


    private List<BetAccount>  betAccountList;
    private void showUserInfo() {

        //排序
        Collections.sort(users, new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                if(o1==null || TextUtils.isEmpty(o1.getBalance())){
                    return 0;
                }
                if (o2==null || TextUtils.isEmpty(o2.getBalance())){
                    return 0;
                }


                double d1 = Double.parseDouble(o1.getBalance());
                double d2 = Double.parseDouble(o2.getBalance());
                if (d1==d2){
                    return 0;
                }

                if (d1>d2){
                    return -1;
                }else {
                    return 1;
                }
            }
        });

        betAccountList = new ArrayList<>();
        accountlayout.removeAllViews();
        for (int i=0;i<users.size();i++){
            final Account account = users.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_thumber, null);
            final TextView nameTv = view.findViewById(R.id.nameTv);
            final TextView accountTv = view.findViewById(R.id.accountTv);
            final TextView balanceTv = view.findViewById(R.id.balanceTv);
            final TextView cookieTimeTv = view.findViewById(R.id.cookieTimeTv);
            CheckBox checkbox = view.findViewById(R.id.checkbox);
            View  updateBtn= view.findViewById(R.id.updateBtn);
            final int index = i;
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserInfo(index,account,balanceTv);
                }
            });
//            account.setCheckBox(checkbox);
            nameTv.setText(i+1+"-"+account.getPeopleName());
            accountTv.setText(account.getUsername());
            balanceTv.setText(account.getBalance());
            cookieTimeTv.setText(account.getCookieTime()+"");
            accountlayout.addView(view);

            BetAccount betAccount = new BetAccount();
            betAccount.setAccount(account);
            betAccount.setCheckBox(checkbox);
            betAccountList.add(betAccount);

        }
    }

    private void updateUserInfo(final int index, final Account account, final TextView infoTv) {

        dialog = ProgressDialog.show(this,"请稍后...","");

        sign(account);

    }


    public void sign(final Account account) {

        Observable<Response<ResponseBody>> observable1 = request.init();

        observable1
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Response<ResponseBody>>() {
                    public void accept(Response<ResponseBody> response) {

                        String key1 = "PHPSESSID";
                        String key2 = "safedog-flow-item";
                        String key3 = "ZDEDebuggerPresent";
                        String value1 = Util.getHeaderValue(response, key1);
                        String value2 = Util.getHeaderValue(response, key2);
                        String value3 = Util.getHeaderValue(response, key3);
                        StringBuffer sb = new StringBuffer();
                        sb.append(key1).append("=").append(value1).append("; ");
                        sb.append(key2).append("=").append(value2).append("; ");
                        sb.append(key3).append("=").append(value3);

                        String coockie = sb.toString();
                        account.setCookie(coockie);


                        LogUtils.d(TAG,  account.getUsername()+" , cookie>" + coockie);
                    }
                })

                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response, "初始化获取session请求成功");

                        Observable<Response<ResponseBody>> code = request.code(account.getCookie());
                        return code;
                    }
                })


                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<BDOrcResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<BDOrcResult>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response, "获取图片验证码请求成功");

                        byte[] bytes = response.body().bytes();
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        BitmapUtil.saveBitmap(bitmap, C.file.thumber_sign+DateUtil.getDateString()+".png");
                        String encode = Base64Util.encode(bytes);
                        Observable<Response<BDOrcResult>> orc = request.baiduOrc(App.bdOrcToken, encode);
                        return orc;
                    }
                })
                .flatMap(new Function<Response<BDOrcResult>, ObservableSource<Response<LoginResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<LoginResult>> apply(Response<BDOrcResult> response) throws Exception {

                        printResponse(response, "解析图片验证码请求成功");
                        BDOrcResult orcResult = response.body();
                        if (orcResult==null){
                            return Observable.error(new Throwable("图片文字识别返回为空"));
                        }
                        List<BDOrcResult.WordsResultBean> items = orcResult.getWords_result();
                        if (items == null || items.size() == 0) {
                            return Observable.error(new Throwable("图片文字识别返回为空"));
                        }

                        BDOrcResult.WordsResultBean bean = items.get(0);


                        String code = bean.getWords();
                        String format = Util.getFormatCode(code);
                        LogUtils.d(TAG, "原code:" + code + ",格式化code:" + format);
                        if (format.length() != 4) {
                            return Observable.error(new Throwable("图片文字识别字符长度不正确:" + format));
                        }


                        String username = account.getUsername();
                        String password = account.getPassword();
                        Observable<Response<LoginResult>> login = request.login(account.getCookie(), username, password, format);
                        return login;
                    }
                })


                .flatMap(new Function<Response<LoginResult>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<LoginResult> response) throws Exception {

                        printResponse(response, "登录请求成功");

                        LoginResult result = response.body();
                        if (result == null) {
                            return Observable.error(new Throwable("图片文字识别返回为空"));
                        }

                        if (result.getMsg() != null) {
                            return Observable.error(new Throwable(result.getMsg()));
                        }

                        if (result.getSuccess() != 1) {
                            return Observable.error(new Throwable("登录失败"));
                        }

                        LogUtils.i(TAG, "登录结果:" + result.getSuccess());


                        Observable<Response<ResponseBody>> personInfo = request.personInfo(account.getCookie());
                        return personInfo;
                    }
                })


                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "onError:" + e.getMessage());
                        ToastUtil.show(TumblerActivity.this,"error:"+e.getMessage());
                        dismissProgressDialog();

                    }

                    public void onComplete() {
                        LogUtils.w(TAG, "onCompleted");

                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d(TAG, ">onNext");
                        printResponse(response, "获取账户余额请求成功");

                        try {
                            final String accountBalance = getAccountBalance(response.body().string());
                            account.setBalance(accountBalance);
                            LogUtils.w(TAG, account.getIndentify()+" 余额：" + accountBalance +",连续签到天数:"+account.getSignDays());
                            showMessage(account.getIndentify()+" 余额：" + accountBalance +",连续签到天数:"+account.getSignDays());

                            updateAccount(account);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        dismissProgressDialog();
                                        showUserInfo();
                                    }
                                });


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private <T> void printResponse(Response<T> result, String tag) {
        String url = result.raw().request().toString();
        LogUtils.i(TAG, tag + ":" + url);
        T body = result.body();
        LogUtils.d(TAG, "结果:" + body.toString());

    }

    public String getAccountBalance(String str) {
        String key = "余额";
        if (str.contains(key)) {
            int i = str.indexOf(key);
            String substring = str.substring(i, i + 15);
            //余额：0.10</li>
            int start = substring.indexOf("：");
            int end = substring.indexOf("<");
            String balance = substring.substring(start + 1, end);
            return balance;
        }

        return "未获取到";
    }

    public void actionClick(View view) {


        List<Account> list = new ArrayList<>();
        for (int i=0;i<betAccountList.size();i++){
            BetAccount account = betAccountList.get(i);
            boolean checked = account.getCheckBox().isChecked();
            if (checked){
                list.add(account.getAccount());
            }
        }



        if (list.size()!=2){
            ToastUtil.show(this,"不能选择"+list.size()+"个账户");
            return;
        }

        Account account1 = list.get(0);
        Account account2 = list.get(1);
        showMoneyDialog(account1,account2);

    }

    public void showMoneyDialog(final Account account1, final Account account2){

        String cookieTime1 = account1.getCookieTime();
        String cookieTime2 = account2.getCookieTime();
        long date1 = DateUtil.getDate(cookieTime1, DateUtil.FORMAT_YMDHMS).getTime();
        long date2 = DateUtil.getDate(cookieTime2, DateUtil.FORMAT_YMDHMS).getTime();
        long date = new Date().getTime();
        //14:28
        if (date-date1>=(3600-5*60)*1000){
            ToastUtil.show(this,account1.getIndentify()+"token过期，请重新登录");
            return;
        }
        if (date-date2>=(3600-5*60)*1000){
            ToastUtil.show(this,account2.getIndentify()+"token过期，请重新登录");
            return;
        }


        int money1 = (int)Double.parseDouble(account1.getBalance());
        int money2 = (int)Double.parseDouble(account2.getBalance());
        int m = money1<money2?money1:money2;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText(m+"");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String money = editText.getText().toString();
                int m = Integer.parseInt(money);
                bet(account1,account2,m);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
    String issuenum1 = null;
    private void bet(final Account account1, final Account account2, final int money) {

        if (account1==null || account2==null || account1.getCookie()==null || account2.getCookie()==null){
            return;
        }

        if (Double.parseDouble(account1.getBalance())<money){
            ToastUtil.show(this,account1.getPeopleName()+"金额不足"+money);
            return;
        }
        if (Double.parseDouble(account2.getBalance())<money){
            ToastUtil.show(this,account2.getPeopleName()+"金额不足"+money);
            return;
        }



        Observable<Response<ResponseBody>> code1 = request.getLastNum(account1.getCookie(), false);
        code1.subscribeOn(Schedulers.io())
                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
            public ObservableSource<Response<ResponseBody>> apply(Response<ResponseBody> response) throws Exception {

                String str = response.body().string();
                Gson gson = new Gson();
                RecordNumResult result = gson.fromJson(str, RecordNumResult.class);
                RecordNumResult.ListBean bean = result.getList().get(0);
                issuenum1 = bean.getIssuenum();
                LogUtils.w(TAG,"投注期号("+account1.toString()+"):"+bean.getIssuenum());
                showMessage("投注期号("+account1.toString()+"):"+bean.getIssuenum());

                Observable<Response<ResponseBody>> code2 = request.getLastNum(account2.getCookie(), false);;
                return code2;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

            public void onError(Throwable e) {
                LogUtils.d(TAG,"--->onError:" + e.getMessage());
                showMessage(e.getMessage());
            }

            public void onComplete() {
                LogUtils.d(TAG,"--->onCompleted");
            }

            public void onSubscribe(Disposable disposable) {

            }

            public void onNext(Response<ResponseBody> response) {
                LogUtils.d(TAG,"--->onNext");
                printResponse(response,"");

                try {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    RecordNumResult result = gson.fromJson(str, RecordNumResult.class);
                    RecordNumResult.ListBean bean = result.getList().get(0);
                    LogUtils.w(TAG,"投注期号("+account2.toString()+"):"+bean.getIssuenum());
                    showMessage("投注期号("+account2.toString()+"):"+bean.getIssuenum());
                    if (bean.getIssuenum().equalsIgnoreCase(issuenum1)){
                        doubleBet(bean.getIssuenum(), account1, money, account2);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private void doubleBet(final String issuenum, final Account account1, final int money, final Account account2) {

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {



        Observable<Response<ResponseBody>> bet1 = getBetRequest(request,account1.getCookie(),issuenum,true,money);
        Observable<Response<ResponseBody>> bet2 = getBetRequest(request,account2.getCookie(),issuenum,false,money);
        bet1. subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.d(TAG,"--->onError:" + e.getMessage());
                    }

                    public void onComplete() {
                        LogUtils.d(TAG,"--->onCompleted");
                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d(TAG,"--->onNext");
                        printResponse(response,"");

                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            BetResult result = gson.fromJson(str, BetResult.class);
                            LogUtils.i(TAG,account1.getIndentify()+"(正):"+result.getMsg());
                            showMessage(account1.getIndentify()+"(正):"+result.getMsg());
                        } catch (IOException e) {
                            LogUtils.e(e);
                            e.printStackTrace();
                        }

                    }
                });

        bet2. subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.d(TAG,"--->onError:" + e.getMessage());
                    }

                    public void onComplete() {
                        LogUtils.d(TAG,"--->onCompleted");
                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d(TAG,"--->onNext");
                        printResponse(response,"");

                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            BetResult result = gson.fromJson(str, BetResult.class);
                            LogUtils.i(TAG,account2.getIndentify()+"(反):"+result.getMsg());
                            showMessage(account2.getIndentify()+"(反):"+result.getMsg());

                        } catch (IOException e) {
                            LogUtils.e(e);
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }


    private static Observable<Response<ResponseBody>> getBetRequest(ThumberApiService request,String coockie,String issuenum,boolean positive,int money){
        //issueNum=201806150855&hongtl=1 & DFW=0  &huitl=0&myy=0 zheng 1
        //issueNum=201806150922&hongtl=0 & DFW=1  &huitl=0&myy=0 fan 1
        //issueNum=201806150925&hongtl=5 & DFW=0  &huitl=0&myy=0  zheng 5
        //issueNum=201806150926&hongtl=0 & DFW=5  &huitl=0&myy=0 fan 5
        System.out.println(positive?"正":"反");
        String param1 = money+"";
        String param2 = "0";
        if (!positive){
            param1 = "0";
            param2 = money+"";
        }
        Observable<Response<ResponseBody>> code = request.SubmitBet(coockie,issuenum, param1, param2, "0", "0");
        return code;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHelper!=null){
            timerHelper.stop();
        }
    }

    @Override
    public void onTimerRunning(int i, int i1, boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textTv.setText("时间:"+ DateUtil.getDateString());
            }
        });

    }

    boolean isRefresh = false;
    public void refreshClick(View view) {
        if (!forceNetCB.isChecked()){
            return;
        }

        isRefresh = true;

        action();
    }
}
