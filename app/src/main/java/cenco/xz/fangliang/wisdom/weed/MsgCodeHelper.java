package cenco.xz.fangliang.wisdom.weed;

import com.cenco.lib.common.http.HttpUtil;
import com.cenco.lib.common.log.LogUtils;

/**
 * Created by Administrator on 2018/7/12.
 * 仅在子线程调用
 */

public class MsgCodeHelper {

    private static final String TAG = MsgCodeHelper.class.getSimpleName();

    public static final String name="fangliangv587";
    public static final String pass ="xin03531883";

    private static MsgCodeHelper instant;

    private String token;

    private static String getDevelopCodeUrl(){
        String str = "http://capi.yika66.com/Code.aspx?uName="+name;
        return str;
    }

    private static String getLoginUrl(String dcode){
        String str="http://kapi.yika66.com:20153/User/login?uName="+name+"&pWord="+pass+"&Developer="+dcode;
        return str;
    }
    private static String getPhoneUrl(String token){
        String projectId = "56609";
        String str="http://kapi.yika66.com:20153/User/getPhone?ItemId="+projectId+"&token="+token;
        return str;
    }
    private static String getMessageUrl(String token,String phone){

//        String str="http://kapi.yika66.com:20153/User/getMessage?Phone="+phone+"&token="+token+"&code=utf8";
        String projectId = "56609";
        String str = "http://kapi.yika66.com:20153/User/getMessage?token="+token+"&ItemId="+projectId+"&Phone="+phone+"&code=utf8";
        return str;
    }

    private MsgCodeHelper() {

    }

    public static MsgCodeHelper getInstant(){
        if (instant==null){
            instant = new MsgCodeHelper();
        }
        return instant;
    }

    private void init(){
        String dcode = HttpUtil.getSync(getDevelopCodeUrl());
        LogUtils.d(TAG,dcode);
        String sync = HttpUtil.getSync(getLoginUrl(dcode));
        LogUtils.i(TAG,sync);
        //登录token&账户余额&最大登录客户端个数&最多获取号码数&单个客户端最多获取号码数&折扣
        String[] split = sync.split("&");
        token = split[0];
        LogUtils.d(TAG,"token="+token);
    }

    public String getPhone(){
        String sync = HttpUtil.getSync(getPhoneUrl(token));
        if (!sync.contains(";")){
            return null;
        }
        String[] split = sync.split(";");
        if (split!=null && split.length!=0){
            return split[0];
        }

        return null;
    }

    public String getPhoneMessage(String phone){
        String sync = HttpUtil.getSync(getMessageUrl(token,phone));
        return sync;
    }

    public void startSingleTask(MsgCodeListener listener){

        init();

        String phone = getPhone();
        if (phone==null){
            listener.onMsgCodePhoneFail("未获取到手机号");
            return;
        }

        listener.onMsgCodePhoneSuccess(phone);

        int max = 20;
        int i=0;
        while (i<max){

            LogUtils.d(TAG,"第"+i+"次获取验证码");

            String message = getPhoneMessage(phone);

            long sleepTime = 10 * 1000;

            if (message != null && !message.equalsIgnoreCase("Null") && i != 0){
                //MSG&56609&17050670446&【赚钱啦】验证码是1062，仅用于手机注册，请勿告知他人。十分钟内有效。[End]
                //RES&56609&17050670446[End]USER&1.25&10&30&10&1&0[End]
                if (!message.contains("[End]")){
                    listener.onMsgCodeFail(message);
                    return;
                }
                String[] split = message.split("\\[End\\]");
                for (String str:split){
                    String[] data = str.split("&");
                    if(data[0].equals("MSG")){
                        String code = data[3].substring(9, 13);
                        listener.onMsgCodeSuccess(code);
                        sleepTime = 1 * 1000;
                    }
                    if (data[0].equals("USER")){
                        String info = "余额:" + data[1];
                        listener.onMsgCodeUserInfo(info);
                        return;
                    }
                }

            }


            i++;

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public interface MsgCodeListener{
        void onMsgCodePhoneFail(String reason);
        void onMsgCodePhoneSuccess(String phone);
        void onMsgCodeFail(String reason);
        void onMsgCodeSuccess(String code);
        void onMsgCodeUserInfo(String info);
    }
}
