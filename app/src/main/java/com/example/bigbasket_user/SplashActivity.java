package com.example.bigbasket_user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ImageView imageView, bananaIv, grapesIv, avocadoIv, cabbageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.imageView);
        bananaIv = findViewById(R.id.bananaIV);
        grapesIv = findViewById(R.id.grapesIV);
        avocadoIv = findViewById(R.id.avocadoIV);
        cabbageIv =findViewById(R.id.cabbageIV);
//        imageView.setX(-1000);
        bananaIv.setX(-600);
        bananaIv.animate().translationXBy(250).setDuration(5000);
        avocadoIv.setX(-600);
        avocadoIv.animate().translationXBy(250).setDuration(5000);
        cabbageIv.setX(1200);
        cabbageIv.animate().translationXBy(-250).setDuration(5000);
        grapesIv.setX(1200);
        grapesIv.animate().translationXBy(-250).setDuration(5000);

        imageView.animate().rotation(1800).translationZ(1.5f).setDuration(2000);
//        String img = "https://firebasestorage.googleapis.com/v0/b/bigbasket-user.appspot.com/o/logo%2Fnewbblogo.png?alt=media&token=dbdbe2f2-4a5d-4684-a0ca-799b6508fdce";
//        Picasso.get().load(R.drawable.newbblogo).placeholder(R.drawable.newbblogo).into(imageView);

        checkInternet();

        mAuth = FirebaseAuth.getInstance();
    }

    private void checkInternet() {
        if (!isConnected(SplashActivity.this)){
            startActivity(new Intent(SplashActivity.this, NoInternetActivity.class));
            finish();
        }else {
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
            }, 5000);
        }
    }

    private boolean isConnected(SplashActivity splashActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork!=null) {
            return true;
        }
        else {
            return false;
        }
    }
//    private  void showCustomDialod(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
//        builder.setMessage("Please connect to the internet")
//                .setCancelable(false)
//                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                })
//                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                }).create().show();
//    }
}
