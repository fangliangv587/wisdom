package cenco.xz.fangliang.wisdom.weed.thumber;


import cenco.xz.fangliang.wisdom.weed.thumber.bean.AccessToken;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.BDOrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.LoginResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.OrcResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignResult;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.SignUserResult;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;

/**
 * Created by Administrator on 2018/4/25.
 */
public interface ThumberApiService {

    //初始化
    @Headers({
            "Connection: keep-alive",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            "Upgrade-Insecure-Requests: 1",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9"
    })
    @GET("/")
    Observable<Response<ResponseBody>> init();


    //登录
    @Headers({
            "Connection: keep-alive",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9"
    })
    @FormUrlEncoded
    @POST("/Login")
    Observable<Response<LoginResult>> login(@Header("Cookie") String cookie, @Field("UserName") String UserName, @Field("UserPwd") String UserPwd, @Field("vercode") String vercode);


    //签到
    @Headers({
            "Connection: keep-alive",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "Accept: application/json, text/javascript, */*; q=0.01",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9"
    })
    @POST("/PersonalCenter/SignInMoney")
    Observable<Response<SignResult>> signIn(@Header("Cookie") String cookie);


    //用户签到列表
    @Headers({
            "Connection: keep-alive",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            "X-Requested-With: XMLHttpRequest",
            "Accept: application/json, text/javascript, */*; q=0.01",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9"
    })
    @FormUrlEncoded
    @POST("/PersonalCenter/UserSignIn")
    Observable<Response<SignUserResult>> UserSignIn(@Header("Cookie") String cookie, @Field("value") int value);



    // 获取验证码
    @GET("/CheckCode?flag=3")
    Observable<Response<ResponseBody>> code(@Header("Cookie") String cookie);


    @Headers({
            "Connection: keep-alive",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
            "Upgrade-Insecure-Requests: 1",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Accept-Encoding: gzip, deflate, br",
            "Accept-Language: zh-CN,zh;q=0.9"
    })
    @GET("/PersonalCenter")
    Observable<Response<ResponseBody>> personInfo(@Header("Cookie") String cookie);


    @FormUrlEncoded
    @POST("https://aip.baidubce.com/oauth/2.0/token?")
    Observable<AccessToken> baiduInit(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("grant_type") String grantType);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic")
    Observable<Response<BDOrcResult>> baiduOrc(@Query("access_token") String  token, @Field("image") String image);


    //腾通优图 识别车牌图片文字
    @Headers({
            "Host: api.youtu.qq.com",
            "Content-Type: text/json"
    })
    @POST("https://api.youtu.qq.com/youtu/ocrapi/plateocr")
    Observable<Response<ResponseBody>> cardnumberOcrText(@Header("Authorization") String authorization, @Body RequestBody info);


    //腾通优图 识别图片文字
    @Headers({
            "Host: api.youtu.qq.com",
            "Content-Type: text/json"
    })
    @POST("https://api.youtu.qq.com/youtu/ocrapi/generalocr")
    Observable<Response<OrcResult>> ocrText(@Header("Authorization") String authorization, @Body RequestBody info);


    @FormUrlEncoded
    @POST("/Uc/GetLastNum")
    Observable<Response<ResponseBody>> getLastNum(@Header("Cookie") String cookie, @Field("isinit") boolean isinit);

    @FormUrlEncoded
    @POST("/Uc/SubmitBet")
    Observable<Response<ResponseBody>> SubmitBet(@Header("Cookie") String cookie, @Field("issueNum") String issueNum, @Field("hongtl") String hongtl, @Field("DFW") String DFW, @Field("huitl") String huitl, @Field("myy") String myy);


}
