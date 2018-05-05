package com.xz.cenco.weed.coohua;


import android.util.Base64;

import com.cenco.lib.common.json.GsonUtil;
import com.cenco.lib.common.log.LogUtils;
import com.coohua.android.jni.NativeJni;
import com.xz.cenco.weed.coohua.bean.User;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */

public class Util {


    public static class Constant {

        public static final String blackBox = "O57cll08GzN0PbJ9c2Gjy5vDWIt-s214Cwt3x0nxE3Fm8gtL8fs6TeHzTdtvr-_GYJIxYBdIvm9SypmRzPlifeaC7H67yp5L74B3HzWvkuoiC9II-z6fkAgFIfQ3HeGpJZBKCN5SyYsFARhcc9wtakKk7go12Gofkq0B58v3h_9JgcBlXL2CWNJWoT6jaC0RvJGxq12zNtm3WEX3F8wcyNYzrK9i16KVHmOr8cRjLIvo2cZ7pRTnjDh9L0KBAWue-AVST0hYj4NDpHeiplENcfYmiYdjYnPg5S-XlTzrzjTYgjxpuDdr-HT2gcYWD7HwHWp9qzoQpBZikeft4rcvGw71NT3KcPALu_-SrZY3gfg2QTGlo6Bz874PBlU8wkBR9skg54Y01D91qgw6WxXbdLrpDLMT79PNIz4zhDU3si8=";

        public static final String version = "5.2.3.0";

        public static final String[] userInfo = {
                "screenSize=720X1280&markId=357619179677247&model=2013023&blueMac=68%3ADF%3ADD%3A0A%3A36%3A04&blackBox=O57cll08GzN0PbJ9c2Gjy9BnOKKJeq-xlQJQBXTmvDBm8gtL8fs6TeHzTdtvr-_GYJIxYBdIvm9SypmRzPlifeaC7H67yp5L74B3HzWvkuoiC9II-z6fkAgFIfQ3HeGpJZBKCN5SyYsFARhcc9wtaksEPtxZ2mjLEaqKh3oQu4N037d3LlD7DeMCufv0UpOY_1YAoHw2NUCo0l3OPuuRsHSESnId87JKhk-0u8nWaI_q43UPoy4Buad--P1LHLuge3t1zzuREggp5Nbm352dVWwZ920J-ISgee0eojuMJ4sKRmLahv2owlu0aB9pLlOAHWp9qzoQpBZikeft4rcvG5SgQf08j-58O94TkmwN2LE2QTGlo6Bz874PBlU8wkBR9skg54Y01D91qgw6WxXbdLrpDLMT79PNIz4zhDU3si8%3D&wifiMac=68%3Adf%3Add%3A12%3A9a%3A3e&imei=&androidId=157f63dbc5caa015&storageSize=2112700416&accountNum=15588591960&cpuModel=armeabi-v7a&password=3531883&version=5.2.3.0"
                };

    }


    public static List<User> getUsers() {
        List<User> list = new ArrayList<User>();
        User user1 = new User("157f63dbc5caa015", "15588591960", "3531883", "68:DF:DD:0A:36:04", "armeabi-v7a", "866822033471269", "68:df:dd:12:9a:3e", "O57cll08GzN0PbJ9c2Gjy5vDWIt-s214Cwt3x0nxE3Fm8gtL8fs6TeHzTdtvr-_GYJIxYBdIvm9SypmRzPlifeaC7H67yp5L74B3HzWvkuoiC9II-z6fkAgFIfQ3HeGpJZBKCN5SyYsFARhcc9wtakKk7go12Gofkq0B58v3h_9JgcBlXL2CWNJWoT6jaC0RvJGxq12zNtm3WEX3F8wcyNYzrK9i16KVHmOr8cRjLIvo2cZ7pRTnjDh9L0KBAWue-AVST0hYj4NDpHeiplENcfYmiYdjYnPg5S-XlTzrzjTYgjxpuDdr-HT2gcYWD7HwHWp9qzoQpBZikeft4rcvGw71NT3KcPALu_-SrZY3gfg2QTGlo6Bz874PBlU8wkBR9skg54Y01D91qgw6WxXbdLrpDLMT79PNIz4zhDU3si8=", "5.2.3.0", "2112700416", "357619179677247", "720X1280", "2013023");
        user1.setComment("xz主号测试机");
        User user2 = new User("39ed9fa09451d04a", "13221809346", "123456", "", "armeabi-v7a", "866822033471269", "68:df:dd:12:9a:3e", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACPF6S8pqvqSqihVyzdWdOMVsklarApEIeIkjoM_vdDXVKGecLpr9o4yiVCDIENSQSbelJ_3WDSYyL9wC9z79brawWtFcGihOwVedf_UxfolWte7y905PN7MBOvcEOtrzW2YbCbGrR7fpg49GlXJeBwsOMYvf9qpHp-jhIn3fVYKCdrPkmE2ccpPCXNKusGe3G_mu5QNgPS69IPMVaZEbKAKn7OgGwPvkol9Jx5eaPYE8p8Sn7XzMlFqdKZ0z5g_ZmlMwmoI6CHl36dmx0TNdeeI=", "5.2.3.0", "118392029184", "357615696645247", "1080X1920", "MI6");
        user2.setComment("xz阿里小号主机");
        User user3 = new User("ce2dc942ec9b1933", "15665851629", "123456", "58:44:98:58:7C:BF", "armeabi-v7a", "", "58:44:98:58:7c:c0", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACCtCGmJzlg0Cc1KezYyebu65MP6HfWigQeZO38qRv7-EqLHzaIhYheOqmC4MSK6D03oAQhAGq32RtHpyKeoqms5549IrAHxHlBfpOcfJelCvVljtJYmpJbqeJW3YELwjVqdzewAfLSfCbECCLA-ZDFHsHD1JciZof5H2MKRV_fGKiUbWRNU1X1Jh3oAAnz3tdpVNNPjfh1h7urJ1H0sUeEOYUOUkbymlJEFxVK3VKcm9hWKR5rPWql2jbFPk-0yQtq086arm1El7Mge-HhzyvGg=", "5.2.3.0", "12511289344", "357616696626247", "1080X1920", "RedmiNote2");
        user3.setComment("ss主号 wangfa 测试机");
        User user4 = new User("7970b401dd14cacc", "13047488791", "123456", "", "armeabi-v7a", "865214036219997", "", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACD9GQHvNeiiJVx-CA6YRZAJZlsivkekd0_MLEi4t3kAK4E_-Fhg9Lyb1T72q6z_RenoAQhAGq32RtHpyKeoqms5549IrAHxHlBfpOcfJelCvRre0ssSufTHwObyF0ZDdjlM5yHmMHscQzvHmiEDhtMZ9eyR5TKfVFYKaFXHednI2WLgDa_SAjedAxz7wnxgb9EOvwKEieKlW4ovIhZpA2lldtiqKLu28lxVW9NA2L_9udOLFi_QNAriz83wz8iVj8I_0LTz3kax1gwHnGk71aHM=", "5.2.3.0", "57464045568", "353515524688244", "1080X1794", "FRD-AL10");
        user4.setComment("ss小号 huxiaoxiao 主机");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        return list;
    }

    public static void parseUserInfo(String userInfo){

    }

    /**
     * 获取登录后的加密校验
     *
     * @param userid
     * @param ticket
     * @return
     */
    public static String getBaseKey(int userid, String ticket) {
        byte[] commonKey = NativeJni.getCommonKey();
        LogUtils.d(commonKey.toString());

        String str = Constant.version + "^" + userid + "^" + ticket;

        byte[] adddd = EncodeUtil.adddd(str.getBytes(), commonKey);
        byte[] encode = Base64.encode(adddd, 10);
        String result = new String(encode);
        return result;
    }

}
