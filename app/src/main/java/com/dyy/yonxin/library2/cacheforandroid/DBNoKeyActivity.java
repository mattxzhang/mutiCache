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
        ToastUtil.shorts("保存成功");

    }

    private CheckFormUtil getCheckDBUserInputData() {
        String name = edtUserName.getText().toString();
        String password = edtUserPwd.getText().toString();
        CheckFormUtil util = new CheckFormUtil();

        return util.addFormElement(name, "请输入用户名")
                .addFormElement(password, "请输入密码");
    }

    public void clickDBUserDelete(View view) {
        CheckFormUtil checkFormUtil = new CheckFormUtil();
        String userName = edtUserName.getText().toString();

        if(!checkFormUtil.isPassCheck(userName,"请输入用户名")){
            ToastUtil.longs(checkFormUtil.getErrMessage());
            return;
        }
        List<DBUserNoKey> dbUsers =  DBManager.getNoKeyDao().queryBuilder().where(DBUserNoKeyDao.Properties.Name.eq(userName)).build().list();
        if(dbUsers != null && dbUsers.size()>0){
//            DBManager.getNoKeyDao().deleteInTx(dbUsers);//无主键不能用，失败。
            DBManager.getSession().getDatabase().execSQL("delete from DBUSER_NO_KEY where name='"+userName+"'");
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

        DBUserNoKey user = new DBUserNoKey();
        user.setPassword(edtUserPwd.getText().toString());
        user.setName(edtUserName.getText().toString());

        updateUser(user);
    }

    private void updateUser(DBUserNoKey newUser) {
        //这个也只能用SQL语句
        List<DBUserNoKey> dbUsers =  DBManager.getNoKeyDao().queryBuilder().where(DBUserNoKeyDao.Properties.Name.eq(newUser.getName())).build().list();
        if(dbUsers!=null && dbUsers.size() > 0){
            DBManager.getSession().getDatabase()
                    .execSQL("update DBUSER_NO_KEY set password=? where name=?"
                            ,new Object[]{newUser.getPassword(),newUser.getName()});
            ToastUtil.longs("更新成功");
        }else{
            ToastUtil.longs("没有该用户");
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
