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
        ToastUtil.shorts("保存成功");

    }

    private boolean hasSavedDBUser(String strId) {
        QueryBuilder qb = DBManager.getMyDBDao().queryBuilder();
        ArrayList<DBUser> dbUsers = (ArrayList<DBUser>) qb.where(DBUserDao.Properties.Id.eq(strId)).list();
        return dbUsers.size()>0;
    }

    private CheckFormUtil getCheckDBUserInputData() {
        String name = edtUserName.getText().toString();
        CheckFormUtil util = new CheckFormUtil();

        return util.addFormElement(name,"请输入用户名");
    }

    public void clickDBUserDelete(View view) {
        CheckFormUtil checkFormUtil = new CheckFormUtil();
        String userId = edtUserId.getText().toString();

        if(!checkFormUtil.isPassCheck(userId,"请输入用户ID")){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }
        List<DBUser> dbUsers =  DBManager.getMyDBDao().queryBuilder().where(DBUserDao.Properties.Id.eq(userId)).build().list();
            if(dbUsers != null && dbUsers.size()>0){
                DBManager.getMyDBDao().delete(dbUsers.get(0));
                ToastUtil.longs("删除成功");
            }else{
                ToastUtil.longs("不存在该用户");
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
            ToastUtil.longs("该用户不存在，无法修改数据");
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
        ToastUtil.longs("更新成功");

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
            ToastUtil.longs("删除成功");
        }else{
            ToastUtil.longs("不存在该用户");
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
        DBUser[] users = new DBUser[3];
        users[0]=user;
        users[1]=user2;
        users[2]=user3;
        try{
            DBManager.getMyDBDao().insertInTx(users);
            ToastUtil.shorts("保存成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void clickDBUserDeleteTx(View view) {
        //offset:跳过前面多少条，limit查询数目多少条，所以此处查到的是第二条和第三条
        List<DBUser> dbUsers =  DBManager.getMyDBDao().queryBuilder().offset(1).limit(2).build().list();
        if(dbUsers != null && dbUsers.size()>0){
            DBManager.getMyDBDao().deleteInTx(dbUsers);
            ToastUtil.longs("删除成功");
        }else{
            ToastUtil.longs("不存在该用户");
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
//        if(!hasSavedDBUser(""+user.getId())){
//            ToastUtil.longs("该用户不存在，无法修改数据");
//            return;
//        }

        updateUserTx(user);
    }

    private void updateUserTx(DBUser newUser) {
        Query<DBUser> query= DBManager.getMyDBDao().queryBuilder().build();
        List<DBUser> dbUsers  = query.list();
        for(DBUser dbUser:dbUsers){
            dbUser.setName(newUser.getName());
        }
        DBManager.getMyDBDao().updateInTx(dbUsers);
        ToastUtil.longs("更新成功");

    }
}
