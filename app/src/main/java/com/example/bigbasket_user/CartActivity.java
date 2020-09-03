package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.bigbasket_user.Adapter.AdapterCart;
import com.example.bigbasket_user.Model.ModelCart;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    //views
    private Button placeOrder;
    private RecyclerView cartItemRv;
    private TextView grandTotal, cartIsEmpty, detailCost, detailDelivery, detailTotal;
    private TableLayout table;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    //progress dialog
    private ProgressDialog mProgressDialog;
    //Model adapter class
    private AdapterCart adapterCart;
    private CollectionReference cartRefrence;
    int sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //init views
        cartItemRv = findViewById(R.id.cartItemRV);
        grandTotal = findViewById(R.id.grandTotalTV);
        placeOrder = findViewById(R.id.placeOrder);
        cartIsEmpty = findViewById(R.id.cartIsEmpty);
        detailCost = findViewById(R.id.detailCost);
        detailDelivery = findViewById(R.id.detailDelivery);
        detailTotal = findViewById(R.id.detailTotal);
        table = findViewById(R.id.table);
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        cartRefrence = fstore.collection("Cart").document(mAuth.getUid()).collection("newItems");
        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);

        loadCartItems();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentModeActivity.class);
                intent.putExtra("totalPrice", sum+"");
                startActivity(intent);
            }
        });
    }

    private void loadCartItems() {
        FirestoreRecyclerOptions<ModelCart> recyclerOptions = new FirestoreRecyclerOptions.Builder<ModelCart>().setQuery(cartRefrence, ModelCart.class).build();

        adapterCart = new AdapterCart(recyclerOptions);
        cartItemRv.setAdapter(adapterCart);

        cartRefrence.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(!value.isEmpty()) {
                    placeOrder.setVisibility(View.VISIBLE);
                    table.setVisibility(View.VISIBLE);
                    cartIsEmpty.setVisibility(View.GONE);
                    grandTotalPrice();
                } else {
                    grandTotal.setText("$0");
                    cartIsEmpty.setVisibility(View.VISIBLE);
                    table.setVisibility(View.GONE);
                    placeOrder.setVisibility(View.GONE);
                }
            }
        });
    }
    private void grandTotalPrice() {
        sum=0;
        cartRefrence.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            sum += Float.valueOf(documentSnapshot.getString("Price").replace("rs","").trim());
                            Log.d("price", sum+" !!");
                        }
                    }
                    grandTotal.setText("₹"+sum);
                    detailCost.setText("₹"+sum);
                    detailTotal.setText("₹"+sum);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterCart.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterCart.stopListening();
    }
}
