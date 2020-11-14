package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.DocumentDelete;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView transactionIdTv, paymentStatusTV, amountTV, transactionTV;
    String transactionID, totalPrice;

    FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        transactionID = getIntent().getStringExtra("transactionID");
        totalPrice = getIntent().getStringExtra("totalPrice");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbarMain);
        transactionIdTv = findViewById(R.id.transactionIdTV);
        paymentStatusTV = findViewById(R.id.paymentStatusTV);
        amountTV = findViewById(R.id.amountTV);
        transactionTV = findViewById(R.id.transactionTV);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (transactionID != null) {
            transactionIdTv.setVisibility(View.VISIBLE);
            transactionTV.setVisibility(View.VISIBLE);
            transactionIdTv.setText(transactionID);
            paymentStatusTV.setText("Paid");
            amountTV.setText("₹" + totalPrice);
            loadData(transactionID, totalPrice);
        } else {
            transactionIdTv.setVisibility(View.GONE);
            transactionTV.setVisibility(View.GONE);
            paymentStatusTV.setText("Unpaid (COD)");
            amountTV.setText("₹" + totalPrice);
            loadData(transactionID, totalPrice);

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

    private void loadData(final String transactionID, final String totalPrice) {
        final String tId = transactionID;
        final String tp = totalPrice;
        DocumentReference docRef = fstore.collection("Users").document(mAuth.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                updateOrderDetails(tId, tp, documentSnapshot.getString("name"), documentSnapshot.getString("phone"), documentSnapshot.getString("address"), documentSnapshot.getString("pinCode"));
                deleteFromCart();
            }
        });
    }

    private void deleteFromCart() {
        fstore.collection("Cart").document(mAuth.getUid()).collection("newItems")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                WriteBatch batch = FirebaseFirestore.getInstance().batch();
                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshots) {
                    batch.delete(snapshot.getReference());
                }

                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("pratik", "Maja aa gaya");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("pratik", "Maja nahi");
                    }
                });
                return;
            }
        });
    }

    private void updateOrderDetails(String transactionID, String totalPrice, String name, String phone, String address, String pinCode) {

        mProgressDialog.setMessage("Placing order...");
        mProgressDialog.show();
        final String timestamp = ""+System.currentTimeMillis();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", name);
        map1.put("address", address);
        map1.put("orderStatus", "In Progress");
        map1.put("transactionId", transactionID);
        map1.put("totalPrice", totalPrice);
        map1.put("pNumber", phone);
        map1.put("pinCode", pinCode);
        map1.put("timeStamp", timestamp);
        map1.put("UId", mAuth.getUid());
        if (transactionID != null) {
            map1.put("paymentStatus", "Paid");
            map1.put("paymentMode", "UPI");
        } else {
            map1.put("paymentStatus", "Unpaid");
            map1.put("paymentMode", "COD");
        }

        final DocumentReference docRefs = fstore.collection("Delivery").document(timestamp);
        docRefs.set(map1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Store", "Successfull");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(ConfirmOrderActivity.this, "" + e, Toast.LENGTH_SHORT).show();
            }
        });

        CollectionReference collRef = fstore.collection("Cart").document(mAuth.getUid()).collection("newItems");
        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot ds : value) {
                    String price = ds.getString("Price");
                    String quantity = ds.getString("Quantity");
                    String singlePrice = ds.getString("SinglePrice");
                    String title = ds.getString("Title");
                    String unit = ds.getString("Unit");

                    final Map<String, Object> items = new HashMap<>();
                    items.put("price", price);
                    items.put("quantity", quantity);
                    items.put("title", title);
                    items.put("singlePrice", singlePrice);
                    items.put("unit", unit);

                    docRefs.collection("Items").document(title).set(items)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Items", items+"");
                                }
                            });

//                    fstore.collection("Cart").document(mAuth.getUid()).collection("newItems")
//                            .document(title).delete();

                }
                mProgressDialog.dismiss();
                Toast.makeText(ConfirmOrderActivity.this, "Order Placed Successfully...", Toast.LENGTH_SHORT).show();
                return;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conferm_order, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfirmOrderActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
