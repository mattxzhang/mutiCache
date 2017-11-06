package com.dyy.yonxin.library2.cacheforandroid.manager;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserDao;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserNoKeyDao;
import com.dyy.yonxin.library2.cacheforandroid.db.DaoMaster;
import com.dyy.yonxin.library2.cacheforandroid.db.DaoSession;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class DBManager {
    private static final String PWD = "123";

    private static DaoSession encryptedSession;
    private static final String DB_NAME = "my.db";
    public static DaoSession getSession(){
        if(encryptedSession == null){
            DBHelper dbHelper = new DBHelper(CacheForAndorid.getSuperContext(),DB_NAME,null);
            encryptedSession = new DaoMaster(dbHelper.getEncryptedWritableDb(PWD)).newSession();
        }
        return encryptedSession;
    }

    private static DBUserDao userDao;
    public static DBUserDao getMyDBDao(){
        if(userDao == null){
            userDao = getSession().getDBUserDao();
        }
        return userDao;
    }

    private static DBUserNoKeyDao noKeyDao;
    public static DBUserNoKeyDao getNoKeyDao(){
        if(noKeyDao == null){
            noKeyDao = getSession().getDBUserNoKeyDao();
        }
        return noKeyDao;
    }

    private static CacheUserDao cacheUserDao;
    public static CacheUserDao getCacheUserDao(){
        if(cacheUserDao == null){
            cacheUserDao = getSession().getCacheUserDao();
        }
        return cacheUserDao;
    }
}
