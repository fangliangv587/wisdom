package cenco.xz.fangliang.wisdom.weed.txapp2;


import android.util.Base64;

import com.cenco.lib.common.DateUtil;

import java.util.ArrayList;
import java.util.List;

import cenco.xz.fangliang.wisdom.core.C;
import cenco.xz.fangliang.wisdom.weed.txapp2.bean.Account;

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

        list.add(new Account("13468006640", "李琦",  "liqi123456","68f691e12a5a6827","男","13468006640", "李琦",  true,"1998"));
        list.add(new Account("15588591960", "辛忠",  "xin03531883","a25b24b851b43890","男","15588591960", "辛忠",  true,"1988"));
        list.add(new Account("13047488791", "霍彬彬", "hbb8791", "a72af041a8b6be33","男","13153870185", "辛子财",true,"2241"));
        list.add(new Account("15764125171", "霍彬彬", "rich1990", "4782124a207df377","女","17864872607", "邱士菊",true,"2594"));
        list.add(new Account("15665851629", "霍彬彬",  "shanshan0330","6d89828bd6001ddb","女","15665851629", "霍彬彬",  true,"2120"));
        list.add(new Account("15712913162", "胡肖肖", "zzm13655381031", "3bab4473e93d0962","女","18678380687", "霍宁宁",true,"2603"));
        list.add(new Account("13221809346", "辛忠", "flower123", "7f692ba2e3ae7aba","女","13181384566", "谈书云",true,"2597"));
        list.add(new Account("17186785314", "王发", "wf19zxc", "83be66aa093da9a3","男","13082761640", "霍合忠",true,"2608"));
//        list.add(new Account("17615835614", "王发", "19860326nie", "e8053a1ed93f3b07","女","13047488791", "霍彬彬",true));


        //
//
//        list.add(new Account("13655388344", "李琦", "liqinum1", "e8053a1ed93f3b07","男",false));
//        list.add(new Account("13655381031", "张子明", "tianxiaming", "3bab4479e33d0962","男",false));
//        list.add(new Account("15665788385", "王洪伟",  "wanghongwei","a9db7d35d575761e","男",false));
//        list.add(new Account("15908924431", "张顺", "qwertyuiop", "bb294f67360579bd","男",false));
//        list.add(new Account("13953835879", "梁翠莲","lianhua0527",  "8296711a96ab253a","女",false));




//        list.add(new Account("13082761640", "霍合忠", "13082761640", "83be66aa093da9a3","男",false));
//        list.add(new Account("13181384566", "谈书云",  "tan0531","d03fd655bd01b3d8","女",false));
//        list.add(new Account("18678380687", "霍宁宁", "ningning", "a8b66bb0d0ef4227","女",false));
//        list.add(new Account("17864872607", "邱士菊", "flower123", "7f692ba2e3ae7aba","女",false));
//        list.add(new Account("13153870185", "辛子财",  "rich1990","4782124a207df377","男",false));
        return list;
    }

    public static String decode(String pass) {

        byte[] decode = Base64.decode(pass, 0);
        String ds = new String(decode);

        return ds;
    }

    public static String encode(String text){
        byte[] encode = Base64.encode(text.getBytes(), 0);
        String ds = new String(encode);
        ds = ds.replace("\t","");
        ds = ds.replace("\n","");
        return ds;
    }

    public static String getFilePath(Account account){
        String date = DateUtil.getDateString(DateUtil.FORMAT_YMD);
        String path = C.file.txapp_action+date+"/"+account.getIndentify()+".txt";
        return path;
    }

    public static String getVirtualRegisterFilePath(Account account){
        String date = DateUtil.getDateString(DateUtil.FORMAT_YMD);
        String path = C.file.txapp_action+"virtual/"+account.getIndentify()+".txt";
        return path;
    }
}
