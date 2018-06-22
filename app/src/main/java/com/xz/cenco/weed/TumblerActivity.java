package com.xz.cenco.weed;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.ImageUtil;
import com.cenco.lib.common.ThreadManager;
import com.cenco.lib.common.ToastUtil;
import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xz.cenco.weed.thumber.ThumberApiService;
import com.xz.cenco.weed.thumber.ThumberHelper;
import com.xz.cenco.weed.thumber.Util;
import com.xz.cenco.weed.thumber.bean.Account;
import com.xz.cenco.weed.thumber.bean.BetResult;
import com.xz.cenco.weed.thumber.bean.CarNumberBody;
import com.xz.cenco.weed.thumber.bean.LoginResult;
import com.xz.cenco.weed.thumber.bean.OrcResult;
import com.xz.cenco.weed.thumber.bean.RecordNumResult;
import com.xz.cenco.weed.thumber.bean.SignResult;
import com.xz.cenco.weed.thumber.bean.SignUserResult;
import com.xz.cenco.weed.thumber.sign.Base64Util;
import com.xz.cenco.wisdom.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/25.
 */

public class TumblerActivity extends Activity  {

    private ThumberApiService request;
    private List<Account> users;
    private String TAG = "Thumbler";
    TextView textTv;
    LinearLayout layout;


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

        textTv = findViewById(R.id.textTv);
        layout = findViewById(R.id.layout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ybol.vip/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        request = retrofit.create(ThumberApiService.class);
        users = Util.getUsers();

         dialog=ProgressDialog.show(this,"","请稍后...");
        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<users.size();i++){
                    Account account = users.get(i);
                    sign(account);
                }
                LogUtils.w(TAG,"所有账户登录成功");

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

    private void showUserInfo() {
        for (int i=0;i<users.size();i++){
            Account account = users.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.item_thumber, null);
            TextView infoTv = view.findViewById(R.id.infoTv);
            CheckBox checkbox = view.findViewById(R.id.checkbox);
            account.setCheckBox(checkbox);
            infoTv.setText(i+1+"-"+account.getUsername()+"("+account.getPeopleName()+")==>"+account.getBalance());
            layout.addView(view);
        }
    }


    public void sign(final Account account) {

        Observable<Response<ResponseBody>> observable1 = request.init();

        observable1

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


                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<OrcResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<OrcResult>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response, "获取图片验证码请求成功");

                        byte[] bytes = response.body().bytes();
                        String encode = Base64Util.encode(bytes);
                        String authorization = Util.getYTAuthorization();
                        CarNumberBody carNumberBody = new CarNumberBody(Util.Constant.APP_ID, encode);
                        Gson gson = new Gson();
                        String json = gson.toJson(carNumberBody);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                        Observable<Response<OrcResult>> observable2 = request.ocrText(authorization, body);
                        return observable2;
                    }
                })
                .flatMap(new Function<Response<OrcResult>, ObservableSource<Response<LoginResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<LoginResult>> apply(Response<OrcResult> response) throws Exception {

                        printResponse(response, "解析图片验证码请求成功");

                        OrcResult result = response.body();
                        List<OrcResult.ItemsBean> items = result.getItems();
                        if (items == null || items.size() == 0) {
                            return Observable.error(new Throwable("图片文字识别返回为空"));
                        }

                        OrcResult.ItemsBean itemsBean = items.get(0);
                        String code = itemsBean.getItemstring();
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



                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "onError:" + e.getMessage());


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
                            String accountBalance = getAccountBalance(response.body().string());
                            account.setBalance(accountBalance);
                            LogUtils.w(TAG, account.getUsername()+" 余额：" + accountBalance +",连续签到天数:"+account.getSignDays());
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
        for (int i=0;i<users.size();i++){
            Account account = users.get(i);
            boolean checked = account.getCheckBox().isChecked();
            if (checked){
                list.add(account);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText("1");
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
        builder.create().show();;

    }

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

        ThreadManager.getPoolProxy().execute(new Runnable() {
            @Override
            public void run() {

                Observable<Response<ResponseBody>> code = request.getLastNum(account1.getCookie(), false);

                code.subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.d("--->onError:" + e.getMessage());
                    }

                    public void onComplete() {
                        LogUtils.d("--->onCompleted");
                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d("--->onNext");
                        printResponse(response,"");

                        try {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            RecordNumResult result = gson.fromJson(str, RecordNumResult.class);
                            RecordNumResult.ListBean bean = result.getList().get(0);
                            LogUtils.w("投注期号:"+bean.getIssuenum());


                            Observable<Response<ResponseBody>> bet1 = getBetRequest(request,account1.getCookie(),bean.getIssuenum(),true,money);
                            Observable<Response<ResponseBody>> bet2 = getBetRequest(request,account2.getCookie(),bean.getIssuenum(),false,money);
                            bet1. subscribeOn(Schedulers.io())
                                    .subscribe(new Observer<Response<ResponseBody>>() {

                                        public void onError(Throwable e) {
                                            LogUtils.d("--->onError:" + e.getMessage());
                                        }

                                        public void onComplete() {
                                            LogUtils.d("--->onCompleted");
                                        }

                                        public void onSubscribe(Disposable disposable) {

                                        }

                                        public void onNext(Response<ResponseBody> response) {
                                            LogUtils.d("--->onNext");
                                            printResponse(response,"");

                                            try {
                                                String str = response.body().string();
                                                Gson gson = new Gson();
                                                BetResult result = gson.fromJson(str, BetResult.class);
                                                LogUtils.i(account1.getPeopleName()+account1.getBank()+"(正):"+result.getMsg());

                                            } catch (IOException e) {
                                                LogUtils.e(e);
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                            bet2. subscribeOn(Schedulers.io())
                                    .subscribe(new Observer<Response<ResponseBody>>() {

                                        public void onError(Throwable e) {
                                            LogUtils.d("--->onError:" + e.getMessage());
                                        }

                                        public void onComplete() {
                                            LogUtils.d("--->onCompleted");
                                        }

                                        public void onSubscribe(Disposable disposable) {

                                        }

                                        public void onNext(Response<ResponseBody> response) {
                                            LogUtils.d("--->onNext");
                                            printResponse(response,"");

                                            try {
                                                String str = response.body().string();
                                                Gson gson = new Gson();
                                                BetResult result = gson.fromJson(str, BetResult.class);
                                                LogUtils.i(account2.getPeopleName()+account2.getBank()+"(反):"+result.getMsg());

                                            } catch (IOException e) {
                                                LogUtils.e(e);
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


//                code  .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
//                    public ObservableSource<Response<ResponseBody>> apply(Response<ResponseBody> response) throws Exception {
//
//                        printResponse(response,"");
//
//
//                        try {
//                            String str = response.body().string();
//                            Gson gson = new Gson();
//                            RecordNumResult result = gson.fromJson(str, RecordNumResult.class);
//                            RecordNumResult.ListBean bean = result.getList().get(0);
//                            System.out.println("投注期号:"+bean.getIssuenum());
//                            //issueNum=201806150855&hongtl=1 & DFW=0  &huitl=0&myy=0 zheng 1
//                            //issueNum=201806150922&hongtl=0 & DFW=1  &huitl=0&myy=0 fan 1
//                            //issueNum=201806150925&hongtl=5 & DFW=0  &huitl=0&myy=0  zheng 5
//                            //issueNum=201806150926&hongtl=0 & DFW=5  &huitl=0&myy=0 fan 5
////                            Observable<Response<ResponseBody>> code = request.SubmitBet(coockie, bean.getIssuenum(), "1", "0", "0", "0");
//                            Observable<Response<ResponseBody>> code1 = getBetRequest(request,account1.getCookie(),bean.getIssuenum(),false,2);
//                            return code1;
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return Observable.error(new Throwable("发生异常"));
//
//                    }
//                })
//
//                        .subscribe(new Observer<Response<ResponseBody>>() {
//
//                            public void onError(Throwable e) {
//                                LogUtils.d("--->onError:" + e.getMessage());
////                        beginTask();
//                            }
//
//                            public void onComplete() {
//                                LogUtils.d("--->onCompleted");
////                        beginTask();
//                            }
//
//                            public void onSubscribe(Disposable disposable) {
//
//                            }
//
//                            public void onNext(Response<ResponseBody> response) {
//                                LogUtils.d("--->onNext");
//                                printResponse(response,"");
//
//                                try {
//                                    String str = response.body().string();
//                                    Gson gson = new Gson();
//                                    BetResult result = gson.fromJson(str, BetResult.class);
//                                    System.out.println(result.getMsg());
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        });
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
}
