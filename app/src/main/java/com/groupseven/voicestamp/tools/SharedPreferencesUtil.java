package com.groupseven.voicestamp.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.groupseven.voicestamp.login.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SharedPreferences tools
 */
public class SharedPreferencesUtil {


    public static final String LOGIN_NAME = "loginName";

    public static final String LOGIN_ID_TYPE = "login_id_type";

    public static final String LOGIN_USERID = "loginUserId";

    public static final String LOGIN_UNIQUE = "loginUnique";

    public static final String SP_NAME = "voice_stamp";

    public static boolean isLogin(Context ctx){

        return getLoggedInUser(ctx) != null;
    }

    public static boolean saveLoggedInUser(Context ctx,LoggedInUser user){

        return setValue(ctx,SP_NAME,LOGIN_USERID,user.getUserId())
                && setValue(ctx,SP_NAME,LOGIN_NAME,user.getDisplayName())
                && setValue(ctx,SP_NAME,LOGIN_UNIQUE,user.getUniqueKey())
                && setValue(ctx,SP_NAME,LOGIN_ID_TYPE,user.getIdType());

    }

    public static LoggedInUser getLoggedInUser(Context ctx){

        String userId = (String)getValue(ctx,SP_NAME,LOGIN_USERID,"");

        if(TextUtils.isEmpty(userId)){
            return null;
        }
        String displayName = (String)getValue(ctx,SP_NAME,LOGIN_USERID,"");
        LoggedInUser user = new LoggedInUser(userId,displayName);
        user.setUniqueKey((String)getValue(ctx,SP_NAME,LOGIN_UNIQUE,""));
        user.setIdType((String)getValue(ctx,SP_NAME,LOGIN_ID_TYPE,""));

        return user;
    }


    public static boolean clearLoggedInUser(Context ctx){

        return setValue(ctx,SP_NAME,LOGIN_USERID,"")
                && setValue(ctx,SP_NAME,LOGIN_NAME,"")
                && setValue(ctx,SP_NAME,LOGIN_UNIQUE,"")
                && setValue(ctx,SP_NAME,LOGIN_ID_TYPE,"");
    }




    public static boolean setValue(Context ctx, String spName, String key, Object value) {
        boolean status = false;
        SharedPreferences spf = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String type = value.getClass().getSimpleName();
        SharedPreferences.Editor editor = spf.edit();
        if (spf != null) {
            if (type.equals("String")) {
                editor.putString(key, (String) value);
            } else if (type.equals("Integer")) {
                editor.putInt(key, (Integer) value);
            } else if (type.equals("Boolean")) {
                editor.putBoolean(key, (Boolean) value);
            } else if (type.equals("Long")) {
                editor.putLong(key, (Long) value);
            } else if (type.equals("Float")) {
                editor.putFloat(key, (Float) value);
            }
            status = editor.commit();
        }
        return status;
    }




    public static Object getValue(Context ctx, String spName, String key, Object defValue) {
        SharedPreferences spf = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String type = defValue.getClass().getSimpleName();// 获取数据类型
        if (spf != null) {
            if (type.equals("String")) {
                return spf.getString(key, (String) defValue);
            } else if (type.equals("Integer")) {
                return spf.getInt(key, (Integer) defValue);
            } else if (type.equals("Boolean")) {
                return spf.getBoolean(key, (Boolean) defValue);
            } else if (type.equals("Long")) {
                return spf.getLong(key, (Long) defValue);
            } else if (type.equals("Float")) {
                return spf.getFloat(key, (Float) defValue);
            }
        }
        return null;
    }



}
