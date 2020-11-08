package com.example.bigbasket_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;

public class PaymentModeActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioBtn;
    private TextView cartTotalTV;
    private Button placrOrderBtn;

    private Toolbar toolbar;

    final int UPI_PAYMENT = 0;
    String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        totalPrice = getIntent().getStringExtra("totalPrice");
        Log.d("PayPrice", totalPrice+" !!");

        radioGroup = findViewById(R.id.radioGroup);
        cartTotalTV = findViewById(R.id.cartTotal);
        placrOrderBtn = findViewById(R.id.placeOrder);

        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        // As we don't want to go back from here. And we have already given the title.
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        cartTotalTV.setText("₹"+totalPrice);

        placrOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                radioBtn = findViewById(selectId);
                if (selectId==-1){
                    Toast.makeText(PaymentModeActivity.this, "Please select any Payment Mode", Toast.LENGTH_SHORT).show();
                } else if (radioBtn.getText().equals("UPI Payment")) {
                    payUsingUpi(totalPrice, "Kiki Singh");
                    Toast.makeText(PaymentModeActivity.this, "UPI Payment", Toast.LENGTH_SHORT).show();
                } else if(radioBtn.getText().equals("Cash On Delivery (COD)")){
                    Toast.makeText(PaymentModeActivity.this, "Cash On Delivery (COD)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentModeActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("totalPrice", totalPrice);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void checkButton(View v) {

    }

    private void payUsingUpi(String amount, String name) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "9522468974@paytm")
                .appendQueryParameter("pn", name)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent intent = Intent.createChooser(upiPayIntent, "Pay With");

        if (null != intent.resolveActivity(getPackageManager())) {
            startActivityForResult(intent, UPI_PAYMENT);
        }
        else {
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String text = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentModeActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("transactionID", approvalRefNo);
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
                Log.d("PayPrice", "responseStr: "+approvalRefNo);
                Log.d("PayPrice", totalPrice+" @@");
                finish();
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
