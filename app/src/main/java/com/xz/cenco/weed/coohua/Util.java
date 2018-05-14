package com.xz.cenco.weed.coohua;


import android.os.Build;
import android.support.annotation.RequiresApi;
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
        user1.setIndex(1);

        User user2 = new User("39ed9fa09451d04a", "13221809346", "123456", "", "armeabi-v7a", "866822033471269", "68:df:dd:12:9a:3e", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACPF6S8pqvqSqihVyzdWdOMVsklarApEIeIkjoM_vdDXVKGecLpr9o4yiVCDIENSQSbelJ_3WDSYyL9wC9z79brawWtFcGihOwVedf_UxfolWte7y905PN7MBOvcEOtrzW2YbCbGrR7fpg49GlXJeBwsOMYvf9qpHp-jhIn3fVYKCdrPkmE2ccpPCXNKusGe3G_mu5QNgPS69IPMVaZEbKAKn7OgGwPvkol9Jx5eaPYE8p8Sn7XzMlFqdKZ0z5g_ZmlMwmoI6CHl36dmx0TNdeeI=", "5.2.3.0", "118392029184", "357615696645247", "1080X1920", "MI6");
        user2.setComment("xz阿里小号主机");
        user2.setIndex(2);

        User user3 = new User("ce2dc942ec9b1933", "15665851629", "123456", "58:44:98:58:7C:BF", "armeabi-v7a", "", "58:44:98:58:7c:c0", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACCtCGmJzlg0Cc1KezYyebu65MP6HfWigQeZO38qRv7-EqLHzaIhYheOqmC4MSK6D03oAQhAGq32RtHpyKeoqms5549IrAHxHlBfpOcfJelCvVljtJYmpJbqeJW3YELwjVqdzewAfLSfCbECCLA-ZDFHsHD1JciZof5H2MKRV_fGKiUbWRNU1X1Jh3oAAnz3tdpVNNPjfh1h7urJ1H0sUeEOYUOUkbymlJEFxVK3VKcm9hWKR5rPWql2jbFPk-0yQtq086arm1El7Mge-HhzyvGg=", "5.2.3.0", "12511289344", "357616696626247", "1080X1920", "RedmiNote2");
        user3.setComment("ss主号 wangfa 测试机");
        user3.setIndex(3);

        User user4 = new User("7970b401dd14cacc", "13047488791", "123456", "", "armeabi-v7a", "865214036219997", "", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACD9GQHvNeiiJVx-CA6YRZAJZlsivkekd0_MLEi4t3kAK4E_-Fhg9Lyb1T72q6z_RenoAQhAGq32RtHpyKeoqms5549IrAHxHlBfpOcfJelCvRre0ssSufTHwObyF0ZDdjlM5yHmMHscQzvHmiEDhtMZ9eyR5TKfVFYKaFXHednI2WLgDa_SAjedAxz7wnxgb9EOvwKEieKlW4ovIhZpA2lldtiqKLu28lxVW9NA2L_9udOLFi_QNAriz83wz8iVj8I_0LTz3kax1gwHnGk71aHM=", "5.2.3.0", "57464045568", "353515524688244", "1080X1794", "FRD-AL10");
        user4.setComment("ss小号 huxiaoxiao 主机");
        user4.setIndex(4);

        //账号异常 18754608342

        User user5 = new User("88c3f1e03d8db1ee", "18754604786", "asdfghjkl", "10:A4:BE:7D:04:CD", "armeabi-v7a", "", "10:a4:be:7d:04:cc", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACOzaiROSl9Ut7Do4eg0n-_1OkB27PDK6652y5GqwfBMi3blv_qPpboGMBXDuUOzqli22ZUGPJiH_faOgcnwFwoBw0TV3Ztvwf4a0YWoYPw5Dq6QX6SJJc8xS9ugjWsZoMGSyUnmMOUuFNsaKuG2997avTzb9gDFSfr7gUlkSkvHnidfeCCav9jOz-UgyqbdQvB6MZ2KV4C6kH8MuM8sw_IfQFAJik0yzvPc5aXfvz7EJa9f7XAG5EGbPQDWcO91uCrCYK6XxIGPjS3ME6hKrJ5X9DL2zwtDbqruL1gj_DToKpKFbYLh0l1GDT7sE-gTGPnExDOBmcolZkT5jZ2Pr-uslzvNEAYvSdAFQh5JLUX5ijv6MmKFW8ULZOJaNru8Ex_HEJEJOoHXQuO99-xSsYKKp8W0wLOWKlcFQKXhOOi1ghvGmi_iywqSRgfy17QSW29zUg3QKanCWBI2zVa5g1E-zknik_yapKM-MhPIlh-GQIIvfcMPEqMWBXd6Qn5VwwixOJgkgDWyqwd7gsPzCZ763zpiQR7u28xczXDE9zF_NAeLfl8Snw9pPM9dB6ZBr3TXw20ygfhhEOSZ6eFbFnfeguaKTec4JDLRJavci5Iskbf6M0njvlI-3n-SkL6shVpwfxA-lZOMLZTAARa5qeSHNFHOSUW1omXsUQQlgzftZwPh_yUJ9R5vz0bddLD6lmYC3VOmdRbMRar_oGGHJKhYCd9PJ97EhPzuP6g4WLTvKZmIbPGs97vq-zdio1ILy513PdHFb8wS5fbf6IssscIHfGFWbm7waNPm14f3-osbLvbdWCPwZ83lv5PosMJkou-bHvg_QHIrlgCCvESD1mUHjAKuTTfY4yOoKTTrEkqPx6j-qO_meGoV1NFsUFL1R17ATD2TPn7itTRrGS2_o0JZcK6dYLYAOBY7WofJrTtGRKNKeGQcVY-jhtS-yxsdpcz2atWSlr8ODDygNOgeaKUfQr5DI0PcY7-3GdVG1impooQGXdxsOONTwsKpFgpzeWjqi5ViLtdHbVfL4FomCjKU7LapQHV8YP1JJPrOvWF9smzUxiuNfTlNEby-1b71LYGovW7mp3homzVCg-Urp8RCBcFkJ8Dw56wsmnFya9ig2e-FdJFLB-w5p8xQveykWyDG4KtLeUe9_l4sJ-2iotxD0Ie6TOLozcLbt6km0niTKbbFC4fZ8o1oCsvngvQgzquDSMnR-cfkp0eoVIKCOnNf1iWziqZ5rClb-fRXCoP8-C7mmIs60aoGpJFLat4NaXa3hiNO0WpxjEjXUkM1sc24pSOQXrHKIzyGC1B3SjD2JmikcjvvTx3WE0lMQLuty91o5CdRea8Xj7iYPgpyI2K0mDZrg8X_X9KtQ1vbNL8Xj66I_fXijmEjz4n1h4-HAmBzEwQhFNpBRxFL-OSw4h2eTZcjBH0qsK6_Db1yQz9J2LLeNRNNGocvJzhASDDiDRMoyEg7JmnaRQcSvM6IomNfXxoZjhZX2P5t654s09Jg_AeTqqzWMjoNdAEQHLsm2bqA1R1QpkvRzmhVetaKEHJEarmQpsYd93Gp79SqCcfxoguWDJ2-tEY3Xgu2Hy_Tcdk0JXGmyWYz_HqKC-UfNNe4sDcf53YuoQavMzn2YN4ZP4yN3LUbswBvnkD71TDtOn2peAqGKlNiciMxmZ0IATukg0B1vuVqxU-6fe65fCSa8nVIofb-QU_-GvVN4YPbr-HNTV260ynEoD42TLVOWYBL8d5xy7ITcARzXe-Ca6LQOt0oWVMpq5yd0k0tW9zvZawX1FiTYiWXVpiWXlW5113t2LLmScyvsQU0oASufMaKSqpET0YzE4cFs_pBIVcu27s_dVu9CMZkUj1I0Db56JnZhvejtFi-rz3y7jrG3O4GONuGPODJR3hyQKQ9gGiEFUxoVZU8GGYGlaC1vLNU17P83QpfqrHtH5BFO40Klm0Vp83OwyOAFhx5pSHw6Sz-oS5weCkb1V5KWLRz0hR6fYv4qhkHnR-ufKA3X5JBalO160skOeaax_IgfeIZ394uG-7VNi4ghm_WzFNoaPFy79dp3qjjh9poGC4dOlb6fOpgqNoc6rSFk8uCktRmkjOS7LOgOAm7dYzG7hlHYvmrL5F1VTD1pJjxrOqyc_sxTKCSGV9O1co_zLHvochgDZ5jgTPu-DBheL7wV3fNfy07IXVJMA-9g_CD5VhSD_UhVk4wiSann6HlsEpAjBhYk83r1bIw_Tujr7waVHy0Lr3N4Oh58hRGGmZR1ntz77p8zuTRNfVaHZrO0zFXEBi3GYYFpJYwuSBNlizqc1fI8ySJ6mMS6F66ba36oaD492xHygMWDp0erLgO8MM52G-5PFQWbg-6o83sdZrXRhEm3EQ5jiafSREUz0BkJE7MWBxr7Bgtj53hFdz1rPt82_wFjgRXtsLCZ6rykZ8i7HG7RcOQTb13vuxgw3Hh_v-TVNhNUBvvIyDoiVLUbD0LXGBno6Ynu533w0SKRfYwM7_aIR0nn9qIVDKX1h5Ot47_bB04tdhMu9YampWNPUecDlnmPcErJNHlj1HHQJX9DOK4Ej2Fe6HUCgLfskYdDoH6FVL-Go42OME1SwTSliLTADyASqrxa-Enw-xYTLUMey-AMXGls2iKhxyNvqSt0Z0yU1wCETMDAGfxcUFRiVxJrpZeOBneWLiNVsrrFMqCpNYnNlwMSQ22_pR6GA4CEd5DGDnqO1AO0HhOGyMH0H0r21rK1lzVlCffL6EsZOrsnijAOuGmmM6a9RmPrgbnavjYCqnoQWIWNcRMnWol6hVpQLbxu-FSaBteOYvW0Ewys2drK7Blak7oRcWjabmLQ0_SvnqYHyN-EUyT2y7QzxVDHY_4_E57b0bnjGew0da-ZzZfK7et0Fopw4miDOMDJp4GEuuAS9DIFqhcx7W4ynfJA0KUr43HOn-D8M4bUZp-r44tjrSUIZtx0XQdlSXasmJv6y2T3xq9LjkOj2_w4b509tpIbljve2e90DDe504lpoTPqtwmOB5jUX3LwWy8PDg9naGMpY01wEu7Uyn0HTZE56DRlEdCmcvln0z1ZwfXOsFl_09sJc2YqMEGBeih9E605HNuTX8xNO40EiBipjf4kzQn0WPbQVXStCDbi5aMKfVruGQw7gBIGTZKKMHO--EsLnK01A75aJQr8N-u4AcM-h9gqzuk-5q6BnFtF-YMHu4zmjMr5hx63aP4k6HO26d-ucpAIawtY4dFFZvaEoZSWbmvog7WE7J0FUL07VVN5xtIQabSD_axLtTejw8fJ7JSzUb_rBLZRQjn2N5CLgsHPeiwJMCww4vH17mbVqMlgThsOH7yuElegKpSzeUUODS0PyBSDosO6-mBDe5LJAARGAIMIK_2zlrosH-f2sWdXVXxphvDOpGClWasgIiBdiPISY2EK7QqpzBmiyPhzZzj3D5pmUnygKwnADd0exrracHZX5n_FVREW5QXjRMvFDCnoOI6Xu0TWuuz2u7aQddkYsKO87oSTFr6QD_9h6-WT5D6WIS4N1HT2CiZGeR_A4F7ZEg3TkNG8mdqLXTgAdxZwyoEXMQAHdV_5ZPipJp6p2hh_wIr7er3QUDOIbWjKLJYnRnQOuKNS-o9whZAZ1A4oRpkdn7UIpnX7rA29TdkeNiZVp4do00kReoeBpVqjyw5Nxt-dXjgHrN_sEgW4Kkv04tDYuCGV0ISCFzAoa4kM3HN1IQx4LAJi9Ind_aCk3o_cqqoVS8XIO4w_kHlqONEKbkFkkJN287swwnPY9Z8Ww4egpxIIIMUnDM_k-xo0sJFTq_d48tSvHIhAit8t91ZqNs1XhonUmGAyJ9oNWnGVD03oht31TBpdPSEdewy1NB9sfHrX4ohxlMhc9VfniQQKtzvexfG6jKtKKQIZAY3G913EDnHOUxx_BhOZm1JOPa90gbAmc8_YhxVd9segf7vgQMQa8CmiI0j_cEsP-S4n6jzJhDrcAQmd2QaKfC8rlEZVyhd1oqqvav_rd-oNE9vQbtFkeF3lN9pCtlr5x4e1F6ukcw4Bp6QVudqoYlqNEPjAr04D-Ex3I_4I5etAYWf1xSN7_OqJSlLAtHH8Ogu6w0Jeh8Xkdl9pLjuStmwAoJzzZ0wZd-q1YQ-4DICnaSgUMDDcANy0nF7o-wNcJO17Kp2CKFcCO3xVbBKfkoT0CUtW2kNzaGPC_VFokLV43U2Tngn2Kz2vI4B74yyrsBmTTShaqh-9UKOr1kKsdWKr0aRBlB2gXG6-aIm3-BU6j2yIUsCiDvPh4VYL6YnqaZYGUDgi3vbLRuuioimUxlJHWOXIhT01_P9VgY52OKNT1-xAKpHB0XI1PJgSWr5Y-vrj7aw8SAws6s7d10PjNGIfa-SBkFXhXEd67A6bP_1h7VotqaTr53ddK-AwYchD2BqtdkZon-SZuaE2YFGU8NaX4Nr6TbYKMSc9LYjIqI-fdTJ7xH7-lJ07si5TuFE77sLPNkDr8SLwd_NHq0ss-a1gRpXacA9BzD-bfFL1TgFfU8pcfQUaxfbV3Pvegk6dlA5Az8UzSHK_m4fabXkHhu9DYVUdkB2f-pbzvCKMwNOGrMJR8STPXKfaUtrBHpX7LqsKl4hWjQqtif2zeyVVcNLFPy8R6PGVQ9CwjlOVvmoT-g-WpJyNslf5GusM5qjD4a4lxvAnb0pHL5DOruBJX0iQteY9xOwuvxF7__3hIBxj_AN9nIJkdPP-J4Fp5MxJtCSKZGH-Fks-j1MSLeD_ZzD2uiDGkAXx3WtpreQQCj-Rj0IkCPJP_dFQNJz83D7JDy5DEl8NGtrgXLkwxJ8I6Zxi2q1dD1v4unO_hAGNRvv1ZShU7sExrTBO-LeWaGj0Wo2qUAoN_gaX-Dr16BTrNrBsHFtoQrual7_yZFAIkd1RhCvOnFUgz-9RG06ISFBiLipXViizf9D7z5_8SzTTL0rcHdc6-t1pziP7bqMs9Iwn7Y_C0uYK4cSv2iwonRj4yTRQLYAUTjPrpytbN7sczzFfnnp14K69K4mgB03_SmsKGsmF7SgCARQw5U0MhOd3SdkCzPdoPrjcjKXcLRdtx3JCgv96U_HOX1rZVln7sBGxfl0aayZjPQKCTcVrdYbMWFVjTOJzyY67UsDDabKRxBk_5CLJsRjDg9JgxcG_Lfx25H5x-csJeUPdocE0qNZ0xT3goahlPKDR5S8nqpLvcQ3UnJjgWjZMBfmLG5R7DvWAlV_rErFdaCOPTt50ukSke_Jh4Yb0pxWlSgxBqXHZLeBMJgEua26vvQp9HdVvOc_Wwn9d6CNptY91uOYXb5DDkFfmoT2XbRTTynz1alMeLu1LssrFCsnIPZi4aAFNI4YNES8s6hQMRwMDFt9u3OIKD3crjjigIsapcBH-S5UtcEqPnpPF_oTkKU7km0vGA2P03FbCj_35cfUzRUmxPa5sE5KJ1wCNiHsZTRi26jOO6zUYwjstaXc7hTB9nKxZzfC0rDg7N2-eNMsKcVjWlSomU6SBfYpp8wkK6e8OAqX1xmSFQ3xcXBUu-9naOIlQ3ihLG5Qc5qNh7L_pfQ==", "5.2.3.0", "2145193984", "357710996800995", "1366X768", "rk3288_box");
        user5.setComment("验证码2 双屏测试机04cc");
        user5.setIndex(5);


        User user6 = new User("638c192c85ff11e7", "18507045781", "zxcvbnm", "58:44:98:58:7C:BF", "armeabi-v7a", "", "58:44:98:58:7c:c0", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw4Ro4m3Kw-BcKkZOTlV3CoE4jiQ-Q2QnnAtaMQIQRPACAwzl0ZH0yvgT2vMMhBBCyu5MP6HfWigQeZO38qRv7-EETY6_WH0h_otbvHrLcb9aOFN0bOs1zLrd31YcoM8lov7f-ce0g3ucoeDQCuPYoILbTLqw6nIg7osnCO4ArOZd25_Oc6EroxMAzKVD65jKcrKl3uTwqe1x2aqSb40tWcvwuzFqqPfzrYFIwhINNvL8Ti9W8w49EI4czc8JT0HLzPzQ6noYcxaoi_AqAkBB0eQgeCav5lwGxobIJkpRVZ34kQ75vKXIQ0F2Tg3eWYR5ks=", "5.2.3.0", "12511289344", "357616696626247", "1080X1920", "RedmiNote2");
        user6.setComment("验证码3 wangfa 测试机刷机");
        user6.setIndex(6);

        //账号异常
        User user7 = new User("38362c3026739cab", "13002621846", "qweasdzxc", "68:DF:DD:0A:36:04", "armeabi-v7a", "", "68:df:dd:12:9a:3e", "O57cll08GzN0PbJ9c2Gjy9ifkw_QgdcF1sLFSEBS6QX0_OeqaV_TdZDhrGkhvAnPsrU7gmYhmuBH5lxrgk-lKu0eUNDkTprmuGp_nPIMBF82CAedOUR3IYQoLe_Dq08E25IuXKoPHVOLPMWm20RzRnAnYac26flPsuvSct4Q1p4hZ9WA-TWunAcMjX59cxiPBbxm56h1uHFIotAL6mHPbRjUvGYSlshuJanbRuCCT-lFt8uT0dz5TTVSs6xtsNEVswdUTVfFYQg2J8eczlnmRnF28l0h0bfBDW6nibhGB4VJ00z91RgsamLD0YKfjPL4bxWDzcb-MMCh-MuSmOXAYQLgLlnd8tbvwD_pUMThFnyKLjZOc1l6jR0wmsE8xHwGYUHrRxc7lE6PCEWZxq6Vc7DIx5MJ0CH9kSx4HKvxzHw=", "5.2.3.0", "2114560000", "357619179677247", "720X1280", "2013023");
        user7.setComment("验证码4 wangfa 测试机刷机");
        user7.setIndex(7);

        //账号异常
        User user8 = new User("39eb68ef72270220", "17024471244", "wsxedcrfv", "", "armeabi-v7a", "866952036353667", "", "V5cl4DyrfxTfteiXqzYX3U9ZJvPWysFh_ZJkVk9zz1SwmRxxOZE3pwC16GvlGHvrqGZiBruAj3k_8up_8WiAFnZf4HtngRIp180MvR9AXw5LOUoJMFSisT4qn7d0Xw_A4jiQ-Q2QnnAtaMQIQRPACFYba7jyauldNuwJCE4lLHO5MP6HfWigQeZO38qRv7-EmyCRWS4vJtEf2OU6GsNJLTDOzT6TtZdMknLNFQfJ31dw0o6NP0ypAculF7VTZEv310r4hQxQp4FTmKqHdLs5ZZKLia3PkH-nCtfQxZOxWAUzuUDJxnLVozs5qrJmQmh5RN6OG5PhkbNBXnMdCbYaEdrSmg4Y3KGFGTSkx_qY6mF6eQW4MRaUyavqiNrNghqSHumDlRrZFDgO7kMt6WmKqa5lwCCNbnlh4kvmSLhAk6Y=", "5.2.4.0", "120584126464", "353515314688247", "1080X2160", "BKL-AL20");
        user8.setComment("验证码4 wangfa 测试机刷机");
        user8.setIndex(8);

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);
        list.add(user6);
        list.add(user7);
        list.add(user8);
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
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
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
