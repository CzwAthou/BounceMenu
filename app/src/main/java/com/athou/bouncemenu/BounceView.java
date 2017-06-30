package com.athou.bouncemenu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/6/30.
 */

public class BounceView extends View {
    private int mArcHeight;//记录变换过程的距离
    private int mArcMaxHeight = 120;//弹窗最高距离
    private Paint mPaint;//画笔
    private Path mPath = new Path();//绘制动画弧度
    private BounceAnimatorListener animatorListener;//动画开始的监听回调
    private Status status = Status.NONE;//记录动画的状态

    public enum Status {
        //没动，上升，下降
        NONE, STATUS_UP, STATUS_DOWN
    }

    public BounceView(Context context) {
        super(context);
        init();
    }

    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void show() {
        status = Status.STATUS_UP;
        ValueAnimator animator = ValueAnimator.ofInt(0, mArcMaxHeight);
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                if (mArcHeight == mArcMaxHeight) {
                    if (animatorListener != null) {
                        animatorListener.showContent();
                    }
                    bounce();
                }
                invalidate();
            }
        });
        animator.start();
    }

    private void bounce() {
        status = Status.STATUS_DOWN;
        ValueAnimator animator = ValueAnimator.ofInt(mArcMaxHeight, 0);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentY = 0;
        switch (status) {
            case NONE:
                currentY = 0;
                break;
            case STATUS_UP:
                currentY = (int) (getHeight() * (1 - mArcHeight * 1f / mArcMaxHeight) + mArcMaxHeight);
                break;
            case STATUS_DOWN:
                currentY = mArcMaxHeight;
                break;
        }

        mPath.reset();
        mPath.moveTo(0, currentY);
        mPath.quadTo(getWidth() / 2, currentY - mArcHeight, getWidth(), currentY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void setAnimatorListener(BounceAnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }

    public interface BounceAnimatorListener {
        void showContent();
    }
}
