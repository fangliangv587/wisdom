package cenco.xz.fangliang.wisdom.weed.txapp2;


import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

import cenco.xz.fangliang.wisdom.core.C;

/**
 * Created by Administrator on 2018/7/2.
 */

public class Utils {

    public static String getAliDataPath(Account account){
//        return C.file.txapp_aliaccount+account.getIdentify()+".txt";
        return null;
    }

    public static List<Account> getAccount() {
        List<Account> list = new ArrayList<Account>();

        list.add(new Account("13655388344", "李琦", "liqinum1", "e8053a1ed93f3b07"));
        list.add(new Account("13047488791", "霍彬彬", "hbb8791", "a72af041a8b6be33"));
        list.add(new Account("15665851629", "霍彬彬",  "shanshan0330","6d89828bd6001ddb"));
        list.add(new Account("15588591960", "辛忠",  "xin03531883","a25b24b851b43890"));
        list.add(new Account("13153870185", "辛子财",  "rich1990","4782124a207df377"));
        list.add(new Account("17864872607", "邱士菊", "flower123", "7f692ba2e3ae7aba"));

        list.add(new Account("18678380687", "霍宁宁", "ningning", "a8b66bb0d0ef4227"));

        list.add(new Account("13082761640", "霍合忠", "13082761640", "83be66aa093da9a3"));
        list.add(new Account("13181384566", "谈书云",  "tan0531","d03fd655bd01b3d8"));

        list.add(new Account("13468006640", "李琦",  "liqi123456","68f691e12a5a6827"));
        list.add(new Account("13655381031", "张子明", "zzm13655381031", "3bab4473e93d0962"));
        list.add(new Account("15665788385", "王洪伟",  "wanghongwei","a9db7d35d575761e"));
        list.add(new Account("15908924431", "张顺", "qwertyuiop", "bb294f67360579bd"));
        list.add(new Account("13953835879", "梁翠莲","lianhua0527",  "8296711a96ab253a"));

        return list;
    }

    public static String decode(String pass) {

        byte[] decode = Base64.decode(pass, 0);
        String ds = new String(decode);


        return ds;
    }
}
