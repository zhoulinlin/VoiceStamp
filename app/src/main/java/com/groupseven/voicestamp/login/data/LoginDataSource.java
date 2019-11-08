package com.groupseven.voicestamp.login.data;

import android.util.Log;

import com.groupseven.voicestamp.login.data.model.LoggedInUser;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    final String TAG = this.getClass().getName();

    public void login(final String username,final String password,final LoginCallback callback){


        if(callback == null){
            return;
        }

        try {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("username",username)
                                .add("password",password).build();
                        String URL= "https://vs.hopeness.net/api/v1/login";

//                        https://vscdn.hopeness.net/agreement.json

                        Response response = null;

                        Log.e(TAG,"<<<<url:"+URL);
                        final Request request = new Request.Builder()
                                .url(URL)
                                .post(body).build();

                        Call call = client.newCall(request);
                        response = call.execute();

                        if(response!= null){
                            Log.e(TAG,"<<<<response:"+ response.toString());
                            JSONObject json = new JSONObject(response.body().toString());
                            int code = json.optInt("code");
                            String errMessage = json.optString("message");

                            if(code == 1){
                                String data = json.optString("data");
                                JSONObject dataoj = new JSONObject(data);
                                String uk = dataoj.optString("uk");
                                String id = dataoj.optString("id");
                                String idType = dataoj.optString("id_type");
                                String nickname = dataoj.optString("nickname");

                                LoggedInUser fakeUser = new LoggedInUser(id, nickname);
                                fakeUser.setIdType(idType);
                                fakeUser.setUniqueKey(uk);
                                callback.onHttpFinish(new Result.Success<>(fakeUser));

                            }else{
                                callback.onHttpFinish(new Result.Error(errMessage));
                            }

                            Log.e(TAG,"<<<<e="+response.toString());
                        }
                    } catch (Exception e) {
                        callback.onHttpFinish(new Result.Error(e));
                        e.printStackTrace();
                    }

                }
            }).start();

        } catch (Exception e) {
            Log.e(TAG,"<<<<e="+Log.getStackTraceString(e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
