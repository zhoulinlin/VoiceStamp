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
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

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

                        JSONObject requestBody = new JSONObject();

                        requestBody.put("id",username);
                        requestBody.put("password",password);
                        RequestBody body = RequestBody.create(requestBody.toString(), JSON);

                        String URL= "https://vs.hopeness.net/api/v1/login";

//                        https://vscdn.hopeness.net/agreement.json

                        Response response = null;

                        Log.e(TAG,"<<<<requestBody:"+requestBody.toString());

                        Log.e(TAG,"<<<<url:"+URL);
                        final Request request = new Request.Builder()
                                .url(URL)
                                .post(body).build();

                        Call call = client.newCall(request);
                        response = call.execute();

                        if(response!= null){
                            String bodyStr = response.body().string();

                            Log.e(TAG,"<<<<bodyStr:"+ bodyStr);

                            JSONObject json = new JSONObject(bodyStr);
                            Log.e(TAG,"<<<<response:"+ json.toString());
                            int code = json.optInt("code");
                            String errMessage = json.optString("message");

                            if(code == 0){
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
                                Log.e(TAG,"<<<<login failed msg:"+ errMessage);
                                callback.onHttpFinish(new Result.Error(code));
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
        try {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String URL= "https://vs.hopeness.net/api/v1/logout";
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(URL).build();
                        Response response = client.newCall(request).execute();

                        if(response!= null){
                            String bodyStr = response.body().string();
                            Log.e(TAG,"<<<<logout bodyStr:"+ bodyStr);
                        }
                    } catch (Exception e) {
                        Log.e(TAG,"<<<<logout Exception:"+ Log.getStackTraceString(e));
                    }
                }
            }).start();

        } catch (Exception e) {
            Log.e(TAG,"<<<<logout e="+Log.getStackTraceString(e));
        }

    }
}
