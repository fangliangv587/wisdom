package com.xz.cenco.wisdom;

import android.app.Application;

import com.xz.cenco.wisdom.entity.DaoMaster;
import com.xz.cenco.wisdom.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2018/2/25.
 */

public class App extends Application {


    private DaoSession daoSession;

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
