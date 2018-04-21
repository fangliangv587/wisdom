package com.xz.cenco.wisdom.util;

import android.os.Environment;

import java.io.File;


/**
 * Created by Administrator on 2018/3/6.
 */

public class C {

    /**
     * 界面传递参数
     */
    public static final class extra{
        public static final String float_service_name = "float_service_name";
        public static final String color = "color";
        public static final String yearmonth = "yearmonth";
        public static final String date = "date";
    }

    /**
     * 文件相关
     */
    public static final class file{
        public static final String root_path = Environment.getExternalStorageDirectory()+ File.separator+"wisdom";
        public static final String log_path = root_path + File.separator +"log";
        public static final String backup_path = root_path + File.separator +"backup";
        public static final String backup_data_path = backup_path + File.separator +"data.txt";
        public static final String card_path = root_path+File.separator+"card";
        public static final String identifycard_path = card_path+File.separator+"zp.bmp";
    }

    /**
     * 请求码
     */
    public static final class request{
        public static final int type_add = 0x0001;
        public static final int wisdom_add = 0x0002;
        public static final int permission_dialog = 0x0003;
        public static final int permission_assist = 0x0004;
    }

    public static final class system{
    }

}
