package com.dyy.yonxin.library2.cacheforandroid.uilistener;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.dyy.yonxin.library2.cacheforandroid.PropertiesActivity;
import com.dyy.yonxin.library2.cacheforandroid.config.PermissionConfig;
import com.dyy.yonxin.library2.cacheforandroid.util.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class PropertyConfigListener {
    private static WeakReference<PropertiesActivity> weakPropertiesActivity;

    private static PropertyConfigListener mMainConfigListener;
    private PropertyConfigListener(){}

    public static PropertyConfigListener getInstance(PropertiesActivity mPropertiesActivity){
        if(mMainConfigListener == null){
            mMainConfigListener = new PropertyConfigListener();
            weakPropertiesActivity = new WeakReference<>(mPropertiesActivity);
        }
        return mMainConfigListener;
    }

    public PermissionListener getPermissionListener(){
        return permissionListener;
    }

    public RationaleListener getRationaleListener(){
        return rationaleListener;
    }

    private static final int REQUEST_CODE_SETTING = 300;
    /**
     * 回调监听。
     */
    private static PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if(weakPropertiesActivity == null || weakPropertiesActivity.get() == null)
                return;
            switch (requestCode) {
                case PermissionConfig.STORAGE: {
                    ToastUtil.shorts("没有SD卡权限");
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(weakPropertiesActivity.get(), deniedPermissions)) {

                AndPermission.defaultSettingDialog(weakPropertiesActivity.get(), REQUEST_CODE_SETTING)
                        .setTitle("权限申请失败")
                        .setMessage("没有SD卡权限不能进行Properies存取！")
                        .setPositiveButton("好，去设置")
                        .show();

            }
        }
    };

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            if(weakPropertiesActivity == null || weakPropertiesActivity.get() == null)
                return;
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：

            // 自定义对话框。
            new AlertDialog.Builder(weakPropertiesActivity.get())
                    .setTitle("权限申请")
                    .setMessage("权限申请失败")
                    .setPositiveButton("去申请", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };


}
