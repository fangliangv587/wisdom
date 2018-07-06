package cenco.xz.fangliang.wisdom.weed.thumber;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.log.LogUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cenco.xz.fangliang.wisdom.weed.thumber.bean.Account;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.CarNumberBody;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.OrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignUserResult;
import cenco.xz.fangliang.wisdom.weed.thumber.sign.Base64Util;
import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2018/4/25.
 */
public class Thumbler {

    private static String TAG = "Thumbler";
    private static List<Account> users;

    public static void main(String args[]) {

//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");

//        users = Util.getUsers();
//        beginTask();


        Account account = Util.getUsers().get(3);
        sign(account);

    }

    private static void beginTask() {
        String dateString = DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD);
        Account account = getAccount(dateString);
        if (account == null) {
            LogUtils.d(dateString + "-任务完成");
            return;
        }
        sign(account);
    }


    public static Account getAccount(String date) {

        for (Account account : users) {
            Map<String, Boolean> result = account.getResult();
            if (!result.containsKey(date) || !result.get(date)) {
                account.putResult(date, false);
                return account;
            }
        }
        return null;
    }


    public static String coockie = null;

    public static void sign(final Account account) {

        LogUtils.d(account.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ybol.vip/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        final ThumberApiService request = retrofit.create(ThumberApiService.class);
        Observable<Response<ResponseBody>> observable1 = request.init();


        observable1
                .doOnNext(new Consumer<Response<ResponseBody>>() {
                    public void accept(Response<ResponseBody> response) throws Exception {

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


                        LogUtils.d("cookie------>" + coockie);
                    }
                })

                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response);

                        Observable<Response<ResponseBody>> code = request.code(coockie);
                        return code;
                    }
                })


                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Response<OrcResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<OrcResult>> apply(Response<ResponseBody> response) throws Exception {

                        printResponse(response);

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

                        printResponse(response);

                        OrcResult result = response.body();
                        List<OrcResult.ItemsBean> items = result.getItems();
                        if (items == null || items.size() == 0) {
                            return Observable.error(new Throwable("图片文字识别返回为空"));
                        }

                        OrcResult.ItemsBean itemsBean = items.get(0);
                        String code = itemsBean.getItemstring();
                        String format = Util.getFormatCode(code);
                        LogUtils.d("原code:" + code + ",格式化code:" + format);
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

                        printResponse(response);

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

                        LogUtils.d("登录结果:" + result.getSuccess());


                        Observable<Response<SignUserResult>> UserSignIn = request.UserSignIn(coockie, 1);
                        return UserSignIn;
                    }
                })


                .flatMap(new Function<Response<SignUserResult>, ObservableSource<Response<SignResult>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<SignResult>> apply(Response<SignUserResult> response) throws Exception {

                        printResponse(response);

                        SignUserResult result = response.body();
                        if (result == null) {
                            return Observable.error(new Throwable("签到列表为空"));
                        }

                        int isNo = result.getObj().getIsNo();
                        if (isNo == 1) {
                            account.putResult(DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD), true);
                            return Observable.error(new Throwable(result.getMsg()));
                        }

                        Observable<Response<SignResult>> signIn = request.signIn(coockie);
                        return signIn;
                    }
                })
                .flatMap(new Function<Response<SignResult>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
                    public ObservableSource<Response<ResponseBody>> apply(Response<SignResult> response) throws Exception {

                        printResponse(response);
                        SignResult result = response.body();
                        if (result.isSuccess()) {
                            account.putResult(DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD), true);
                        } else {
                            account.putResult(DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD), false);
                            return Observable.error(new Throwable("签到失败"));
                        }

                        Observable<Response<ResponseBody>> personInfo = request.personInfo(coockie);

                        return personInfo;
                    }
                })

                .subscribe(new Observer<Response<ResponseBody>>() {

                    public void onError(Throwable e) {
                        LogUtils.d("--->onError:" + e.getMessage());
//                        beginTask();
                    }

                    public void onComplete() {
                        LogUtils.d("--->onCompleted");
//                        beginTask();
                    }

                    public void onSubscribe(Disposable disposable) {

                    }

                    public void onNext(Response<ResponseBody> response) {
                        LogUtils.d("--->onNext");
                        printResponse(response);

                        try {
                            String accountBalance = getAccountBalance(response.body().string());
                            account.setBalance(accountBalance);
                            LogUtils.d("余额：" + accountBalance);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    public static String getAccountBalance(String str) {
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


    private static <T> void printResponse(Response<T> result) {
        String url = result.raw().request().toString();
        LogUtils.d("网络请求成功:" + url);
        T body = result.body();
        LogUtils.d("结果:" + body.toString());

    }


}
