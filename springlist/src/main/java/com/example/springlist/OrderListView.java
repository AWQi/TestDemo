package com.example.springlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderListView extends ListView  implements AbsListView.OnScrollListener,View.OnTouchListener{
    private static final String TAG = "SpringList";
    private  Context context;
    private  int firstVisibleItem;
    private  int visibleItemCount;
    private  int totalItemCount;
    private  int lastVisibleItem;
    private  int preLastVisibleItem;
    private  int ScrollNum = 0;
    private  float dx = 0; // 滑动距离x
    private  float dy = 0; // 滑动距离y
    private  float prex = 0;//  前一次 的坐标x
    private  float prey = 0;// 前一次 的坐标 y
    private LinearLayout emptyView ;
    private TextView footer = null;
    private TextView header = null;
    private boolean isStart = false;
    private boolean ischange = false;
    private  final int MAX_EMPTY_SIZE = Integer.MAX_VALUE;
    private final int CHANGE_SIZE = 500 ;
    private StatusChangeListener listener;
    public OrderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        emptyView = new LinearLayout(context);
        emptyView.setLayoutParams(param);
        emptyView.setOrientation(LinearLayout.VERTICAL);
        this.addHeaderView(emptyView);
        this.addFooterView(emptyView);

        setOnTouchListener(this);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem , int visibleItemCount, int totalItemCount) {
        /*   这里的参数  包含 header   footer */
            this.firstVisibleItem = firstVisibleItem;
            this.visibleItemCount = visibleItemCount;
            this.totalItemCount = totalItemCount;
            this.lastVisibleItem = visibleItemCount+firstVisibleItem;
//        Log.d(TAG, "firstVisibleItem: "+firstVisibleItem);
//        Log.d(TAG, "visibleItemCount: "+visibleItemCount);
//        Log.d(TAG, "totalItemCount: "+totalItemCount);
//        Log.d(TAG, "lastVisibleItem: "+lastVisibleItem);

    }

    public void addHeader(View v) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.CENTER;
        v.setLayoutParams(param);
        emptyView.addView(v);

    }
    public void  cleanHeader(){
        emptyView.removeAllViews();
    }
    public void addFooter(View v) {
        LinearLayout.LayoutParams param =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.CENTER;
        v.setLayoutParams(param);
        emptyView.addView(v);
    }
    public void cleanFooter(){
        emptyView.removeAllViews();
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN :
                prey = motionEvent.getY();//
                dy=  0; // dy  本次滑动初始化
                break;
            case  MotionEvent.ACTION_MOVE:
                dy=motionEvent.getY()-prey; // 此次滑动此时移动距离
                /**
                 *
                 *  上拉
                 */
                if (lastVisibleItem== totalItemCount&&dy<0){
                    int y = Math.abs((int)dy);
                    int padding = y>MAX_EMPTY_SIZE?MAX_EMPTY_SIZE:y;
                    emptyView.setPadding(0,padding/2,0,padding/2);
                    // 上拉start
                    if (!isStart){ //布尔去重
                        Log.d(TAG, "start: ");
                        dy = 0;prey = motionEvent.getY();
                        isStart = true;
                        if (listener!=null){
                            listener.bottomStart();
                        }
                    }
                    // 上拉 change
                    if (y>CHANGE_SIZE&&!ischange){
                        Log.d(TAG, "change: ");
                        ischange = true;
                        if (listener!=null){
                            listener.bottomChange();
                        }
                    }
                }
                /**
                 *  下拉
                 */
                if (firstVisibleItem== 0&&dy>0){
                    int y = (int)dy;
                    Log.d(TAG, "dy: ------------"+dy);
                    Log.d(TAG, "y: --------------"+y);
                    int padding = y>MAX_EMPTY_SIZE?MAX_EMPTY_SIZE:y;
                    emptyView.setPadding(0,padding/2,0,padding/2);

                    // 下拉start
                    if (!isStart){ //布尔去重
                        Log.d(TAG, "start: ");
                        dy = 0;prey = motionEvent.getY();
                        isStart = true;
                            if (listener!=null){
                                listener.headerStart();
                            }
                    }
                    // 下拉 change
                    if (y>CHANGE_SIZE&&!ischange){
                        Log.d(TAG, "change: ");
                        ischange = true;
                            if (listener!=null){
                                listener.headerChange();
                            }
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                emptyView.setPadding(0,0,0,0);
                // 上拉 end
                if (lastVisibleItem== totalItemCount&&dy<0){
                    Log.d(TAG, "end: ");
                        if (listener!=null){
                            listener.bottomEnd();
                        }
                }
                /**
                 *  下拉 end
                 */
                if (firstVisibleItem== 0&&dy>0){
                    Log.d(TAG, "end: ");
                        if (listener!=null){
                            listener.headerEnd();
                        }
                }
                isStart =false;
                ischange = false;
                break;
        }
        return false;
    }

    public void setStatusChangListener(StatusChangeListener listener) {
        this.listener = listener;
    }

    public  interface StatusChangeListener{
        void headerStart();
        void headerChange();
        void headerEnd();
        void bottomStart();
        void bottomChange();
        void bottomEnd();
    }
}
