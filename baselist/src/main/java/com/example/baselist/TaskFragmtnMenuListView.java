package com.example.baselist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class
TaskFragmtnMenuListView extends ListView{
    private  int[] icon  = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
    private  String[] title = {"历史订单","使用说明"};
    private  Context context;
    private BaseAdapter adapter;

    public TaskFragmtnMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
         adapter= new TaskMenuListViewAdapter();
         this.setAdapter(adapter);
    }

    public  class  TaskMenuListViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setText(title[position]);
            textView.setTextSize(25);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            Drawable drawable = ContextCompat.getDrawable(context,icon[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
//            textView.setCompoundDrawables(drawable,null,null,null);   // 使用此方法必须 setBounds

            return textView;
        }

    }
}
