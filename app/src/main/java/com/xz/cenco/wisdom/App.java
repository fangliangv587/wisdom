package com.xz.cenco.wisdom;

import android.app.Application;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

import com.xz.cenco.wisdom.entity.DaoMaster;
import com.xz.cenco.wisdom.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2018/2/25.
 */

public class App extends Application {


    private DaoSession daoSession;
    public static  int screenDensity;
    public static  int captureResultCode;
    public static Intent captureIntent;
    public static MediaProjectionManager mediaProjectionManager;;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public DaoSession getDaoSession(){
        if (daoSession == null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "wisdom-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
        }

        return daoSession;

    }
}
