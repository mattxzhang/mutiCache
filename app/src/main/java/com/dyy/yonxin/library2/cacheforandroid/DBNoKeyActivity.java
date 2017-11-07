package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.dyy.yonxin.library2.cacheforandroid.bean.DBUserNoKey;
import com.dyy.yonxin.library2.cacheforandroid.db.DBUserNoKeyDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.CheckFormUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ToastUtil;

import org.greenrobot.greendao.query.Query;

import java.util.List;

public class DBNoKeyActivity extends AppCompatActivity{
    private EditText edtUserName;
    private EditText edtUserPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbno_key);

        edtUserName = (EditText) findViewById(R.id.edt_user_name);
        edtUserPwd = (EditText) findViewById(R.id.edt_user_pwd);
    }

    public void clickDBUserAdd(View view) {
        CheckFormUtil checkFormUtil = getCheckDBUserInputData();
        if (!checkFormUtil.isPassCheck()) {
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }

        saveDBUser();
    }

    private void saveDBUser() {
        DBUserNoKey user = new DBUserNoKey();
        user.setName(edtUserName.getText().toString());
        user.setPassword(edtUserPwd.getText().toString());

        DBManager.getNoKeyDao().insert(user);
        ToastUtil.shorts(CacheForAndorid.getRes().getString(R.string.hint_save_success));

    }

    private CheckFormUtil getCheckDBUserInputData() {
        String name = edtUserName.getText().toString();
        String password = edtUserPwd.getText().toString();
        CheckFormUtil util = new CheckFormUtil();

        return util.addFormElement(name, CacheForAndorid.getRes().getString(R.string.please_input_user_name))
                .addFormElement(password, CacheForAndorid.getRes().getString(R.string.please_input_user_password));
    }

    public void clickDBUserDelete(View view) {
        CheckFormUtil checkFormUtil = new CheckFormUtil();
        String userName = edtUserName.getText().toString();

        if(!checkFormUtil.isPassCheck(userName,CacheForAndorid.getRes().getString(R.string.please_input_user_name))){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }
        List<DBUserNoKey> dbUsers =  DBManager.getNoKeyDao().queryBuilder().where(DBUserNoKeyDao.Properties.Name.eq(userName)).build().list();
        if(dbUsers != null && dbUsers.size()>0){
            //你不能使用下列代码，因为DBUserNoKey这张表没有主键
            //you cannot use this line of code,because you have no Primary Key in table BUSER_NO_KEY.
//            DBManager.getNoKeyDao().deleteInTx(dbUsers);
            DBManager.getSession().getDatabase().execSQL("delete from DBUSER_NO_KEY where name='"+userName+"'");
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

        DBUserNoKey user = new DBUserNoKey();
        user.setPassword(edtUserPwd.getText().toString());
        user.setName(edtUserName.getText().toString());

        updateUser(user);
    }

    private void updateUser(DBUserNoKey newUser) {
        //这个也只能用SQL语句
        //this is also only SQL can be used.
        List<DBUserNoKey> dbUsers =  DBManager.getNoKeyDao().queryBuilder().where(DBUserNoKeyDao.Properties.Name.eq(newUser.getName())).build().list();
        if(dbUsers!=null && dbUsers.size() > 0){
            DBManager.getSession().getDatabase()
                    .execSQL("update DBUSER_NO_KEY set password=? where name=?"
                            ,new Object[]{newUser.getPassword(),newUser.getName()});
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_update_success));
        }else{
            ToastUtil.longs(CacheForAndorid.getRes().getString(R.string.hint_not_exist_user));
        }
    }

    public void clickDBUserSelect(View view) {
        Query<DBUserNoKey> query= DBManager.getNoKeyDao().queryBuilder().build();
        List<DBUserNoKey> dbUsers = query.list();
        for(DBUserNoKey dbUser:dbUsers){
            ToastUtil.longs("id="+dbUser.getName()+"\nname="+dbUser.getPassword());
        }
    }

}
