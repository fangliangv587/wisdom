package cenco.xz.fangliang.wisdom.weed.coohua;

import android.content.Context;
import android.util.Log;

import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.SystemUtil;
import com.cenco.lib.common.TimerHelper;
import com.cenco.lib.common.log.LogUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cenco.xz.fangliang.wisdom.weed.coohua.bean.HomeResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.IncomeResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.NewsReadResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.OpenBoxResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.SignResult;
import cenco.xz.fangliang.wisdom.weed.coohua.bean.User;
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
 * 4.首页新闻阅读，最多30次 ----> v
 * 5.收徒，最多5.5（徒弟3.5，徒孙2）
 */

public class CoohuaHelper implements TimerHelper.TimerListener {

    private CohuaApiService request;
    private final int total = 30 * 60;//30分钟
    private boolean loop = false;
    private List<User> users;

    private String TAG = "coohua";
    private Context context;
    private Date upDate;
    private Date downDate;
    private int count;

    public CoohuaHelper(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.coohua.com:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        request = retrofit.create(CohuaApiService.class);
        users = Util.getUsers();
        upDate = DateUtil.createDate(2018,1,1,23,59,0);
        downDate = DateUtil.createDate(2018,1,1,6,0,0);

    }

    public void start() {



        User user = getUser();
        loop = false;
        if (user == null) {
            count++;
            init();
            String dateString = DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMDHMS);
            LogUtils.w(TAG, dateString +"       第" +count+"个任务完成\r\n\r\n\r\n\r\n");
            loop = true;
            return;
        }

        beginTask(user);


    }

    private User getUser() {
        for (User user : users) {
            if (!user.isFinish()) {
                return user;
            }
        }

        return null;
    }

    private void init() {
        for (User user : users) {
            user.setFinish(false);
        }
    }

    public void stop() {

    }

    private void beginTask(final User user) {

        LogUtils.w(TAG,"开始第"+user.getIndex()+"个用户操作");

        Observable<Response<LoginResult>> login = request.login(user.getAndroidId(), user.getAccountNum(), user.getPassword(), user.getBlueMac(), user.getCpuModel(), user.getImei(), user.getWifiMac(), user.getBlackBox(), user.getVersion(), user.getStorageSize(), user.getMarkId(), user.getScreenSize(), user.getModel());
        login
                .subscribeOn(Schedulers.io())

                .flatMap(new Function<Response<LoginResult>, ObservableSource<Response<SignResult>>>() {
                    public ObservableSource<Response<SignResult>> apply(Response<LoginResult> response) {

                        printObj(response, "登录");

                        LoginResult.ResultBean result = response.body().getResult();
                        if (result == null || result.getTicket() == null) {
                            return Observable.error(new Throwable("登录失败"));
                        }

                        int coohuaId = result.getCoohuaId();
                        String ticket = result.getTicket();
                        String baseKey = Util.getBaseKey(coohuaId, ticket);
                        user.setCoohuaId(coohuaId);
                        user.setBaseKey(baseKey);

                        LogUtils.v(TAG, "baseKey=" + baseKey);
                        LogUtils.v(TAG, "coohuaId=" + coohuaId);

                        Observable<Response<SignResult>> signIn = request.signIn(user.getCoohuaId(), user.getBaseKey(), Util.Constant.version);
                        return signIn;
                    }
                })

                .flatMap(new Function<Response<SignResult>, ObservableSource<Response<OpenBoxResult>>>() {
                    public ObservableSource<Response<OpenBoxResult>> apply(Response<SignResult> response) {

                        printObj(response, "签到");

                        Observable<Response<OpenBoxResult>> openBox = request.openBox(user.getCoohuaId(), user.getBaseKey(), Util.Constant.version);
                        return openBox;
                    }
                })


                .flatMap(new Function<Response<OpenBoxResult>, ObservableSource<Response<HomeResult>>>() {
                    public ObservableSource<Response<HomeResult>> apply(Response<OpenBoxResult> response) {

                        printObj(response, "开宝箱");

                        Observable<Response<HomeResult>> home = request.home(user.getCoohuaId(), user.getBaseKey());
                        return home;
                    }
                })


                .flatMap(new Function<Response<HomeResult>, ObservableSource<Response<NewsReadResult>>>() {
                    public ObservableSource<Response<NewsReadResult>> apply(Response<HomeResult> response) {

                        printObj(response, "打开首页");

                        Observable<Response<NewsReadResult>> income = request.readNews(user.getCoohuaId(), user.getBaseKey());
                        return income;
                    }
                })

                .flatMap(new Function<Response<NewsReadResult>, ObservableSource<Response<IncomeResult>>>() {
                    public ObservableSource<Response<IncomeResult>> apply(Response<NewsReadResult> response) {

                        printObj(response, "打开新闻阅读，增加金币");

                        Observable<Response<IncomeResult>> income = request.income(user.getCoohuaId(), user.getBaseKey());
                        return income;
                    }
                })


                .subscribe(new Observer<Response<IncomeResult>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<IncomeResult> response) {
                        IncomeResult.ResultBean result = response.body().getResult();
                        LogUtils.w(TAG, user.getAccountInfo()+"-->现金:" + result.getCurrentCredit() + "(1:100),金币:" + result.getCurrentGoldCoin() + "(1:2000)");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e==null || e.getCause() ==null){
                            LogUtils.e(TAG, "发生异常");
                        }else {
                            LogUtils.e(TAG, "!!!!onError:" + e.getCause().getMessage() + "!!!!");
                        }

                        try {
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        start();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "-----onComplete----");
                        user.setFinish(true);
                        start();
                    }
                });


    }


    private <T> void printObj(Response<T> result, String tag) {
        String url = result.raw().request().toString();
        LogUtils.i(TAG, tag + ":" + url);
        T body = result.body();
        if (body == null) {
            return;
        }
        String string = body.toString();
        if (string.length() > 200) {
            string = string.substring(0, 199);
        }

        LogUtils.d(TAG, "结果:" + string);

    }

    private void printResponse(Response<ResponseBody> result, String tag) {
        try {
            String url = result.raw().request().toString();
            LogUtils.i(TAG, tag + ":" + url);
            String body = result.body().string();
            LogUtils.d(TAG, "结果:" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onTimerRunning(int i, int i1, boolean b) {

        //条件1：计数器到点
        //条件2：可以进行循环（单次任务是否完成）
        //条件3：是否在有效时间内
        if (i % total == 0  && loop && isValidTime() ){

            start();

        }

    }

    private boolean isValidTime(){
        boolean valid = DateUtil.isInPeriodDate(new Date(), downDate, upDate, DateUtil.FORMAT_HMS);
        LogUtils.w(TAG,"时间段检查："+valid);
        return valid;
    }
}
