package com.groupseven.voicestamp.mainlist.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.login.ui.login.LoginActivity;
import com.groupseven.voicestamp.login.ui.login.LoginViewModel;
import com.groupseven.voicestamp.login.ui.login.LoginViewModelFactory;

public class SettingActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    private final Handler mHandler = new Handler();


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setStatusBarFullTransparent();
        setFitSystemWindow(false);


        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        findViewById(R.id.tv_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.logout();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingProgressBar.setVisibility(View.GONE);
                        LoginActivity.actionStart(SettingActivity.this);
                        finish();

                    }
                },3000);
            }
        });

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.actionStart(RecorderMainActivity.this);
                finish();
            }
        });
    }
}
