package com.xz.cenco.weed.coohua;



import com.xz.cenco.weed.coohua.bean.IncomeResult;
import com.xz.cenco.weed.coohua.bean.HomeResult;
import com.xz.cenco.weed.coohua.bean.LoginResult;
import com.xz.cenco.weed.coohua.bean.OpenBoxResult;
import com.xz.cenco.weed.coohua.bean.SignResult;
import com.xz.cenco.weed.coohua.bean.TaskResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/25.
 */
public interface CohuaApiService {

    //登录
    @FormUrlEncoded
    @POST("http://api.coohua.com:8888/p/v1/login.do")
    Observable<Response<LoginResult>> login(@Field("androidId") String androidId, @Field("accountNum") String accountNum, @Field("password") String password, @Field("blueMac") String blueMac,
                                            @Field("cpuModel") String cpuModel, @Field("imei") String imei, @Field("wifiMac") String wifiMac, @Field("blackBox") String blackBox,
                                            @Field("version") String version, @Field("storageSize") String storageSize, @Field("markId") String markId, @Field("screenSize") String screenSize,@Field("model") String model);


    //获取任务列表
    @FormUrlEncoded
    @POST("http://api.coohua.com:8888/ac/getAccTaskList.do")
    Observable<Response<TaskResult>> getTaskList(@Field("coohuaId") int coohuaId, @Field("baseKey") String baseKey, @Field("appVersion") String appVersion);


    //签到
    @FormUrlEncoded
    @POST("http://pocket.coohua.com/pocket/signIn/addGoldCoin.do")
    Observable<Response<SignResult>> signIn(@Field("coohuaId") int coohuaId, @Field("baseKey") String baseKey, @Field("appVersion") String appVersion);

    //开启宝箱
    @FormUrlEncoded
    @POST("http://pocket.coohua.com/pocket/treasureBox/open.do")
    Observable<Response<OpenBoxResult>> openBox(@Field("coohuaId") int coohuaId, @Field("baseKey") String baseKey, @Field("appVersion") String appVersion);


    //获取收入
    @POST("http://api.coohua.com:8888/p/getIncome.do")
    Observable<Response<IncomeResult>> income(@Query("coohuaId") int coohuaId, @Query("baseKey") String baseKey);


    //打开首页
    @FormUrlEncoded
    @POST("http://101.200.35.130/pocket/goldCoin/homeAdd.do")
    Observable<Response<HomeResult>> home(@Field("coohuaId") int coohuaId, @Field("baseKey") String baseKey);

    //分享
    @POST("http://pocket.coohua.com/pocket/dailyShare/takeReward.do")
    Observable<Response<IncomeResult>> dailyShare(@Query("coohuaId") int coohuaId, @Query("baseKey") String baseKey);






}
