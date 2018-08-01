package com.example.circletext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CircleTextView extends android.support.v7.widget.AppCompatTextView{
    private static final String TAG = "CircleTextView";
    private Paint paint = null;
    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec); //  先调用super.onMeasure 测量 不然得到的是 mathparent

        // 重新设置 控件大小 使 控件 宽高相同  用于画圆
        int width = getMeasuredWidth();
       int height = getMeasuredHeight();
        int d = Math.max(width,height);
        setMeasuredDimension(d,d);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽高 ，画圆
        int r =Math.min(getWidth(),getHeight())/2;
        Log.d(TAG, "onDraw: "+getWidth()+"  "+getHeight());
        canvas.drawCircle(getWidth()/2,getHeight()/2,r,paint);
    }
}
