package com.groupseven.voicestamp.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.groupseven.voicestamp.R;


/**
 * test 公共的dialog样式
 */
public abstract class ABaseDialog extends Dialog implements View.OnClickListener {

    private LinearLayout titleLayout;

    public TextView tvTitle;

    private FrameLayout mContent;

    private TextView btn1;

    private TextView btn2;

    private TextView btn3;

    private TextView btn4;

    private View.OnClickListener mBtn1ClickListener;
    private View.OnClickListener mBtn2ClickListener;
    private View.OnClickListener mBtn3ClickListener;
    private View.OnClickListener mBtn4ClickListener;

    private boolean isAutoDismiss1 = true;

    private boolean isAutoDismiss2 = true;

    private boolean isAutoDismiss3 = true;

    private boolean isAutoDismiss4 = true;

    private View vertical_line1, vertical_line2;

    public ABaseDialog(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
    }

    public void init() {

        this.getContext().setTheme(R.style.AlertDialogStyle);
        super.setContentView(R.layout.common_base_dialog);

        titleLayout = (LinearLayout) findViewById(R.id.linearlayout_head_title);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mContent = (FrameLayout) findViewById(R.id.fl_content);

        btn1 = (TextView) findViewById(R.id.btn1);
        btn2 = (TextView) findViewById(R.id.btn2);
        btn3 = (TextView) findViewById(R.id.btn3);
        btn4 = (TextView) findViewById(R.id.btn4);

        vertical_line1 = findViewById(R.id.vertical_line1);
        vertical_line2 = findViewById(R.id.vertical_line2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.4f;
        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.8f);
        window.setLayout(width, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置dialog的标题
     * 
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置dialog的标题
     * 
     * @param resId
     * @see Dialog#setTitle(int)
     */
    public void setTitle(int resId) {
        setTitle(getContext().getResources().getString(resId));
    }


    public abstract View createContentView();

    public void setContentView(View view) {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        mContent.addView(view);
    }

    /**
     * 设置按钮1的文字
     * 
     * @param text
     */
    public void setBtn1Text(String text) {
        btn1.setText(text);
    }

    /**
     * 设置按钮2的文字
     * 
     * @param text
     */
    public void setBtn2Text(String text) {
        btn2.setText(text);
    }

    /**
     * 设置按钮3的文字
     * 
     * @param text
     */
    public void setBtn3Text(String text) {
        btn3.setText(text);
    }

    /**
     * 设置按钮4的文字
     * 
     * @param text
     */
    public void setBtn4Text(String text) {
        btn4.setText(text);
    }

    /**
     * 设置按钮1的文字
     * 
     * @param resId
     */
    public void setBtn1Text(int resId) {
        btn1.setText(resId);
    }

    /**
     * 设置按钮2的文字
     * 
     * @param resId
     */
    public void setBtn2Text(int resId) {
        btn2.setText(resId);
    }

    /**
     * 设置按钮3的文字
     * 
     * @param resId
     */
    public void setBtn3Text(int resId) {
        btn3.setText(resId);
    }

    /**
     * 设置按钮4的文字
     * 
     * @param resId
     */
    public void setBtn4Text(int resId) {
        btn4.setText(resId);
    }

    /**
     * 设置点击按钮1后时候是否关闭dialog 默认为true
     * 
     * @param autoDismiss
     */
    public void setAutoDismiss1(boolean autoDismiss) {
        isAutoDismiss1 = autoDismiss;
    }

    /**
     * 设置点击按钮2后时候是否关闭dialog 默认为true
     * 
     * @param autoDismiss
     */
    public void setAutoDismiss2(boolean autoDismiss) {
        isAutoDismiss2 = autoDismiss;
    }

    /**
     * 设置点击按钮3后时候是否关闭dialog 默认为true
     * 
     * @param autoDismiss
     */
    public void setAutoDismiss3(boolean autoDismiss) {
        isAutoDismiss3 = autoDismiss;
    }

    /**
     * 设置点击按钮4后时候是否关闭dialog 默认为true
     * 
     * @param autoDismiss
     */
    public void setAutoDismiss4(boolean autoDismiss) {
        isAutoDismiss4 = autoDismiss;
    }

    /**
     * 设置按钮1是否可见
     * 
     * @param visible
     */
    public void setBtn1Visible(boolean visible) {
        if (visible) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.GONE);
        }
    }

    /**
     * 设置按钮2是否可见
     * 
     * @param visible
     */
    public void setBtn2Visible(boolean visible) {
        if (visible) {
            btn2.setVisibility(View.VISIBLE);
        } else {
            btn2.setVisibility(View.GONE);
        }
    }

    /**
     * 设置按钮3是否可见
     * 
     * @param visible
     */
    public void setBtn3Visible(boolean visible) {
        if (visible) {
            btn3.setVisibility(View.VISIBLE);
        } else {
            btn3.setVisibility(View.GONE);
        }
    }

    /**
     * 设置按钮4是否可见
     * 
     * @param visible
     */
    public void setBtn4Visible(boolean visible) {
        if (visible) {
            btn4.setVisibility(View.VISIBLE);
        } else {
            btn4.setVisibility(View.GONE);
        }
    }


    public void setTitleEnable(boolean bool) {
        if (bool) {
            setVisibilitySafe(titleLayout, View.VISIBLE);
            setVisibilitySafe(tvTitle, View.VISIBLE);
        } else {
            setVisibilitySafe(titleLayout, View.GONE);
            setVisibilitySafe(tvTitle, View.GONE);
        }
    }
    /**
     * 设置按钮1是否处于可点击状态
     * 
     * @paramenable
     */
    public void setBtn1Enable(boolean enabled) {
        btn1.setEnabled(enabled);
    }

    /**
     * 设置按钮2是否处于可点击状态
     * 
     * @paramenable
     */
    public void setBtn2Enable(boolean enabled) {
        btn2.setEnabled(enabled);
    }

    /**
     * 设置按钮3是否处于可点击状态
     * 
     * @paramenable
     */
    public void setBtn3Enable(boolean enabled) {
        btn3.setEnabled(enabled);
    }

    /**
     * 设置按钮4是否处于可点击状态
     * 
     * @paramenable
     */
    public void setBtn4Enable(boolean enabled) {
        btn4.setEnabled(enabled);
    }

    /**
     * @param listener
     */
    public void setBtn1ClickListener(View.OnClickListener listener) {
        mBtn1ClickListener = listener;
    }

    /**
     * @param listener
     */
    public void setBtn2ClickListener(View.OnClickListener listener) {
        mBtn2ClickListener = listener;
    }

    /**
     * @param listener
     */
    public void setBtn3ClickListener(View.OnClickListener listener) {
        mBtn3ClickListener = listener;
    }

    /**
     * @param listener
     */
    public void setBtn4ClickListener(View.OnClickListener listener) {
        mBtn4ClickListener = listener;
    }

    /**
     * @param color
     */
    public void setBtn1TextColor(int color) {
        btn1.setTextColor(color);
    }

    public void setBtn1Background(int resid) {
        btn1.setBackgroundResource(resid);
    }

    /**
     * @param color
     */
    public void setBtn2TextColor(int color) {
        btn2.setTextColor(color);
    }

    public void setBtn4TextColor(int color) {
        btn4.setTextColor(color);
    }

    /**
     * @param resid
     */
    public void setBtn2Background(int resid) {
        btn2.setBackgroundResource(resid);
    }

    public void setVerticalLine1Visible(boolean visible) {
        if (visible) {
            setVisibilitySafe(vertical_line1, View.VISIBLE);
        } else {
            setVisibilitySafe(vertical_line1, View.GONE);
        }
    }

    public void setVerticalLine2Visible(boolean visible) {
        if (visible) {
            setVisibilitySafe(vertical_line2, View.VISIBLE);
        } else {
            setVisibilitySafe(vertical_line2, View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                if (mBtn1ClickListener != null) {
                    mBtn1ClickListener.onClick(v);
                }
                if (isAutoDismiss1) {
                    dismiss();
                }
                break;
            case R.id.btn2:
                if (mBtn2ClickListener != null) {
                    mBtn2ClickListener.onClick(v);
                }
                if (isAutoDismiss2) {
                    dismiss();
                }
                break;
            case R.id.btn3:
                if (mBtn3ClickListener != null) {
                    mBtn3ClickListener.onClick(v);
                }
                if (isAutoDismiss3) {
                    dismiss();
                }
                break;
            case R.id.btn4:
                if (mBtn4ClickListener != null) {
                    mBtn4ClickListener.onClick(v);
                }
                if (isAutoDismiss4) {
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    public static void setVisibilitySafe(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

}
