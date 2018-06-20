package com.xz.cenco.weed.thumber;

import com.xz.cenco.weed.thumber.bean.Account;
import com.xz.cenco.weed.thumber.sign.YoutuSign;

import okhttp3.Headers;
import retrofit2.Response;

import java.util.*;


/**
 * Created by Administrator on 2018/4/25.
 */
public class Util {

    public static class Constant {
        public static final String clientId = "sTtgxGj54Hzcz7CUefn3Ib5v";
        public static final String clientSecret = "bNQZqS3tfe6S3IMgeneU5Y2aKdLV2t5q";
        public static final String grantType = "client_credentials";

        public static final String APP_ID = "10127680";
        public static final String SECRET_ID = "AKIDupqyAUD854wZ6bMY8xjYRgzJVG84Df4e";
        public static final String SECRET_KEY = "xxx2Ld9AGRxhdEx18GTm7WVRaOFAZ6oh";
        public static final String USER_ID = "xxx"; //qq号
        // 30 days
        private static int EXPIRED_SECONDS = 2592000;

    }

    public static List<Account> getUsers(){
        List<Account> list = new ArrayList<Account>();
        list.add(new Account("15588591960","xin03531883","xz招商"));
        list.add(new Account("13468006640","lllqycyl0909","lq"));
        list.add(new Account("15665851629","binbin1629","bb工商"));
        list.add(new Account("13047488791","13047488791","mom支付宝"));
        list.add(new Account("13221809346","qwertyuiop","xz支付宝"));
        return list;
    }

    public static String getYTAuthorization (){
        StringBuffer mySign = new StringBuffer("");
        YoutuSign.appSign(Constant.APP_ID, Constant.SECRET_ID, Constant.SECRET_KEY,
                System.currentTimeMillis() / 1000 + Constant.EXPIRED_SECONDS,
                Constant.USER_ID, mySign);
        return mySign.toString();
    }


    public static String getFormatCode(String string){
        StringBuffer sb= new StringBuffer();
        for (int i=0;i<string.length();i++){
            char c = string.charAt(i);
            //数字
            if (c>=48 && c<=57){
                sb.append(c);
            }

            //大写字母
            if (c>=65 && c<=90){
                sb.append(c);
            }

            //小写字母
            if (c>=97 && c<=122){
                sb.append(c);
            }
        }

        String format = sb.toString();
        return format;
    }

    public static String getHeaderValue(Response response,String key) {
        if (response == null || key == null) {
            return "";
        }
        Headers headers = response.headers();
        if (headers == null && headers.size() == 0) {
            return "";
        }


        Set<String> names = headers.names();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            List<String> values = headers.values(next);
            if (values != null && values.size() != 0) {
                String value = null;
                if (values.size() == 1) {
                    value = values.get(0);
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < values.size(); i++) {
                        String s = values.get(i);
                        sb.append(s);
                        sb.append(";");
                    }
                    value = sb.toString();

                    String[] split = value.split(";");
                    if (split != null && split.length != 0) {
                        for (String part : split) {
                            if (part.contains("=")) {
                                String[] split1 = part.split("=");
                                if (split1 != null && split1.length == 2) {
                                    String k1 = split1[0];
                                    String v1 = split1[1];

                                        if (k1.equals(key)) {
                                            return v1;
                                        }
                                }
                            }
                        }
                    }
                }
//                System.out.println(next+":"+value);
                    if (next.equals(key)) {
                        return value;
                    }
            }
        }

        return "";
    }
}
