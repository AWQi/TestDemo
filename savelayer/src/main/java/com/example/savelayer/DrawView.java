package com.example.savelayer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DrawView extends View {
    int[][] color = {{184,216,174}
                      ,{114,126,241}
                      ,{140,232,217}
                      ,{243,232,90}
                      ,{252,227,217}
                      ,{81,179,196}};
    protected static final String TAG = "DrawView";
    static final int DRAW_CIRCLE = 100;
    static final int DRAW_TRANGLE = 101;
    static final int DRAW_LINE = 102;
    public static final int MESSAGE_READ = 2;
    private int view_width=0;//屏幕的宽度
    private int view_height=0;//屏幕的高度
    private int cellSize = 0;//  划分画布到单元格 设置单元格大小
    private float preX;//起始点的x坐标
    private float preY;//起始点的y坐标
    private int x = 0;// 当前行
    private int y = 0;// 当前列
    private int  n = 10; // 定义每行 几个图形
    private int m = 0; //定义一共可以放多少行
    int margin = 5; //  定义四周缩进
    private Path path;//路径
    public Paint paint ;//画笔
    public  int curX = 0; // 保存当前画笔的  x  坐标
    Bitmap cacheBitmap=null;//定义一个内存中的图片，该图片将作为缓冲区
    Bitmap bmp = null; // 存放背景图
    Canvas cacheCanvas=null;//定义cacheBitmap上的Canvas对象
    private Handler handler ;
    private Context context;
    /*
     * 功能：构造方法
     * */
    public DrawView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view_width = context.getResources().getDisplayMetrics().widthPixels;//获取屏幕宽度
        view_height = context.getResources().getDisplayMetrics().heightPixels;//获取屏幕高度
        Log.d(TAG, "屏幕高: " + view_width);
        Log.d(TAG, "屏幕宽: " + view_height);
        cellSize = view_width / 20;// 一行n个图形
        m = view_height / cellSize;

//创建一个与该View相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();//创建一个新的画布
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);
        paint = new Paint(Paint.DITHER_FLAG);//Paint.DITHER_FLAG防抖动的
        paint.setColor(Color.RED);
        //设置画笔风格
        paint.setStyle(Paint.Style.FILL);//设置填充方式为描边
        paint.setStrokeJoin(Paint.Join.ROUND);//设置笔刷转弯处的连接风格
        paint.setStrokeCap(Paint.Cap.ROUND);//设置笔刷的图形样式(体现在线的端点上)
        paint.setStrokeWidth(20);//设置默认笔触的宽度为1像素
        paint.setAntiAlias(true);//设置抗锯齿效果
        paint.setDither(true);//使用抖动效果

        Log.d(TAG, "构造器: ");

    }
    /*
     * 功能：重写onDraw方法
     * */
    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFFFFFFF);//设置背景色
        bmp= getBitmap(context,R.drawable.bg);



        cacheBitmap = bmp;
        canvas.drawBitmap(cacheBitmap, 0, 0,paint);//绘制cacheBitmap
        canvas.save(Canvas.ALL_SAVE_FLAG);//保存canvas的状态

        paint.setColor(Color.BLUE);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(200, 200);
        canvas.drawRect(100, 100, 300, 300, paint);
        canvas.restore();

        paint.setColor(Color.RED);
        canvas.drawRect(100, 100, 400, 400, paint);


    }


    /**
     *
     *
     *              根据数据画图形
     *
     * @param canvas
     * @param cellSize
     * @param margin
     * @param paint
     */
    public  void drawCircleByData(Canvas canvas, int cellSize, int margin, Paint paint){
        x=curX+margin+ cellSize/2;
        Random rdm = new Random();
//        y = rdm.nextInt(view_width-cellSize+1)+cellSize;//随机高度
        y = rdm.nextInt(view_height*2/3);//随机高度
        Log.d(TAG, "width: "+view_width);
        Log.d(TAG, "heigh: "+view_height);
        Log.d(TAG, "y: "+y);
        Log.d(TAG, "x: "+x);
        canvas.save();
        paint.setColor(Color.RED);
        drawCirCle(cacheCanvas,x,y,cellSize/2-margin,paint);
        canvas.restore(); //合并图层
        curX = curX+cellSize+margin;
    }
    public  void drawTriangleByData(Canvas canvas, int cellSize, int x, int y, int margin , Paint paint){
        Random rdm = new Random();
        y = rdm.nextInt(view_width-cellSize+1)+cellSize;//随机高度        x=curX+margin;
        paint.setColor(Color.rgb(199, 88, 76));
        drawTriangle(cacheCanvas,x,y
                ,x+cellSize,y
                ,x+cellSize/2,y-cellSize,paint);
        curX = curX+cellSize+margin;
    }
    public  void drawLineByData(Canvas canvas, int cellSize, int x, int y, int time, double k, int margin , Paint paint){
        Random rdm = new Random();
        y = rdm.nextInt(view_width-cellSize+1)+cellSize;//随机高度        x=curX+margin;
        int c =rdm.nextInt(5);
        paint.setColor(Color.rgb(color[c][0], color[c][1], color[c][2]));// 随机颜色

        canvas.drawLine(x, y
                ,x+time,(float) (y-time*k)
                , paint);// 画线  从左下角
    }
    /**
     *
     *      画文本
     */
    public void drawText(Canvas canvas, String text, int x, int y, float textSize, Paint bmpPaint){
        bmpPaint.setTextSize(textSize);//  size  以xp为单位
        canvas.drawText("画图：", x, y, bmpPaint); //  x y  为基线的坐标
    }
    /**
     *     画圆
     */
    public  void drawCirCle(Canvas canvas, int x, int y, int rad, final Paint bmpPaint){
//        bmpPaint.setStyle(Paint.Style.STROKE);// 设置空心
//        bmpPaint.setStyle(Paint.Style.FILL);
        bmpPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(x, y, rad, bmpPaint);// x y 是坐标  rad 是半径
    }
    /**
     *   画三角
     *
     */
    public  void drawTriangle(Canvas canvas, int x1, int y1, int x2 , int y2, int x3, int y3, final Paint bmpPaint){
        Path path = new Path();
        path.moveTo(x1, y1);// 此点为多边形的起点
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close(); // 使这些点构成封闭的多边形
        bmpPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, bmpPaint);
    }
    /**
     *      画线
     *
     */
    public  void  drawLine(Canvas canvas , int startX, int startY , int endX, int endY , Paint bmpPaint){
        canvas.drawLine(startX, startY, endX, endY, bmpPaint);// 画线
    }

private static Bitmap getBitmap(Context context, int vectorDrawableId) {
    Resources r = context.getResources();
    @SuppressLint("ResourceType") InputStream is = r.openRawResource(R.drawable.bg);
//    Log.d(TAG, "getBitmap: is"+is);
    BitmapDrawable bmpDraw = new BitmapDrawable(is);
//    Log.d(TAG, "getBitmap: draw"+bmpDraw);
    Bitmap bmp = bmpDraw.getBitmap();
//    Log.d(TAG, "getBitmap: bimap"+bmp);
    return bmp;
}
}