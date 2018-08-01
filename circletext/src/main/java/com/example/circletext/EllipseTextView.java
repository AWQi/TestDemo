package com.example.circletext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class EllipseTextView extends android.support.v7.widget.AppCompatTextView{
    private Paint paint = null;
    public EllipseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.setPadding(0,50,0,50);// 设置padding 改变 view 宽高   进而改变  椭圆宽高
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawArc(rectF,0,360,true,paint);
    }
}
