package com.example.bigbasket_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
//        iconeIV = findViewById(R.id.iconIv);
//        iconText = findViewById(R.id.iconText);
//        leftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_animation);
//        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
//        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

//        iconeIV.setAnimation(leftAnimation);
//        iconText.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null){
                    Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent  = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
}
