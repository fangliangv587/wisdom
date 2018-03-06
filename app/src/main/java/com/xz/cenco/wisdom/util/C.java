package com.xz.cenco.wisdom.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2018/3/6.
 */

public class C {

    public static final class extra{
        public static final String float_service_name = "float_service_name";
    }

    public static final class file{
        public static final String root_path = Environment.getExternalStorageDirectory()+ File.separator+"wisdom";
        public static final String backup_path = root_path + File.separator +"backup";
        public static final String backup_data_path = backup_path + File.separator +"data.txt";
    }

}
