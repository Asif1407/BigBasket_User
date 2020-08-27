package com.example.bigbasket_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView transactionIdTv, paymentStatusTV, amountTV, transactionTV;
    String transactionID,totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        transactionID = getIntent().getStringExtra("transactionID");
        totalPrice = getIntent().getStringExtra("totalPrice");

        toolbar = findViewById(R.id.toolbarMain);
        transactionIdTv = findViewById(R.id.transactionIdTV);
        paymentStatusTV = findViewById(R.id.paymentStatusTV);
        amountTV = findViewById(R.id.amountTV);
        transactionTV = findViewById(R.id.transactionTV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (transactionID!=null){
            transactionIdTv.setVisibility(View.VISIBLE);
            transactionTV.setVisibility(View.VISIBLE);
            transactionIdTv.setText(transactionID);
            paymentStatusTV.setText("Paid");
            amountTV.setText("₹"+totalPrice);
        } else {
            transactionIdTv.setVisibility(View.GONE);
            transactionTV.setVisibility(View.GONE);
            paymentStatusTV.setText("Unpaid (COD)");
            amountTV.setText("₹"+totalPrice);
        }


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeBtn:
                        startActivity(new Intent(ConfirmOrderActivity.this, MainActivity.class));
                        finish();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conferm_order, menu);
        return true;
    }
}
