package cenco.xz.fangliang.wisdom.weed.thumber;


import com.cenco.lib.common.DateUtil;
import com.cenco.lib.common.IOUtils;
import com.cenco.lib.common.json.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.weed.thumber.bean.Account;
import cenco.xz.fangliang.wisdom.weed.thumber.sign.YoutuSign;
import okhttp3.Headers;
import retrofit2.Response;


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

    public static List<Account> getOrinalUsers(){
        List<Account> list = new ArrayList<Account>();
        list.add(new Account("13468006640","lllqycyl0909","李琦","支付宝","",true));
        list.add(new Account("13047488791","13047488791","邱士菊","支付宝","",true));
        list.add(new Account("13221809346","qwertyuiop","辛忠","支付宝","468531247@qq.com",true));
        list.add(new Account("17024472824","17024472824","梁翠莲(李琦妈妈)","支付宝","17024472824@126.com",false));

        list.add(new Account("13237522180","13237522180li","李琦","建行","13237522180@126.com",false));
        list.add(new Account("15588591960","xin03531883","辛忠","招商","",false));
        list.add(new Account("15665851629","binbin1629","霍彬彬","工商","",false));
        list.add(new Account("15764125171","asdfghjkl","霍彬彬","支付宝","15764125171@qq.com",false));
        list.add(new Account("18579013870","18579013870xin","辛子财","支付宝","18579013870@126.com",false));
        list.add(new Account("18679326052","18679326052","霍宁宁","支付宝","18679326052@126.com",false));
        list.add(new Account("17024474404","17024474404","霍合忠","支付宝","17024474404@126.com",false));

        list.add(new Account("18507045703","18507045703","谈书云","支付宝","18507045703@126.com",false));
        list.add(new Account("17024474405","17024474405a","王洪伟","支付宝","17024474405@126.com",false));
        list.add(new Account("17024474105","17024474105zh","张顺","支付宝","17024474105@126.com",false));
        list.add(new Account("17024474106","zhangshun123","张顺","农行","17024474106@126.com",false));
        list.add(new Account("17024474083","17024474083","张子明","支付宝","17024474083@126.com",false));
        list.add(new Account("17024474084","17024474084","张子明","工行","17024474084@126.com",false));
//        list.add(new Account("17024474685","wanghongwei","王洪伟","银行未定","17024474685@126.com",false));
        return list;
    }

    public static List<Account>  getUsers(){

        File file = new File(getPath());
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        String s = IOUtils.readFile2String(file);
        if (s!=null){
            List<Account> list = GsonUtil.fromJson(s,new TypeToken<List<Account>>(){}.getType());
            if (list!=null){
                return list;
            }
        }

        return Util.getOrinalUsers();
    }

    public static String getPath(){
        String path = C.file.thumber_sign+ DateUtil.getDateString(DateUtil.FORMAT_YMD)+".data";
        return path;
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
