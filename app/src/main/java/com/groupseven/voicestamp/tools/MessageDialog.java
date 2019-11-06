package com.groupseven.voicestamp.tools;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupseven.voicestamp.R;


public class MessageDialog extends ABaseDialog {

    private boolean canNotCancel;

    private TextView tvMessage;

    private View contentView;

    private EditText editText;

    private boolean isBack = true;

    public MessageDialog(Context context) {
        super(context);
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.common_message_dialog, null);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        editText = (EditText) contentView.findViewById(R.id.edittext);
    }

    public MessageDialog(Context context, boolean canNotCancel) {
        super(context);
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.common_message_dialog, null);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        editText = (EditText) contentView.findViewById(R.id.edittext);
        this.canNotCancel = canNotCancel;
    }

    @Override
    public View createContentView() {
        return contentView;
    }

    public void setMessage(String text) {
        tvMessage.setText(text);
    }

    public void setCanNotCancel(boolean canNotCancel) {
        this.canNotCancel = canNotCancel;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH) {
            if (canNotCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setOneBtnStyle() {
        setBtn1Visible(false);
        setBtn2Visible(false);
        setBtn3Visible(false);
        setBtn4Visible(true);
    }

    public void setTwoBtnStyle() {
        setBtn1Visible(true);
        setBtn2Visible(true);
        setBtn3Visible(false);
        setBtn4Visible(false);
        setVerticalLine1Visible(true);
    }

    public void setThreeBtnStyle() {
        setBtn1Visible(true);
        setBtn2Visible(true);
        setBtn3Visible(true);
        setBtn4Visible(false);
        setVerticalLine1Visible(true);
        setVerticalLine2Visible(true);
    }

    public void setOneBtnClickListener(View.OnClickListener onClickListener) {
        setBtn4ClickListener(onClickListener);
    }

    public void setEditTextVisible(boolean visble) {
        if (visble) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
    }

    public void setEditPassword(boolean is) {
        if (is) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public void setTitle(int rsid) {
        super.setTitle(rsid);
    }

    public void setEditTextLength(int length) {
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(length) });
    }

    public EditText getEditText() {
        return editText;
    }

}
