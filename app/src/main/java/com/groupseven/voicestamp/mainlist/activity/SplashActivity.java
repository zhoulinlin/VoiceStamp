package com.groupseven.voicestamp.mainlist.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.groupseven.voicestamp.login.ui.login.LoginActivity;
import com.groupseven.voicestamp.tools.SharedPreferencesUtil;

/**
 * 启动页面
 */
public class SplashActivity extends AppCompatActivity {


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPreferencesUtil.isLogin(getApplication())){
            MainActivity.actionStart(SplashActivity.this);
            finish();
        }else{
            LoginActivity.actionStart(SplashActivity.this);
            finish();
        }

    }
}
