package com.dyy.yonxin.library2.cacheforandroid.cache.sub;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.cache.DBCache;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.DBExchangeUtil;

/**
 * Created by 段钰莹 on 2017/11/7.
 */

public class CacheUserDBCache extends DBCache<CacheUser> {
    private void saveObjByType(CacheUser mCacheUser) {
        saveCacheUser(mCacheUser);
    }

    private CacheUser restoreNormalData(CacheUser t) {
        return DBExchangeUtil.getCacheUserDBRestore(t);
    }

    private CacheUser getObjectInDB(CacheUser mCacheUser) {
        return getCacheUserInDB();
    }

    private void saveCacheUser(CacheUser mCacheUser) {
        if (mCacheUser == null)
            return;
        mCacheUser = DBExchangeUtil.getCacheUserReadyDB(mCacheUser);
        CacheUser nowCacheUser = getCacheUserInDB();
        if (nowCacheUser == null)
            DBManager.getCacheUserDao().insert(mCacheUser);
        else {
            DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME + " where USER_ID=" + mCacheUser.getUserId());
            DBManager.getCacheUserDao().insert(mCacheUser);
        }
    }
}
