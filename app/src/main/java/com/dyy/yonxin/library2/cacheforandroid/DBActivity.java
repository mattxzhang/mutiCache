package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dyy.yonxin.library2.cacheforandroid.bean.DBUser;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.CheckFormUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ToastUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DBActivity extends SimpleCacheActivity {
    private EditText edtUserId;
    private EditText edtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        edtUserId = (EditText) findViewById(R.id.edt_user_id);
        edtUserName = (EditText) findViewById(R.id.edt_user_name);
    }

    @Override
    protected String getInstructionName() {
        return "dbInstruction.txt";
    }

    public void clickDBUserAdd(View view) {
        CheckFormUtil checkFormUtil = getCheckDBUserInputData();
        if(!checkFormUtil.isPassCheck()){
            ToastUtil.longs(checkFormUtil.getErrMessage());
           return;
       }

       saveDBUser();

    }

    private void saveDBUser() {
        DBUser user = new DBUser();
        user.setName(edtUserName.getText().toString());

//        if(hasSavedDBUser(user.getId()+"")){
//            ToastUtil.longs("已经保存过该用户");
//            return;
//        }
        DBManager.getMyDBDao().insert(user);
        ToastUtil.shorts(CacheForAndorid.getRes().getString(R.string.hint_save_success));

    }

    private boolean hasSavedDBUser(String strId) {
        QueryBuilder qb = DBManager.getMyDBDao().queryBuilder();
        ArrayList<DBUser> dbUsers = (ArrayList<DBUser>) qb.where(DBUserDao.Properties.Id.eq(strId)).list();
        return dbUsers.size()>0;
    }

    private CheckFormUtil getCheckDBUserInputData() {
        String name = edtUserName.getText().toString();
        CheckFormUtil util = new CheckFormUtil();
        return util.addFormElement(name,CacheForAndorid.getRes().getString(R.string.please_input_user_name));
    }

    public void clickDBUserDelete(View view) {
        CheckFormUtil checkFormUtil = new CheckFormUtil();
        String userId = edtUserId.getText().toString();

        if(!checkFormUtil.isPassCheck(userId,CacheForAndorid.getRes().getString(R.string.please_input_user_id))){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }
        List<DBUser> dbUsers =  DBManager.getMyDBDao().queryBuilder().where(DBUserDao.Properties.Id.eq(userId)).build().list();
            if(dbUsers != null && dbUsers.size()>0){
                DBManager.getMyDBDao().delete(dbUsers.get(0));
                ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_delete_success));
            }else{
                ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_not_exist_user));
            }


    }

    public void clickDBUserUpdate(View view) {
        CheckFormUtil checkFormUtil = getCheckDBUserInputData();
        if(!checkFormUtil.isPassCheck()){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }

        DBUser user = new DBUser();
        user.setId(Long.parseLong(edtUserId.getText().toString()));
        user.setName(edtUserName.getText().toString());
        if(!hasSavedDBUser(""+user.getId())){
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_cannot_update_not_exist_user));
            return;
        }

        updateUser(user);
    }

    private void updateUser(DBUser newUser) {
        Query<DBUser> query= DBManager.getMyDBDao().queryBuilder().where(DBUserDao.Properties.Id.eq(""+newUser.getId())).build();
        List<DBUser> dbUsers  = query.list();
        DBUser oldUser = dbUsers.get(0);
        oldUser.setName(newUser.getName());
        DBManager.getMyDBDao().update(oldUser);
        ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_update_success));

    }

    public void clickDBUserSelect(View view) {
        Query<DBUser> query= DBManager.getMyDBDao().queryBuilder().build();
        List<DBUser> dbUsers = query.list();
        for(DBUser dbUser:dbUsers){
            ToastUtil.longs("id="+dbUser.getId()+"\nname="+dbUser.getName());
        }

    }

    public void clickDBUserDeleteAll(View view) {
        List<DBUser> dbUsers =  DBManager.getMyDBDao().queryBuilder().build().list();
        if(dbUsers != null && dbUsers.size()>0){
            DBManager.getMyDBDao().deleteAll();
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_delete_success));
        }else{
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_not_exist_user));
        }
    }

    public void clickDBUserAddTx(View view) {
        CheckFormUtil checkFormUtil = getCheckDBUserInputData();
        if(!checkFormUtil.isPassCheck()){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }
        saveDBUserTx();
    }

    private void saveDBUserTx() {
        DBUser user = new DBUser();
        user.setName(edtUserName.getText().toString());
        DBUser user2 = user.cloneDBUser();
        DBUser user3 = user.cloneDBUser();

        //注意不能用同一个对象，否则bug
        //Notice that you cannot user a same object,otherwise bug will appear
        DBUser[] users = new DBUser[3];
        users[0]=user;
        users[1]=user2;
        users[2]=user3;
        try{
            DBManager.getMyDBDao().insertInTx(users);
            ToastUtil.shorts(CacheForAndorid.getRes().getString(R.string.hint_save_success));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void clickDBUserDeleteTx(View view) {
        //offset:跳过前面多少条，limit:查询数目多少条，所以此处查到的是第二条和第三条
        //offset:skip front count of data,limit:how many datas would you want
        // in this place you can get the second and the third line of datas
        List<DBUser> dbUsers =  DBManager.getMyDBDao().queryBuilder().offset(1).limit(2).build().list();
        if(dbUsers != null && dbUsers.size()>0){
            DBManager.getMyDBDao().deleteInTx(dbUsers);
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_delete_success));
        }else{
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_not_exist_user));
        }
    }

    public void clickDBUserUpdateTx(View view) {
        CheckFormUtil checkFormUtil = getCheckDBUserInputData();
        if(!checkFormUtil.isPassCheck()){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }

        DBUser user = new DBUser();
        user.setName(edtUserName.getText().toString());

        updateUserTx(user);
    }

    private void updateUserTx(DBUser newUser) {
        Query<DBUser> query= DBManager.getMyDBDao().queryBuilder().build();
        List<DBUser> dbUsers  = query.list();
        for(DBUser dbUser:dbUsers){
            dbUser.setName(newUser.getName());
        }
        DBManager.getMyDBDao().updateInTx(dbUsers);
        ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_update_success));

    }
}
