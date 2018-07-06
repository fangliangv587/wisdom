package cenco.xz.fangliang.wisdom.weed.txapp;


import java.util.ArrayList;
import java.util.List;

import cenco.xz.fangliang.wisdom.core.C;

/**
 * Created by Administrator on 2018/7/2.
 */

public class Utils {

    public static String getAliDataPath(AliPayAccount account){
        return C.file.txapp_aliaccount+account.getIdentify()+".txt";
    }

    public static List<AliPayAccount> getAlipayAccount() {
        List<AliPayAccount> list = new ArrayList<AliPayAccount>();


        list.add(new AliPayAccount("13047488791", "霍彬彬", "1", "", "a72af041a8b6be33"));
        list.add(new AliPayAccount("15665851629", "霍彬彬",  "2","", "6d89828bd6001ddb"));
        list.add(new AliPayAccount("15588591960", "辛忠",  "1","", "a25b24b851b43890"));
        list.add(new AliPayAccount("13153870185", "辛子财",  "1","", "4782124a207df377"));
        list.add(new AliPayAccount("17864872607", "邱士菊",  "1","", "7f692ba2e3ae7aba"));


        list.add(new AliPayAccount("18678380687", "霍宁宁",  "1","", "a8b66bb0d0ef4227"));

        list.add(new AliPayAccount("13082761640", "霍合忠",  "1","", "83be66aa093da9a3"));
        list.add(new AliPayAccount("13181384566", "谈书云",  "1","", "d03fd655bd01b3d8"));

        list.add(new AliPayAccount("13468006640", "李琦", "1", "", "68f691e12a5a6827"));
        list.add(new AliPayAccount("13655388344", "李琦",  "2","", "e8053a1ed93f3b07"));
        list.add(new AliPayAccount("13655381031", "张子明", "1", "", "3bab4473e93d0962"));
        list.add(new AliPayAccount("15665788385", "王洪伟", "1", "", "a9db7d35d575761e"));
        list.add(new AliPayAccount("15908924431", "张顺", "1", "", "bb294f67360579bd"));
        list.add(new AliPayAccount("13953835879", "梁翠莲", "1", "", "8296711a96ab253a"));

//        list.add(new AliPayAccount("", "", "1", "", "319f0a0da7e15522"));
//        list.add(new AliPayAccount("", "", "1", "", "b6e35d77348ffa46"));


        return list;
    }
}
