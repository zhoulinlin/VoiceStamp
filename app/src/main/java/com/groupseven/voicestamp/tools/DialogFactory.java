package com.groupseven.voicestamp.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;



public class DialogFactory {
    private static final String TAG = DialogFactory.class.getSimpleName();

    private static MessageDialog mMessageDialog;

    private static MessageDialog warnDialog;

    private static MessageDialog permissionWarnDialog;

    /**
     * 确认框
     */
    public static void showConfirmDialog(Context ctx, OnClickListener neutralListener,
                                         OnClickListener negativeListener, String message, String neutralText, String negativeText) {
        MessageDialog msgDialog = new MessageDialog(ctx, true);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(neutralListener);
        msgDialog.setBtn1Text(neutralText);
       
        msgDialog.setBtn2ClickListener(negativeListener);
        msgDialog.setBtn2Text(negativeText);

        msgDialog.show();

    }

    public static Dialog showContentDialog(Context ctx, OnClickListener neutralListener,
                                           OnClickListener negativeListener, String message, String neutralText, String negativeText) {
        MessageDialog msgDialog = new MessageDialog(ctx, true);

        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(neutralListener);
        msgDialog.setBtn1Text(neutralText);

        msgDialog.setBtn2ClickListener(negativeListener);
        msgDialog.setBtn2Text(negativeText);

        msgDialog.setBtn3Visible(false);

        msgDialog.setBtn4Visible(false);

        msgDialog.show();

        return msgDialog;
    }

    /**
     * @param context
     * @param str
     */
    public static MessageDialog warningDialog(Context context, String str) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        if(warnDialog == null) {
            warnDialog = new MessageDialog(context);
        }
        warnDialog.setOneBtnStyle();
        warnDialog.setMessage(str);
        warnDialog.show();
        return warnDialog;
    }


    /**
     * 存储权限  没开启  提示的dialog
     * @param context 上下文
     * @param message 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static MessageDialog showPermissionWarningDialog(Context context, String title, String message, String sureString,
                                                            String cancelString, OnClickListener surelistener, OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        if(permissionWarnDialog == null) {
            permissionWarnDialog = new MessageDialog(context);
        }
        permissionWarnDialog.setTwoBtnStyle();
        permissionWarnDialog.setMessage(message);
        permissionWarnDialog.setBtn1ClickListener(cancellistener);
        permissionWarnDialog.setBtn2ClickListener(surelistener);
        permissionWarnDialog.setBtn1Text(cancelString);
        permissionWarnDialog.setBtn2Text(sureString);
        permissionWarnDialog.show();
        return permissionWarnDialog;
    }


    public static MessageDialog getPermissionDialog() {
        return permissionWarnDialog;
    }

    /**
     * @param context
     * @param id
     */
    public static MessageDialog warningDialog(Context context, int id) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setMessage(context.getResources().getString(id));
        msgDialog.show();
        return msgDialog;
    }

    /**
     * @param context 上下文
     * @param str 提示信息
     * @param sureString 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static MessageDialog warningDialog(Context context, String str, String sureString,
                                              OnClickListener surelistener) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setMessage(str);
        msgDialog.setBtn4Text(sureString);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }



    public static MessageDialog warningDialog(Context context, String str, String sureString,
                                              OnClickListener surelistener, boolean Cancelable) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setCancelable(Cancelable);
        msgDialog.setMessage(str);
        msgDialog.setBtn4Text(sureString);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }


    /**
     * @param context 上下文
     * @param strId 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static MessageDialog warningDialog(Context context, int strId, int sureStringId, OnClickListener surelistener) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setMessage(context.getResources().getString(strId));
        msgDialog.setBtn4Text(sureStringId);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }

    /**
     * @param context 上下文
     * @param str 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static MessageDialog warningDialog(Context context, String str, int sureStringId,
                                              OnClickListener surelistener) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setMessage(str);
        msgDialog.setBtn4Text(sureStringId);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }




    /**
     * @param context 上下文
     * @param message 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, String message, int sureStringId, OnClickListener surelistener) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(String.valueOf(sureStringId))) {
            msgDialog.setBtn2Text(sureStringId);
        }
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param message 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, String message, int sureStringId, OnClickListener surelistener,
                                    OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(String.valueOf(sureStringId))) {
            msgDialog.setBtn2Text(sureStringId);
        }
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param message 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, String message, String sureString, String cancelString,
                                    OnClickListener surelistener, OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn1Text(cancelString);
        msgDialog.setBtn2Text(sureString);
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @parammessage 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, int messageId, int sureString, int cancelString,
                                    OnClickListener surelistener, OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(messageId));
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn1Text(cancelString);
        msgDialog.setBtn2Text(sureString);
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param message 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, String message, int sureString, int cancelString,
                                    OnClickListener surelistener, OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn1Text(cancelString);
        msgDialog.setBtn2Text(sureString);
        msgDialog.show();
    }


   /**
    * 带有标题的弹出框
    */
    public static void chooseDialog(Context context, String title, String message, String sureString,
                                    String cancelString, OnClickListener surelistener, OnClickListener cancellistener, boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setTitle(title);
        msgDialog.setTitleEnable(true);
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn1Text(cancelString);
        msgDialog.setBtn2Text(sureString);
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param message 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static MessageDialog chooseDialog(Context context, String message, String sureString, String cancelString,
                                             OnClickListener surelistener, OnClickListener cancellistener) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn1ClickListener(cancellistener);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn1Text(cancelString);
        msgDialog.setBtn2Text(sureString);
        return msgDialog;
    }

    /**
     * @param context 上下文
     * @param message 提示信息
     * @paramsureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, String message, String sureString, OnClickListener surelistener,
                                    boolean Cancelable) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setCancelable(Cancelable);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn2ClickListener(surelistener);
        msgDialog.setBtn2Text(sureString);
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param messageId 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, int messageId, int sureStringId, OnClickListener surelistener) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(messageId));
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(String.valueOf(sureStringId))) {
            msgDialog.setBtn2Text(sureStringId);
        }
        msgDialog.show();
    }

    /**
     * @param context 上下文
     * @param messageId 提示信息
     * @param sureString 确定按钮文字
     * @param surelistener 确定按钮监听事件
     */
    public static void chooseDialog(Context context, int messageId, String sureString, OnClickListener surelistener) {
        if (null == context) {
            return;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(messageId));
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
    }

    public static MessageDialog editDiaglog(Context context, int messageId, String sureString,
                                            OnClickListener surelistener, int title) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        msgDialog.setEditPassword(true);
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(messageId));
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }

    /**
     * 带EditText Dialog
     * 
     * @param context
     * @param messageId 提示内容
     * @param sureString 按钮内容
     * @param surelistener 按钮事件
     * @param title 标题
     * @param isPassword EditText是否为密码
     * @return
     */
    public static MessageDialog editDiaglog(Context context, int messageId, String sureString,
                                            OnClickListener surelistener, int title, boolean isPassword) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        if (isPassword) {
            msgDialog.setEditPassword(true);
        } else {
            msgDialog.setEditPassword(false);
        }
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(messageId));
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }

    /**
     * 带EditText Dialog
     * 
     * @param context
     * @param message 提示内容
     * @param sureString 按钮内容
     * @param surelistener 按钮事件
     * @param title 标题
     * @param isPassword EditText是否为密码
     * @return
     */
    public static MessageDialog editDiaglog(Context context, String message, String sureString,
                                            OnClickListener surelistener, String title, boolean isPassword) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        if (isPassword) {
            msgDialog.setEditPassword(true);
        } else {
            msgDialog.setEditPassword(false);
        }
        msgDialog.setTitle(title);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }

    /**
     * 带EditText Dialog
     * 
     * @param context
     * @param message 提示内容
     * @param sureString 按钮内容
     * @param surelistener 按钮事件
     * @param title 标题
     * @param isPassword EditText是否为密码
     * @return
     */
    public static MessageDialog editDiaglog(Context context, String message, String sureString,
                                            OnClickListener surelistener, String title, boolean isPassword, int length) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        if (isPassword) {
            msgDialog.setEditPassword(true);
        } else {
            msgDialog.setEditPassword(false);
        }
        msgDialog.setTitle(title);
        msgDialog.setTitleEnable(true);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.setEditTextLength(length);
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }

    /**
     * @param context 上下文
     * @param strId 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     * @param title 标题
     */
    public static MessageDialog warningDialog(Context context, int strId, int sureStringId,
                                              OnClickListener surelistener, int title) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTitleEnable(true);
        msgDialog.setMessage(context.getResources().getString(strId));
        msgDialog.setBtn4Text(sureStringId);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }
    /**
     * 不能通过物理返回键取消的弹出框
     * @param context
     * @param strId
     * @param sureStringId
     * @param surelistener
     * @param title
     * @return
     * @author tanzhao
     * @time 2015-5-19 下午7:38:21
     */
    public static MessageDialog warningNotCancelDialog(Context context, int strId, int sureStringId,
                                                       OnClickListener surelistener, int title) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTitleEnable(true);
        msgDialog.setMessage(context.getResources().getString(strId));
        msgDialog.setBtn4Text(sureStringId);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.setCanNotCancel(true);
        msgDialog.show();
        return msgDialog;
    }
    
    
    /**
     * @param context 上下文
     * @paramstrId 提示信息
     * @param sureStringId 确定按钮文字
     * @param surelistener 确定按钮监听事件
     * @param title 标题
     */
    public static MessageDialog warningDialog(Context context, String string, int sureStringId,
                                              OnClickListener surelistener, int title) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setOneBtnStyle();
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTitleEnable(true);
        msgDialog.setMessage(string);
        msgDialog.setBtn4Text(sureStringId);
        msgDialog.setBtn4ClickListener(surelistener);
        msgDialog.show();
        return msgDialog;
    }

    public static void dismissLoadingDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 带EditText Dialog(测试用)
     * 
     * @param context
     * @param message 提示内容
     * @param sureString 按钮内容
     * @param surelistener 按钮事件
     * @param title 标题
     * @param editTextContnt 编辑内容
     * @return
     */
    public static MessageDialog editDiaglog(Context context, int message, String sureString,
                                            OnClickListener surelistener, int title, String editTextContnt) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        msgDialog.setEditPassword(false);
        msgDialog.getEditText().setText(editTextContnt);
        msgDialog.setTitle(context.getResources().getString(title));
        msgDialog.setTitleEnable(true);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(context.getResources().getString(message));
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }

    /**
     * 带EditText Dialog(测试用)
     * 
     * @param context
     * @param message 提示内容
     * @param sureString 按钮内容
     * @param surelistener 按钮事件
     * @param title 标题
     * @param isPassword EditText是否为密码
     * @param editTextContnt 编辑内容
     * @return
     */
    public static MessageDialog editDiaglog(Context context, String message, String sureString,
                                            OnClickListener surelistener, String title, boolean isPassword, int length, int editTextContnt) {
        if (null == context) {
            return null;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return null;
            }
        }
        MessageDialog msgDialog = new MessageDialog(context);
        msgDialog.setEditTextVisible(true);
        if (isPassword) {
            msgDialog.setEditPassword(true);
        } else {
            msgDialog.setEditPassword(false);
        }
        msgDialog.setTitle(title);
        msgDialog.setTitleEnable(true);
        msgDialog.setTwoBtnStyle();
        msgDialog.setMessage(message);
        msgDialog.getEditText().setContentDescription((context.getResources().getString(editTextContnt)));
        msgDialog.setEditTextLength(length);
        msgDialog.setBtn2ClickListener(surelistener);
        if (!TextUtils.isEmpty(sureString)) {
            msgDialog.setBtn2Text(sureString);
        }
        msgDialog.show();
        return msgDialog;
    }



}
