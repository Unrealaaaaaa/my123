package com.wyz.my123;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


@SuppressLint("AppCompatCustomView")
public class ProgressButton extends Button {


    private int i = 1,m,n,a;
    private static final String TAG = "Unreal";
    private int mProgress; //当前进度
    private int mMaxProgress = 100; //最大进度：默认为100
    private int mMinProgress = 0;//最小进度：默认为0
    private GradientDrawable mProgressDrawable;// 加载进度时的进度颜色
    private GradientDrawable mProgressDrawableBg;// 加载进度时的背景色
    private StateListDrawable mNormalDrawable; // 按钮在不同状态的颜色效果
    private boolean isShowProgress;  //是否展示进度
    private boolean isFinish; // 结束状态
    private boolean isStop;// 停止状态
    private boolean isStart ; // 刚开始的状态
    private OnStateListener onStateListener; //结束时的监听
    private float cornerRadius; // 圆角半径



    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attributeSet) {

        // 初始化按钮状态Drawable
        mNormalDrawable = new StateListDrawable();
        // 初始化进度条Drawable
        mProgressDrawable = (GradientDrawable)getResources().getDrawable(
                R.drawable.shape).mutate();
        // 初始化进度条背景Drawable
        mProgressDrawableBg = (GradientDrawable)getResources().getDrawable(
                R.drawable.shape7).mutate();

        TypedArray attr =  context.obtainStyledAttributes(attributeSet, R.styleable.progressbutton);

        try {

            // 默认的圆角大小
            float defValue = getResources().getDimension(androidx.cardview.R.dimen.cardview_compat_inset_shadow);
            // 获取圆角大小
            cornerRadius = attr.getDimension(R.styleable.progressbutton_buttonCornerRadius, defValue);


            // 获取是否显示进度信息的属性
            isShowProgress = attr.getBoolean(R.styleable.progressbutton_showProgressNum,false);

            // 给按钮的状态Drawable添加被点击时的状态
            mNormalDrawable.addState(new int[]{android.R.attr.state_pressed},
                    getPressedDrawable(attr));
            // 给按钮的状态Drawable添加其他时候的状态
            mNormalDrawable.addState(new int[] { }, getNormalDrawable(attr));


            // 获取进度条颜色属性值
            int defaultProgressColor = getResources().getColor(R.color.color_02);
            int progressColor = attr.getColor(R.styleable.progressbutton_progressColor,defaultProgressColor);
            // 设置进度条Drawable的颜色
            mProgressDrawable.setColor(progressColor);

            // 获取进度条背景颜色属性值
            int defaultProgressBgColor = getResources().getColor(R.color.color_01);
            int progressBgColor = attr.getColor(R.styleable.progressbutton_progressBgColor,defaultProgressBgColor);
            // 设置进度条背景Drawable的颜色
            mProgressDrawableBg.setColor(progressBgColor);



        } finally {
            attr.recycle();
        }

        // 初始化状态
        isFinish = false;
        isStop = true;
        isStart = false;

        // 设置圆角
        mProgressDrawable.setCornerRadius(cornerRadius);
        mProgressDrawableBg.setCornerRadius(cornerRadius);
        // 设置按钮背景为状态Drawable
        setBackgroundCompat(mNormalDrawable);
    }



    @Override
    protected void onDraw(Canvas canvas) {

        if (mProgress > mMinProgress && mProgress <= mMaxProgress && !isFinish) {
            // 更新进度：
            float scale = (float) getProgress() / (float) mMaxProgress;
            float indicatorWidth = (float) getMeasuredWidth() * scale;

            mProgressDrawable.setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
            Log.e(TAG, "onDraw: 11111");
            mProgressDrawable.draw(canvas);

            if (i == 2 ){
                mProgress = 0;
                i = 1;
                Log.e(TAG, "onDraw: 11111");
            }

            // 进度完成时回调方法，并更变状态
            if(mProgress==mMaxProgress) {
                setBackgroundCompat(mProgressDrawable);
                isFinish = true;
                if(onStateListener!=null) {
                    onStateListener.onFinish();
                }
            }
        }
        super.onDraw(canvas);
    }

    // 设置进度信息
    public void setProgress(int progress) {
        if(!isFinish&&!isStop){
            mProgress = progress;
            if(isShowProgress) setText(mProgress + " %");
            // 设置背景
            setBackgroundCompat(mProgressDrawableBg);
            invalidate();
        }
    }

    // 获取进度
    public int getProgress() {
        return mProgress;
    }

    // 设置为停止状态
    public void setStop(boolean stop) {
        isStop = stop;
        invalidate();
    }


    public boolean isStop() {
        return isStop;
    }

    public boolean isFinish() {
        return isFinish;
    }

    // 切换状态：
    public void toggle(){
        if(!isFinish&&isStart){
            if(isStop){
                setStop(false);
                onStateListener.onContinue();
            } else {
                setStop(true);
                onStateListener.onStop();
            }
        }else {
            setStop(false);
            isStart = true;
        }
    }

    // 设置按钮背景
    private void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }

    // 初始化状态
    public void initState(){
        setBackgroundCompat(mNormalDrawable);
        isFinish = false;
        isStop = true;
        isStart = false;
        mProgress = 0;
    }

    // 获取状态Drawable的正常状态下的背景
    private Drawable getNormalDrawable( TypedArray attr) {
        GradientDrawable drawableNormal =
                (GradientDrawable) getResources().getDrawable(R.drawable.shape8).mutate();// 修改时就不会影响其它drawable对象的状态
        drawableNormal.setCornerRadius(cornerRadius); // 设置圆角半径

        int defaultNormal =  getResources().getColor(R.color.color_01);
        int colorNormal =  attr.getColor(R.styleable.progressbutton_buttonNormalColor,defaultNormal);
        drawableNormal.setColor(colorNormal);//设置颜色

        return drawableNormal;
    }

    // 获取按钮被点击时的Drawable
    private Drawable getPressedDrawable( TypedArray attr) {
        GradientDrawable drawablePressed =
                (GradientDrawable) getResources().getDrawable(R.drawable.shape9).mutate();// 修改时就不会影响其它drawable对象的状态
        drawablePressed.setCornerRadius(cornerRadius);// 设置圆角半径

        int defaultPressed = getResources().getColor(R.color.color_01);
        int colorPressed = attr.getColor(R.styleable.progressbutton_buttonPressedColor,defaultPressed);
        drawablePressed.setColor(colorPressed);//设置颜色

        return drawablePressed;
    }

    // 设置状态监听接口
    interface OnStateListener{

        abstract void onFinish();
        abstract void onStop();
        abstract void onContinue();

    }

    public void setOnStateListener(OnStateListener onStateListener){
        this.onStateListener = onStateListener;
    }

    public void isShowProgressNum(boolean b){
        this.isShowProgress = b;
    }

}
