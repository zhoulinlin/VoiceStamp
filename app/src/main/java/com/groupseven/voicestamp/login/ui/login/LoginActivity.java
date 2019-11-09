package com.groupseven.voicestamp.login.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.login.data.LoginCallback;
import com.groupseven.voicestamp.login.data.Result;
import com.groupseven.voicestamp.mainlist.activity.MainActivity;
import com.groupseven.voicestamp.tools.DialogFactory;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private TextView tvSinup,tvForgetPassword;

    private RadioButton agreeButton;

    private EditText conPasswordEditText;

    private final static  int SIGNIN_MODE = 1;

    private final static int REGISTER_MODE = 2;

    private TextView registerBtn;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final TextView loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        agreeButton = findViewById(R.id.rbtn_agreement);

        conPasswordEditText = findViewById(R.id.password_comfirm);

        tvForgetPassword = findViewById(R.id.tv_forget_password);

        registerBtn = findViewById(R.id.register);

        usernameEditText.setText("test8@gmail.com");
        passwordEditText.setText("123456");

        tvSinup = findViewById(R.id.select_action);
        tvSinup.setTag(SIGNIN_MODE);

        showSigninView();

        tvSinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)view.getTag() == SIGNIN_MODE){
                    showRegisterView();
                    view.setTag(REGISTER_MODE);
                }else if((int)view.getTag() == REGISTER_MODE){
                    showSigninView();
                    view.setTag(SIGNIN_MODE);
                }
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

//                MainActivity.actionStart(LoginActivity.this);

                //Complete and destroy login activity once successful
//                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = passwordEditText.getText().toString();
                String conPass = conPasswordEditText.getText().toString();

                if(!TextUtils.isEmpty(password) && !password.equals(conPass)){
                    Log.d("LoginActivity","password:" +password +"   conPass:" +conPass);
                    Toast.makeText(LoginActivity.this,"Two passwords are inconsistent",Toast.LENGTH_SHORT).show();
                }else{

                    if(!agreeButton.isChecked()){
                        Toast.makeText(LoginActivity.this,"Please read the agreement",Toast.LENGTH_SHORT).show();

                    }else {
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        loginViewModel.register(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }

                }
            }
        });


        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingProgressBar.setVisibility(View.VISIBLE);

                loginViewModel.getAgreement(new LoginCallback() {
                    @Override
                    public void onHttpFinish(final Result result) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingProgressBar.setVisibility(View.GONE);

                                if(result instanceof Result.Success){
                                    String data = (String) ((Result.Success) result).getData();
                                    Spanned text = Html.fromHtml(data.toString());
                                    DialogFactory.chooseDialog(LoginActivity.this, "Agreement", text, "Agree", "Disagree", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    agreeButton.setChecked(true);
                                                }
                                            },
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    agreeButton.setChecked(false);
                                                }
                                            }, true);
                                }else{
                                    Toast.makeText(LoginActivity.this,"Get agreement failed!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });
            }
        });
    }


    private void showSigninView(){
        registerBtn.setVisibility(View.GONE);
        findViewById(R.id.login).setVisibility(View.VISIBLE);
        conPasswordEditText.setVisibility(View.GONE);
        tvSinup.setBackgroundResource(R.mipmap.login_bg_sighin);
        agreeButton.setVisibility(View.GONE);
        tvForgetPassword.setVisibility(View.VISIBLE);
    }


    private void showRegisterView(){
        registerBtn.setVisibility(View.VISIBLE);
        findViewById(R.id.login).setVisibility(View.GONE);
        conPasswordEditText.setVisibility(View.VISIBLE);
        tvSinup.setBackgroundResource(R.mipmap.login_bg_sighup);
        agreeButton.setVisibility(View.VISIBLE);
        tvForgetPassword.setVisibility(View.GONE);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        MainActivity.actionStart(LoginActivity.this);
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
