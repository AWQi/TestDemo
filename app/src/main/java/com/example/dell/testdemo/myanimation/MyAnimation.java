package com.example.dell.testdemo.myanimation;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.dell.testdemo.R;

public class MyAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animation);

        ImageView iv = findViewById(R.id.iv);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv,"rotation",0,360);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }
}
