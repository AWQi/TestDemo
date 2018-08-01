package com.example.attrs;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

public class MyToolBar extends Toolbar{

    public MyToolBar(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLogo(R.drawable.ic_launcher_background);
        this.setBackground(context.getResources().getDrawable(R.color.colorAccent));
        this.getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
    }
}
