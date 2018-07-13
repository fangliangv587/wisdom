package cenco.xz.fangliang.wisdom.weed.txapp2;

import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.http.SimpleCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by Administrator on 2018/7/10.
 */

public class ApiService {

    public static final String url_code ="http://47.104.141.79:8080/tkxt/android/system/smsCode4M.action";
    public static final String url_register ="http://47.104.141.79:8080/tkxt/android/system/save4M.action";
    public static final String url_login ="http://47.104.141.79:8080/tkxt/android/system/login4M.action";
    public static final String url_identify ="http://47.104.141.79:8080/tkxt/android/system/user/updateUser.action";
    public static final String url_withdraw ="http://47.104.141.79:8080/tkxt/android/part/toaPartWithdraw/withdraw4M.action";
    public static final String url_withdraw_list ="http://47.104.141.79:8080/tkxt/android/part/toaPartWithdraw/getWithdrawList.action";
    public static final String url_user_info ="http://47.104.141.79:8080/tkxt/android/system/user/getUserAllInfoById4M.action";
    public static final String url_income ="http://47.104.141.79:8080/tkxt/android/part/toaPartIncome/save.action";
    public static final String url_reward ="http://47.104.141.79:8080/tkxt/android/part/toaPartReward/save.action";

    public static void getCode(String phone, SimpleCallback simpleCallback) {
        HttpParams params = new HttpParams();
        params.put("sTel",Utils.encode(phone));
        HttpUtil.post(url_code,params,simpleCallback);
    }


    public static void register(String phone, String pass, String mac, String codeResult, String code,String visitCode, SimpleCallback simpleCallback) {
        HttpParams params = new HttpParams();
        params.put("loginName",Utils.encode(phone));
        params.put("password",Utils.encode(pass));
        params.put("homeinvite",Utils.encode(visitCode));
        params.put("mac",mac);
        params.put("telCode",codeResult);
        params.put("code",Utils.encode(code));
        HttpUtil.post(url_register,params,simpleCallback);
    }

    public static void login(String phone, String pass,SimpleCallback simpleCallback) {
        HttpParams params = new HttpParams();
        params.put("loginName",phone);
        params.put("password",pass);
        HttpUtil.post(url_login,params,simpleCallback);
    }

    public static String syncLogin(String phone, String pass) {
        HttpParams params = new HttpParams();
        params.put("loginName",phone);
        params.put("password",pass);
        String s = HttpUtil.postSync(url_login, params);
        return s;
    }

    public static void identify(int id, String token, String officePhone, String realname, String sexTag, SimpleCallback simpleCallback) {
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(id+""));
        params.put("sign",token);
        params.put("officeTel",Utils.encode(officePhone));
        params.put("displayName",Utils.encode(realname));
        params.put("sex",Utils.encode(sexTag));
        HttpUtil.post(url_identify,params,simpleCallback);
    }

    public static void withdraw(int userId,String token,String withdrawNumber,String extStr4, SimpleCallback simpleCallback){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userId+""));
        params.put("token",token);
        params.put("withdrawNumber",Utils.encode(withdrawNumber));
        params.put("extStr4",Utils.encode(extStr4));
        HttpUtil.post(url_withdraw,params,simpleCallback);
    }

    public static String syncWithdraw(int userId,String token,String withdrawNumber,String extStr4 ){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userId+""));
        params.put("token",token);
        params.put("withdrawNumber",Utils.encode(withdrawNumber));
        params.put("extStr4",Utils.encode(extStr4));
        String s = HttpUtil.postSync(url_withdraw, params);
        return s;
    }

    public static void withdrawList(int userId,String token,SimpleCallback simpleCallback){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userId+""));
        params.put("token",token);
        HttpUtil.post(url_withdraw_list,params,simpleCallback);
    }

    public static void getUserInfo(int userId,String token,SimpleCallback simpleCallback){
        HttpParams params = new HttpParams();
        params.put("id",Utils.encode(userId+""));
        params.put("token",token);
        HttpUtil.post(url_user_info,params,simpleCallback);
    }

    public static void income(int userid,String token,float income,SimpleCallback simpleCallback){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userid+""));
        params.put("token",token);
        params.put("incomeNumber",Utils.encode(income+""));
        HttpUtil.post(url_income,params,simpleCallback);
    }
    public static String syncIncome(int userid,String token,String income){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userid+""));
        params.put("token",token);
        params.put("incomeNumber",Utils.encode(income+""));
        String s = HttpUtil.postSync(url_income, params);
        return s;
    }
    public static String syncReward(int userid,String token,String income){
        HttpParams params = new HttpParams();
        params.put("userId",Utils.encode(userid+""));
        params.put("token",token);
        params.put("rewardNumber",Utils.encode(income+""));
        params.put("remark",Utils.encode("挂机奖励"));
        String s = HttpUtil.postSync(url_reward, params);
        return s;
    }
}
