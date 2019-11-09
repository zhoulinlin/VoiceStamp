package com.groupseven.voicestamp.login.data;

import com.groupseven.voicestamp.login.data.model.LoggedInUser;
import com.groupseven.voicestamp.tools.AppGlobal;
import com.groupseven.voicestamp.tools.SharedPreferencesUtil;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private LoggedInUser user = null;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return getUser() != null;
    }

    public LoggedInUser getUser() {
        if(user == null){
            user = SharedPreferencesUtil.getLoggedInUser(AppGlobal.getInstance().getApplicationContext());
        }
        return user;
    }

    public void logout() {
        user = null;
        dataSource.logout();
        SharedPreferencesUtil.clearLoggedInUser(AppGlobal.getInstance().getApplicationContext());
    }

    public void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        SharedPreferencesUtil.saveLoggedInUser(AppGlobal.getInstance().getApplicationContext(),user);
    }

    public void login(String username, String password,LoginCallback callback) {
        dataSource.login(username, password,callback);
    }

    public void register(String username, String password,LoginCallback callback) {
        dataSource.register(username, password,callback);
    }

    public void getAgreement(LoginCallback callback) {

        dataSource.getAgreement(callback);
    }
}
