package com.example.bigbasket_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import Adapters.AdapterItemList;
import Adapters.HistoryAdapter;
import DataModels.History;
import DataModels.ModelItemList;

public class DeliveredItemsActivity extends AppCompatActivity {

    private String id;
    private TextView orderidTv, nameTv, numberTv, addressTv, transactionIdTv, dateTv, orderStatusTv, paymentStatusTv, totalAmountTv;
    private RecyclerView itemListRv;
    private Toolbar toolbar;

    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;

    private AdapterItemList adapterItemList;
    private ArrayList<ModelItemList> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_items);

        id = getIntent().getStringExtra("timeStamp");

        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        orderidTv = findViewById(R.id.orderidTv);
        nameTv = findViewById(R.id.nameTV);
        numberTv = findViewById(R.id.numberTV);
        addressTv = findViewById(R.id.addressTV);
        transactionIdTv = findViewById(R.id.transactionIdTV);
        dateTv = findViewById(R.id.dateTV);
        orderStatusTv = findViewById(R.id.orderStatusTV);
        paymentStatusTv = findViewById(R.id.paymentStatusTV);
        totalAmountTv = findViewById(R.id.totalAmountTV);
        itemListRv = findViewById(R.id.itemListRV);

        loadData(id);

        orderList = new ArrayList<>();
        adapterItemList = new AdapterItemList(this, orderList);

        fstore.collection("Delivery").document(id).collection("Items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot snapshot : value) {
                            ModelItemList items = snapshot.toObject(ModelItemList.class);
                            orderList.add(items);
                        }
                        adapterItemList.notifyDataSetChanged();
                    }
                });
        itemListRv.setHasFixedSize(true);
        itemListRv.setAdapter(adapterItemList);
    }

    private void loadData(final String id) {

        DocumentReference docRef = fstore.collection("Delivery").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                orderidTv.setText("OrderId: " + id);
                nameTv.setText(value.getString("name"));
                numberTv.setText(value.getString("pNumber"));
                addressTv.setText(value.getString("pinCode") + ", " + value.getString("address"));
                transactionIdTv.setText(value.getString("transactionId"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(value.getString("timeStamp")));
                String formatedData = DateFormat.format("dd/MM/yyyy", calendar).toString();
                dateTv.setText(formatedData);
                String orderStatus = value.getString("orderStatus");
                if (orderStatus.equals("In Progress")) {
                    orderStatusTv.setTextColor(Color.parseColor("#F9B100"));
                } else if (orderStatus.equals("Delivered")) {
                    orderStatusTv.setTextColor(Color.parseColor("#43A047"));
                } else if (orderStatus.equals("Cancelled")) {
                    orderStatusTv.setTextColor(Color.RED);
                }
                orderStatusTv.setText(orderStatus);
                String paymentStatus = value.getString("paymentStatus").trim();
                if (paymentStatus.equals("Unpaid")) {
                    paymentStatusTv.setTextColor(Color.parseColor("#F9B100"));
                } else if (paymentStatus.equals("Paid")) {
                    paymentStatusTv.setTextColor(Color.parseColor("#43A047"));
                } else if (paymentStatus.equals("Refund")) {
                    paymentStatusTv.setTextColor(Color.RED);
                }
                paymentStatusTv.setText(paymentStatus);
                totalAmountTv.setText("Amt: ₹" + value.getString("totalPrice"));
            }
        });

    }
}
