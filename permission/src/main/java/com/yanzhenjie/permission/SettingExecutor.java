/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.permission;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.target.Target;

/**
 * <p>Setting executor.</p>
 * Created by Yan Zhenjie on 2016/12/28.
 */
class SettingExecutor implements SettingService {

    private Target target;
    private int mRequestCode;

    SettingExecutor(@NonNull Target target, int requestCode) {
        this.target = target;
        this.mRequestCode = requestCode;
    }

    @Override
    public void execute() {
//        if (MiuiOs.isMIUI()) {
//            Intent intent = MiuiOs.getSettingIntent(target.getContext());
//            if (MiuiOs.isIntentAvailable(target.getContext(), intent)) {
//                target.startActivityForResult(intent, mRequestCode);
//            }
//        } else {
            try {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:" + target.getContext().getPackageName()));
                target.startActivityForResult(intent, mRequestCode);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    target.startActivityForResult(intent, mRequestCode);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
//        }
    }

    @Override
    public void cancel() {
    }
}