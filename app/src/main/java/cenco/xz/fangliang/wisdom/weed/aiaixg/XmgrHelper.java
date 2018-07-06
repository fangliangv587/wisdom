//package cenco.xz.fangliang.wisdom.weed.aiaixg;
//
//import android.content.Context;
//
//import com.cenco.lib.common.DateUtil;
//import com.cenco.lib.common.TimerHelper;
//import com.cenco.lib.common.log.LogUtils;
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import com.xz.cenco.weed.aiaixg.*;
//import com.xz.cenco.weed.aiaixg.Bean;
//import com.xz.cenco.weed.aiaixg.User;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by Administrator on 2018/5/28.
// */
//public class XmgrHelper implements TimerHelper.TimerListener {
//
//    private List<com.xz.cenco.weed.aiaixg.User> users;
//    private String cookie = null;
//    private String TAG="aiaixg2";
//    private AiaixgApiService request;
//    private final int  total = 30*60;//30分钟
//
//    private boolean loop = false;
//    private Context context;
//
//    private Date upDate;
//    private Date downDate;
//
//    public XmgrHelper(Context context) {
//
//        this.context =context;
//
//        users = getUser();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://jnqgl.com/") // 设置 网络请求 Url
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
//                .build();
//
//        request = retrofit.create(AiaixgApiService.class);
//
//        upDate = DateUtil.createDate(2018,1,1,23,59,0);
//        downDate = DateUtil.createDate(2018,1,1,6,0,0);
//    }
//
//    public  void start(){
//
//        if (!com.xz.cenco.wisdom.util.Util.isNetworkAvailable(context)){
//            loop = true;
//            LogUtils.w(TAG, "无网");
//            return;
//        }
//        loop = false;
//        String dateString = DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD);
//        com.xz.cenco.weed.aiaixg.User account = getUser(dateString);
//        if (account == null) {
//            LogUtils.w(TAG, dateString + "-任务完成");
//            loop = true;
//            return;
//        }
//        action(account);
//
//    }
//
//
//
//    public com.xz.cenco.weed.aiaixg.User getUser(String date) {
//
//        for (com.xz.cenco.weed.aiaixg.User account : users) {
//            Map<String, Boolean> result = account.getResult();
//            if (!result.containsKey(date) || !result.get(date)) {
//                account.putResult(date, false);
//                return account;
//            }
//        }
//        return null;
//    }
//
//    private  void action(final com.xz.cenco.weed.aiaixg.User user) {
//
//        Observable<Response<com.xz.cenco.weed.aiaixg.Bean>> login = request.login(user.getName(),user.getPass(),user.getDosubmit(),user.getCookietime());
//
//        login   .subscribeOn(Schedulers.io())
//                .doOnNext(new Consumer<Response<com.xz.cenco.weed.aiaixg.Bean>>() {
//                    public void accept(Response<com.xz.cenco.weed.aiaixg.Bean> response) {
//                        printResponse(response);
//                        String key = "Set-Cookie";
//                        cookie= com.xz.cenco.weed.aiaixg.Util.getHeaderValue(response, key);
//                        LogUtils.d(cookie);
//                    }
//                })
//
//                .flatMap(new Function<Response<com.xz.cenco.weed.aiaixg.Bean>, ObservableSource<Response<com.xz.cenco.weed.aiaixg.Bean>>>() { // 作变换，即作嵌套网络请求
//                    public ObservableSource<Response<com.xz.cenco.weed.aiaixg.Bean>> apply(Response<com.xz.cenco.weed.aiaixg.Bean> response)  {
//                        Observable<Response<com.xz.cenco.weed.aiaixg.Bean>> sign = request.sign(cookie);
//                        return sign;
//                    }
//                })
//                .doOnNext(new Consumer<Response<com.xz.cenco.weed.aiaixg.Bean>>() {
//                    public void accept(Response<com.xz.cenco.weed.aiaixg.Bean> response) throws Exception {
//                        printResponse(response);
//                    }
//                })
//                .flatMap(new Function<Response<com.xz.cenco.weed.aiaixg.Bean>, ObservableSource<Response<ResponseBody>>>() { // 作变换，即作嵌套网络请求
//                    public ObservableSource<Response<ResponseBody>> apply(Response<Bean> response)  {
//                        Observable<Response<ResponseBody>> personInfo = request.member(cookie);
//                        return personInfo;
//                    }
//                })
//
//                .subscribe(new Observer<Response<ResponseBody>>() {
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    public void onNext(Response<ResponseBody> response) {
//                        String s = getResponse(response);
//                        String account = getAccount(s);
//                        LogUtils.w(TAG,user.getName()+"===>"+account);
//                    }
//
//                    public void onError(Throwable throwable) {
//                        LogUtils.e(TAG,"onError:"+throwable.getMessage()+",等待10分钟");
//
//                        try {
//                            //经常性出现服务器连接不上的情况
//                            Thread.sleep(10*60*1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        start();
//
//                    }
//
//                    public void onComplete() {
////                        LogUtils.d("onComplete");
//                        user.putResult(DateUtil.getDateString(new Date(), DateUtil.FORMAT_YMD), true);
//                        start();
//                    }
//                });
//
//    }
//
//    public List<com.xz.cenco.weed.aiaixg.User> getUser(){
//        List<com.xz.cenco.weed.aiaixg.User> list = new ArrayList<>();
//        com.xz.cenco.weed.aiaixg.User user0 = new com.xz.cenco.weed.aiaixg.User("15588591960", "xin03531883");
//        com.xz.cenco.weed.aiaixg.User user1 = new com.xz.cenco.weed.aiaixg.User("15665851629", "qwertyuiop");
//        com.xz.cenco.weed.aiaixg.User user2 = new com.xz.cenco.weed.aiaixg.User("13047488791", "asdfghjkl");
//        com.xz.cenco.weed.aiaixg.User user3 = new com.xz.cenco.weed.aiaixg.User("15764125171", "zxcvbnm");
//        com.xz.cenco.weed.aiaixg.User user4 = new User("13468006640", "lllqycyl0909");
//        list.add(user0);
//        list.add(user1);
//        list.add(user2);
//        list.add(user3);
//        list.add(user4);
//        return list;
//    }
//
//    private static  String getResponse(Response<ResponseBody> result) {
//        try {
//            String url = result.raw().request().toString();
//            LogUtils.d("网络请求成功:" + url);
//            String body = result.body().string();
//
//            String printInfo=body;
//            if (printInfo.length()>200){
//                printInfo = printInfo.substring(0,200)+"...";
//            }
//            LogUtils.i("结果:" + printInfo);
//            return body;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static <T> void printResponse(Response<T> result) {
//        String url = result.raw().request().toString();
//        LogUtils.d("网络请求成功:" + url);
//        T body = result.body();
//        LogUtils.i("结果:" + body.toString());
//
//    }
//
//    public static String getAccount(String str){
//        String regex = "可用余额：[0-9]+\\.00元";
//        Pattern pattern = Pattern.compile(regex);// 匹配的模式
//        Matcher m = pattern.matcher(str);
//        while (m.find()) {
//            String result  = m.group(0);
//            return result;
//        }
//        return null;
//    }
//
//    @Override
//    public void onTimerRunning(int current, int n, boolean b) {
//        if (loop && current % total == 0 && isValidTime() ){
////            LogUtils.w(TAG,"新一轮的查询签到",true);
//            start();
//        }
//    }
//
//    private boolean isValidTime(){
//        boolean valid = DateUtil.isInPeriodDate(new Date(), downDate, upDate, DateUtil.FORMAT_HMS);
//        LogUtils.w(TAG,"时间段检查："+valid);
//        return valid;
//    }
//
//
//}
