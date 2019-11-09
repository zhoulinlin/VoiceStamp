package com.groupseven.voicestamp.login.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.login.data.LoginCallback;
import com.groupseven.voicestamp.login.data.LoginRepository;
import com.groupseven.voicestamp.login.data.Result;
import com.groupseven.voicestamp.login.data.model.LoggedInUser;

import java.util.logging.Handler;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {

        // can be launched in a separate asynchronous job
        LoginCallback callback = new LoginCallback() {
            @Override
            public void onHttpFinish(Result result) {
                if (result instanceof Result.Success) {
                    LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                    loginResult.postValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                    loginRepository.setLoggedInUser(data);
                } else if(result instanceof Result.Error){
//                    Integer errorMsg = ((Result.Error) result).getErrorMsg();
//                    loginResult.postValue(new LoginResult(errorMsg));
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                }else{
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                }
            }
        };

       loginRepository.login(username, password,callback);
    }


    public void logout() {
        loginRepository.logout();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
