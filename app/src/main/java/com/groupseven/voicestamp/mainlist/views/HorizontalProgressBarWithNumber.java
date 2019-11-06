package com.groupseven.voicestamp.mainlist.views;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.groupseven.voicestamp.R;

public class HorizontalProgressBarWithNumber extends ProgressBar
{

    /**
     * 设置各种默认值
     */
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;


    /**
     * painter of all drawing things  所有画图所用的画笔
     */
    protected Paint mPaint = new Paint();
    /**
     * color of progress number  进度号码的颜色
     */
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * size of text (sp)  文本的大小
     */
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);

    /**
     * offset of draw progress  进度条文本补偿宽度
     */
    protected int mTextOffset = dp2px(DEFAULT_SIZE_TEXT_OFFSET);

    /**
     * height of reached progress bar  进度条高度
     */
    protected int mReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);

    /**
     * color of reached bar   成功的文本颜色
     */
    protected int mReachedBarColor = DEFAULT_TEXT_COLOR;
    /**
     * color of unreached bar 未完成的bar颜色
     */
    protected int mUnReachedBarColor = DEFAULT_COLOR_UNREACHED_COLOR;
    /**
     * height of unreached progress bar  未覆盖的进度条高度
     */
    protected int mUnReachedProgressBarHeight = dp2px(DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
    /**
     * view width except padding  除padding外的视图宽度
     */
    protected int mRealWidth;

    protected boolean mIfDrawText = true;

    protected static final int VISIBLE = 0;

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs,
                                           int defStyle)
    {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);//初始化参数
        mPaint.setTextSize(mTextSize);//文本大小
        mPaint.setColor(mTextColor);//文本颜色
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec)
    {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);//高度
        setMeasuredDimension(width, height);//必须调用该方法来存储View经过测量的到的宽度和高度

        mRealWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();//真正的宽度值是减去左右padding
    }


    /**
     *  EXACTLY：父控件告诉我们子控件了一个确定的大小，你就按这个大小来布局。比如我们指定了确定的dp值和macth_parent的情况。
     *  AT_MOST：当前控件不能超过一个固定的最大值，一般是wrap_content的情况。
     *  UNSPECIFIED:当前控件没有限制，要多大就有多大，这种情况很少出现。
     * @param measureSpec
     * @return  视图的高度
     */
    private int measureHeight(int measureSpec)
    {

        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);//父布局告诉我们控件的类型
        int specSize = MeasureSpec.getSize(measureSpec);//父布局传过来的视图大小
        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        } else
        {
            /**
             * mPaint.descent() 最高点的高度
             * mPaint.ascent() 最低点的高度
             */
            float textHeight = (mPaint.descent() - mPaint.ascent());// 设置文本的高度
            /**
             * Math.abs() 返回绝对值
             *  Math.max 返回最大值
             *  Math.min 返回最小值
             */
            result = (int) (getPaddingTop() + getPaddingBottom() + Math.max(
                    Math.max(mReachedProgressBarHeight,
                            mUnReachedProgressBarHeight), Math.abs(textHeight)));
            if (specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * get the styled attributes  获取属性的样式
     *
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs)
    {
        // init values from custom attributes
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.HorizontalProgressBarWithNumber);

        mTextColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_text_color,
                        DEFAULT_TEXT_COLOR);
        mTextSize = (int) attributes.getDimension(
                R.styleable.HorizontalProgressBarWithNumber_progress_text_size,
                mTextSize);

        mReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_reached_color,
                        mTextColor);
        mUnReachedBarColor = attributes
                .getColor(
                        R.styleable.HorizontalProgressBarWithNumber_progress_unreached_color,
                        DEFAULT_COLOR_UNREACHED_COLOR);
        mReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_reached_bar_height,
                        mReachedProgressBarHeight);
        mUnReachedProgressBarHeight = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_unreached_bar_height,
                        mUnReachedProgressBarHeight);
        mTextOffset = (int) attributes
                .getDimension(
                        R.styleable.HorizontalProgressBarWithNumber_progress_text_offset,
                        mTextOffset);

        int textVisible = attributes
                .getInt(R.styleable.HorizontalProgressBarWithNumber_progress_text_visibility,
                        VISIBLE);
        if (textVisible != VISIBLE)
        {
            mIfDrawText = false;
        }
        attributes.recycle();
    }


    /**
     * 开始画
     */
    @Override
    protected synchronized void onDraw(Canvas canvas)
    {

        canvas.save();
        /**
         * 设置偏移后的坐标原点 以原来为基础上偏移后， 例如： (100,100), translate(1,1), 坐标原点(101,101);
         */
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean noNeedBg = false;
        float radio = getProgress() * 1.0f / getMax();//设置进度
        float progressPosX = (int) (mRealWidth * radio);//设置当前进度的宽度
        String text = getProgress() + "%";//设置文本
//       mPaint.getTextBounds(text, 0, text.length(), mTextBound);

        float textWidth = mPaint.measureText(text);//返回文本的宽度
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;//设置文本的高度

        if (progressPosX + textWidth > mRealWidth)
        {//当文本和当前进度的宽度大于bar的宽度时
            progressPosX = mRealWidth - textWidth;
            noNeedBg = true;
        }

        // draw reached bar   画出bar
        float endX = progressPosX - mTextOffset / 2;//设置文本补偿宽度
        if (endX > 0)
        {
            mPaint.setColor(mReachedBarColor);
            mPaint.setStrokeWidth(mReachedProgressBarHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        // draw progress bar
        // measure text bound
        if (mIfDrawText)
        {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressPosX, -textHeight, mPaint);
        }

        // draw unreached bar
        if (!noNeedBg)
        {
            float start = progressPosX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnReachedBarColor);
            mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();

    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
    }

}
