package com.xz.cenco.weed.aiaixg;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;

/**
 * Created by Administrator on 2018/4/25.
 */
public interface AiaixgApiService {


    /**
     * 信息
     * @param cookie
     * @return
     */
    @GET("/Member/")
    Observable<Response<ResponseBody>> member(@Header("Cookie") String cookie);

    /**
     * 登录
     * @param username
     * @param password
     * @param dosubmit
     * @param cookietime
     * @return
     */
    @Headers({
            "X-Requested-With: XMLHttpRequest"
    })
    @FormUrlEncoded
    @POST("/Member/Common/login")
    Observable<Response<Bean>> login(@Field("username") String username, @Field("password") String password, @Field("dosubmit") String dosubmit, @Field("cookietime") String cookietime);

    /**
     * 签到
     * @return
     */
    @Headers({
            "X-Requested-With: XMLHttpRequest"
    })
    @POST("/Member/Index/dosign")
    Observable<Response<Bean>> sign(@Header("Cookie") String cookie);







}
