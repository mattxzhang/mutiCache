package com.dyy.yonxin.library2.cacheforandroid;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dyy.yonxin.library2.cacheforandroid.util.FileUtil;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public abstract class SimpleCacheActivity extends AppCompatActivity {
    public void clickInstructions(View view){
        String instructions = FileUtil.getFromAssets(getInstructionName());
        showInstructionDialog(instructions);
    }

    protected abstract String getInstructionName();

    private void showInstructionDialog(String instructions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(CacheForAndorid.getRes().getString(R.string.btn_instructions))
                .setMessage(instructions)
                .show();
    }
}
