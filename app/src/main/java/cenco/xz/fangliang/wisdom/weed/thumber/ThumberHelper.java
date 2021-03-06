package cenco.xz.fangliang.wisdom.weed.thumber;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cenco.lib.common.BitmapUtil;
import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cenco.xz.fangliang.wisdom.App;
import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.Account;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.BDOrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.CarNumberBody;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.OrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignUserResult;
import cenco.xz.fangliang.wisdom.weed.thumber.sign.Base64Util;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/29 0029.
 */

public class ThumberHelper implements TimerHelper.TimerListener {

    private String TAG = ThumberHelper.class.getSimpleName();
    private List<Account> users;
    private ThumberApiService request;
    private final int  total = 30 * 60;//30分钟
    private boolean loop = false;
    private Context context;

    private Date upDate;
    private Date downDate;

    public ThumberHelper(Context context) {
        this.context =context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ybol.vip/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        request = retrofit.create(ThumberApiService.class);
        users = Util.getUsers();

        upDate = DateUtil.createDate(2018,1,1,23,59,0);
        downDate = DateUtil.createDate(2018,1,1,6,0,0);

    }


    public void start() {
        beginTask();
    }




    private void beginTask() {


        String dateString = DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD);
        Account account = getAccount(dateString);
        loop = false;
        if (account == null) {
            LogUtils.i(TAG, dateString + "-任务完成");
            loop = true;

            File file = new File(Util.getPath());
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if (!file.exists()){
                LogUtils.d(TAG,"保存操作记录");
                String s = GsonUtil.toJson(users);
                IOUtils.writeFileFromString(file,s);
            }

            return;
        }
        sign(account);
    }

    private void printUserInfo() {
        for (Account account : users) {
            LogUtils.d(TAG,account.toString());
        }
    }

    private int index;
    public Account getAccount(String date) {

        for (int i=0;i<users.size();i++) {
            Account account = users.get(i);
            String signDate = account.getSignDate();
            if (!date.equals(signDate)){
                return account;
            }

        }
        return null;
    }


    public String coockie = null;

    public void sign(final Account account) {

        Observable<Response<ResponseBody>> observable1 = request.init();
        LogUtils.w(TAG,index+"/"+users.size()+"===>"+account.getUsername()+":"+account.getBank());

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

                        coockie = sb.toString();
                        account.setCookie(coockie);

                        LogUtils.d(TAG,  account.getUsername()+" , cookie>" + coockie);
                    }
                })

                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response, "初始化获取session请求成功");

                        Observable<Response<ResponseBody>> code = request.code(coockie);
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
                        Observable<Response<LoginResult>> login = request.login(coockie, username, password, format);
                        return login;
                    }
                })


                .flatMap(new Function<Response<LoginResult>, ObservableSource<Response<SignUserResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<SignUserResult>> apply(Response<LoginResult> response) throws Exception {

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


                        Observable<Response<SignUserResult>> UserSignIn = request.UserSignIn(coockie, 1);
                        return UserSignIn;
                    }
                })


                .flatMap(new Function<Response<SignUserResult>, ObservableSource<Response<SignResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<SignResult>> apply(Response<SignUserResult> response) throws Exception {

                        printResponse(response, "获取签到列表请求成功");

                        SignUserResult result = response.body();
                        if (result == null) {
                            return Observable.error(new Throwable("签到列表为空"));
                        }

                        int isNo = result.getObj().getIsNo();
                        account.setSignDays(result.getObj().getUser_CheckNum());
                        if (isNo == 1) {

                            account.setSignDate(DateUtil.getDateString(DateUtil.FORMAT_YMD));
                            return Observable.error(new Throwable(result.getMsg()));
                        }

                        Observable<Response<SignResult>> signIn = request.signIn(coockie);
                        return signIn;
                    }
                })
                .flatMap(new Function<Response<SignResult>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<SignResult> response) throws Exception {

                        printResponse(response, "签到请求成功");
                        SignResult result = response.body();
                        if (result.isSuccess()) {
                            account.setSignDate(DateUtil.getDateString(DateUtil.FORMAT_YMD));
                            LogUtils.i(TAG, account.getUsername()+" 签到成功");
                        } else {
                            return Observable.error(new Throwable("签到失败"));
                        }

                        Observable<Response<ResponseBody>> personInfo = request.personInfo(coockie);

                        return personInfo;
                    }
                })

                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "onError:" + e.getMessage());
                        try {
                            Thread.sleep(1000*2);
                        } catch (InterruptedException e1) {
                            LogUtils.e(e);
                            e1.printStackTrace();
                        }
                        beginTask();
                    }

                    public void onComplete() {
                        LogUtils.i(TAG, "onCompleted");
                        beginTask();
                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d(TAG, ">onNext");
                        printResponse(response, "获取账户余额请求成功");

                        try {
                            String accountBalance = getAccountBalance(response.body().string());
                            account.setBalance(accountBalance);
                            LogUtils.w(TAG, account.getUsername()+"===>"+account.getBank()+" 余额：" + accountBalance +",连续签到天数:"+account.getSignDays());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

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


    private <T> void printResponse(Response<T> result, String tag) {
        String url = result.raw().request().toString();
        LogUtils.i(TAG, tag + ":" + url);
//        T body = result.body();
//        LogUtils.d(TAG, "结果:" + body.toString());

    }

    @Override
    public void onTimerRunning(int current, int n, boolean b) {
        if (loop && current % total == 0 && isValidTime() ){
//            LogUtils.w(TAG,"新一轮的查询签到",true);
            start();
        }
    }

    private boolean isValidTime(){
        boolean valid = DateUtil.isInPeriodDate(new Date(), downDate, upDate, DateUtil.FORMAT_HMS);
        LogUtils.i(TAG,"时间段检查："+valid);
        return valid;
    }

    public void stop() {
    }
}
