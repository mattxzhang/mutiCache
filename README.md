# mutiCache
使用责任链模式实现多级缓存组合（Using responsibility chain model to implement multilevel cache combination）
使用了集合+SharePreferences+GreenDao(有sqlcipher加密)[used list++SharePreferences+GreenDao(with sqlcipher encrypt)]

## 简化各种存储方式（Simplify various storage types）
### SharePreferences

#### 使用SharePreferences保存（use SharePreferences save data）：
```
ShareUtils.resetShare()
    .set("isOpen",true)
    .set("loveAndroid","very")
    .set("experienceAge",2)
    .commit();
```
#### 使用SharePreferences取数据（use SharePreferences restore data）：
```
ShareUtils mShareUtil = ShareUtils.resetShare();
boolean isOpen = mShareUtil.getBoolean("isOpen");
String loveAndroid = mShareUtil.getString("loveAndroid");
int experienceAge = mShareUtil.getInt("experienceAge");
```

### Properties

####使用Properties存数据（use Properties save data）
```
 PropertiesUtil.resetProperties()
                .put("version",1)
                .put("dbVersion",2)
                .put("versionName",1.0)
                .put("isLatestVersion",true)
                .put("appName","CacheForAndroid")
                .save(saveFile);
```
####使用Properties读数据（use Properties restore data）
```
PropertiesUtil mPropertiesUtil = PropertiesUtil.resetProperties().loadProperties(saveFile);
        int version = mPropertiesUtil.getInt("version");
        int dbVersion = mPropertiesUtil.getInt("dbVersion");
        double versionName = mPropertiesUtil.getDouble("versionName");
        boolean isLatestVersion = mPropertiesUtil.getBoolean("isLatestVersion");
```

### Serailizable Object
#### 使用序列化对象保存（use Seriliable Object save data）：
```
SerialUtil.saveObjectInFile(user,saveFile);
```

#### 使用序列化对象取数据（use Seriliable Object restore data）：
```
User user = SerialUtil.restoreObjectByFile(saveFile,new User());
```

##责任链模式联合缓存（Responsibility chain model combined cache）
###存（save）
```
CacheUtil.setCacheUser(getMyCacheUser());
```
###取（restore）
```
CacheUser cacheUser = CacheUtil.getCacheUser();
```

###链式结构，可随意拆卸搭配（Chain structure can be freely disassembled and matched）：
```
 getCacheUserListCache().setNextCache(getCacheUserShareCache());
 getCacheUserShareCache().setNextCache(getCacheUserDBCache());
```