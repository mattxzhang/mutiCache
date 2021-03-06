package com.dyy.yonxin.library2.cacheforandroid.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.bean.DBUser;
import com.dyy.yonxin.library2.cacheforandroid.bean.DBUserNoKey;

import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserDao;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserNoKeyDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cacheUserDaoConfig;
    private final DaoConfig dBUserDaoConfig;
    private final DaoConfig dBUserNoKeyDaoConfig;

    private final CacheUserDao cacheUserDao;
    private final DBUserDao dBUserDao;
    private final DBUserNoKeyDao dBUserNoKeyDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cacheUserDaoConfig = daoConfigMap.get(CacheUserDao.class).clone();
        cacheUserDaoConfig.initIdentityScope(type);

        dBUserDaoConfig = daoConfigMap.get(DBUserDao.class).clone();
        dBUserDaoConfig.initIdentityScope(type);

        dBUserNoKeyDaoConfig = daoConfigMap.get(DBUserNoKeyDao.class).clone();
        dBUserNoKeyDaoConfig.initIdentityScope(type);

        cacheUserDao = new CacheUserDao(cacheUserDaoConfig, this);
        dBUserDao = new DBUserDao(dBUserDaoConfig, this);
        dBUserNoKeyDao = new DBUserNoKeyDao(dBUserNoKeyDaoConfig, this);

        registerDao(CacheUser.class, cacheUserDao);
        registerDao(DBUser.class, dBUserDao);
        registerDao(DBUserNoKey.class, dBUserNoKeyDao);
    }
    
    public void clear() {
        cacheUserDaoConfig.clearIdentityScope();
        dBUserDaoConfig.clearIdentityScope();
        dBUserNoKeyDaoConfig.clearIdentityScope();
    }

    public CacheUserDao getCacheUserDao() {
        return cacheUserDao;
    }

    public DBUserDao getDBUserDao() {
        return dBUserDao;
    }

    public DBUserNoKeyDao getDBUserNoKeyDao() {
        return dBUserNoKeyDao;
    }

}
