package com.example.springlist;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter{
    private Context context;
    private List<String> list = new ArrayList<>();
    {list.add("ASA");list.add("ASA");
    list.add("ASA");list.add("ASA");
        list.add("ASA");list.add("ASA");

        list.add("ASA");list.add("ASA");
        list.add("ASA");list.add("ASA");
        list.add("ASA");list.add("ASA");

        list.add("ASA");list.add("ASA");}

    public Adapter(Context context) {
        this.context = context;
    }

    public Adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = new TextView(context);
        tv.setTextSize(50);
        tv.setText(list.get(i));
        return tv;
    }
}
