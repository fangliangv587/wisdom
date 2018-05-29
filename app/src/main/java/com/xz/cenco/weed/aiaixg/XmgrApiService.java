package com.xz.cenco.weed.aiaixg;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/25.
 */
public interface XmgrApiService {


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
     * @return
     */
    @Headers({
            "X-Requested-With: XMLHttpRequest"
    })
    @FormUrlEncoded
    @POST("/M/User/login")
    Observable<Response<Bean>> login(@Field("user_name") String username, @Field("user_pass") String password, @Field("dosubmit") String dosubmit);

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
