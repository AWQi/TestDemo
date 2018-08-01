package com.example.springlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final OrderListView springList = findViewById(R.id.listView);
        Adapter adapter = new Adapter(MainActivity.this);
        springList.setAdapter(adapter);
        springList.setDivider(null);

        springList.setStatusChangListener(new OrderListView.StatusChangeListener() {
            TextView v = new TextView(MainActivity.this);
            {
                v.setTextSize(30);
                v.setGravity(View.TEXT_ALIGNMENT_CENTER);
            }

            @Override
            public void headerStart() {
                v.setText("下拉刷新");
                springList.addHeader(v);
            }
            @Override
            public void headerChange() {
                v.setText("松开刷新");
            }
            @Override
            public void headerEnd() {
                springList.cleanHeader();
            }
            @Override
            public void bottomStart() {
                v.setText("上拉加载");
                springList.addFooter(v);
            }
            @Override
            public void bottomChange() {
                v.setText("松开刷新");
            }
            @Override
            public void bottomEnd() {
                springList.cleanFooter();
            }
        });

    }
}
