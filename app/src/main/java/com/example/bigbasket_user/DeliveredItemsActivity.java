package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapters.AdapterItemList;
import Adapters.HistoryAdapter;
import DataModels.History;
import DataModels.ModelItemList;

public class DeliveredItemsActivity extends AppCompatActivity {

    private String id;
    private TextView orderidTv, nameTv, numberTv, addressTv, transactionIdTv, dateTv, orderStatusTv, paymentStatusTv, totalAmountTv, paymentModeTv;
    private RecyclerView itemListRv;
    private Toolbar toolbar;
    private Button pdfBtn;

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
        paymentModeTv = findViewById(R.id.paymentModeTV);
        totalAmountTv = findViewById(R.id.totalAmountTV);
        itemListRv = findViewById(R.id.itemListRV);
        pdfBtn = findViewById(R.id.pdfBtn);

        loadData(id);

        pdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formPdf();
            }
        });

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

    private void formPdf() {
        // Adjustment Needed and Proper Value to be passed.

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forDottedLine = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,350,1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);

        // Forming a Canvas so that the page form above it.
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        // Setting the values.
        canvas.drawText("GREEN TOKRI",20,20,paint);
        paint.setTextSize(8.8f);
        canvas.drawText("InFront of Gurudwara",20,40,paint);
        canvas.drawText("Station Road, Durg",20,55,paint);

        forDottedLine.setStyle(Paint.Style.STROKE);
        forDottedLine.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forDottedLine.setStrokeWidth(2);
        canvas.drawLine(25,65,230,65,forDottedLine);

        canvas.drawText("Customer Name : Niket Jain", 20, 80,paint);

        canvas.drawLine(25,105,230,105,forDottedLine);

        canvas.drawText("Purchase's",20,120,paint);

        // For First Row.
        canvas.drawText("Petrol",20,135,paint);
        canvas.drawText("2Ltr",120,135,paint);
        canvas.drawText("amount",220,135,paint);

        /* You can set the alignment of the text by using
         * paint.setTextAlign property */
        // For Second Row.
        canvas.drawText("Tax",20,175,paint);
        canvas.drawText("GST",120,175,paint);
        canvas.drawText("5%",220,175,paint);

        canvas.drawLine(20,200,230,200,forDottedLine);

        paint.setTextSize(10.2f);
        canvas.drawText("Total Price",150,220,paint);
        canvas.drawText("2 rs",220,200,paint);

        paint.setTextSize(12.8f);
        canvas.drawText("Date",20,245,paint);
        canvas.drawText("Invoice Number",20,265,paint);
        canvas.drawText("Payment Mode COD/UPI",20,285,paint);

        paint.setTextSize(15.2f);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("THANKYOU",canvas.getWidth()/2,300,paint);


        pdfDocument.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"),"greenTokri.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
        Toast.makeText(this, "PDF Formed", Toast.LENGTH_SHORT).show();
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
                String paymentMode = value.getString("paymentMode").trim();
                if (paymentMode.equals("COD")) {
                    paymentModeTv.setTextColor(Color.parseColor("#F9B100"));
                } else if (paymentMode.equals("UPI")) {
                    paymentModeTv.setTextColor(Color.parseColor("#43A047"));
                }
                paymentModeTv.setText(paymentMode);
                totalAmountTv.setText("Amt: ₹" + value.getString("totalPrice"));
            }
        });
    }
}
