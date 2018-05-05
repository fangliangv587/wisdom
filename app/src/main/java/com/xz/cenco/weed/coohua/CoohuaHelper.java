package com.xz.cenco.weed.coohua;

import com.cenco.lib.common.log.LogUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xz.cenco.weed.coohua.bean.IncomeResult;
import com.xz.cenco.weed.coohua.bean.HomeResult;
import com.xz.cenco.weed.coohua.bean.LoginResult;
import com.xz.cenco.weed.coohua.bean.OpenBoxResult;
import com.xz.cenco.weed.coohua.bean.SignResult;
import com.xz.cenco.weed.coohua.bean.User;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/4.
 * 1.每天签到一次 ----> v
 * 2.每3个小时可开启宝箱 ----> v
 * 3.每小时打开首页 ----> v
 * 4.首页新闻阅读，最多30次 ----> x
 */

public class CoohuaHelper {

    private CohuaApiService request;

    private String TAG = "coohua";

    public CoohuaHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ybol.vip/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        request = retrofit.create(CohuaApiService.class);
        User user = Util.getUsers().get(2);

        beginTask(user);
    }

    private void beginTask(final User user) {



        Observable<Response<LoginResult>> login = request.login(user.getAndroidId(),user.getAccountNum(),user.getPassword(),user.getBlueMac(),user.getCpuModel(),user.getImei(),user.getWifiMac(),user.getBlackBox(),user.getVersion(),user.getStorageSize(),user.getMarkId(),user.getScreenSize(),user.getModel());
        login.subscribeOn(Schedulers.io())

                .flatMap(new Function<Response<LoginResult>, ObservableSource<Response<SignResult>>>() {
                    public ObservableSource<Response<SignResult>> apply(Response<LoginResult> response)  {

                        printObj(response,"登录");

                        LoginResult.ResultBean result = response.body().getResult();
                        if (result==null || result.getTicket()==null){
                            return Observable.error(new Throwable("登录失败"));
                        }

                        int coohuaId = result.getCoohuaId();
                        String ticket = result.getTicket();
                        String baseKey = Util.getBaseKey(coohuaId, ticket);
                        user.setCoohuaId(coohuaId);
                        user.setBaseKey(baseKey);

                        LogUtils.w(TAG,"baseKey="+baseKey);

                        Observable<Response<SignResult>> signIn = request.signIn(user.getCoohuaId(),user.getBaseKey(),Util.Constant.version);
                        return signIn;
                    }
                })

                .flatMap(new Function<Response<SignResult>, ObservableSource<Response<OpenBoxResult>>>() {
                    public ObservableSource<Response<OpenBoxResult>> apply(Response<SignResult> response)  {

                        printObj(response,"签到");

                        Observable<Response<OpenBoxResult>> openBox = request.openBox(user.getCoohuaId(), user.getBaseKey(), Util.Constant.version);
                        return openBox;
                    }
                })


                .flatMap(new Function<Response<OpenBoxResult>, ObservableSource<Response<HomeResult>>>() {
                    public ObservableSource<Response<HomeResult>> apply(Response<OpenBoxResult> response)  {

                        printObj(response,"开宝箱");

                        Observable<Response<HomeResult>> home = request.home(user.getCoohuaId(),user.getBaseKey());
                        return home;
                    }
                })


                .flatMap(new Function<Response<HomeResult>, ObservableSource<Response<IncomeResult>>>() {
                    public ObservableSource<Response<IncomeResult>> apply(Response<HomeResult> response)  {

                        printObj(response,"打开首页");

                        Observable<Response<IncomeResult>> income = request.income(user.getCoohuaId(),user.getBaseKey());
                        return income;
                    }
                })



                .subscribe(new Observer<Response<IncomeResult>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<IncomeResult> response) {
                        printObj(response,"获取收入金额");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError:"+e.getCause().getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("onComplete");
                    }
                });


    }


    private <T> void printObj(Response<T> result, String tag) {
        String url = result.raw().request().toString();
        LogUtils.i(TAG, tag + ":" + url);
        T body = result.body();
        String string = body.toString();
        if (string.length()>200){
            string = string.substring(0, 199);
        }

        LogUtils.d(TAG, "结果:" + string);

    }

    private  void printResponse(Response<ResponseBody> result, String tag) {
        try {
            String url = result.raw().request().toString();
            LogUtils.i(TAG, tag + ":" + url);
            String body = result.body().string();
            LogUtils.d(TAG, "结果:" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void start(){

    }

    public void stop(){

    }

}
