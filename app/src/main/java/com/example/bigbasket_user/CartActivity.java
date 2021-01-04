package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbasket_user.Adapter.AdapterCart;
import com.example.bigbasket_user.Model.ModelCart;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import Fragments.AccountFragment;

public class CartActivity extends AppCompatActivity {

    //views
    private Button placeOrder;
    private RecyclerView cartItemRv;
    private TextView grandTotal, cartIsEmpty, detailCost, detailDelivery, detailTotal, acceptOrders;
    private TableLayout table;
    RelativeLayout RL;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    //progress dialog
    private ProgressDialog mProgressDialog;
    //Model adapter class
    private AdapterCart adapterCart;
    final String[] acceptOrder = {"a"};
    private CollectionReference cartRefrence;
    int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();
        progressBar();
        cartRefrence = fstore.collection("Cart").document(mAuth.getUid()).collection("newItems");
        AcceptOrderCheck();
        loadCartItems();
        buttonOnClick();
    }

    private void AcceptOrderCheck() {
        fstore.collection("OrderOptions").document("QGWvyuszvUl3ipn3FFRh")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        acceptOrder[0] = value.getString("Open");
                        Log.d("AcceptOrder", acceptOrder[0]);
                    }
                });
    }

    private void progressBar() {
        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    private void buttonOnClick() {
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fstore.collection("Users").document(mAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (documentSnapshot.exists() ) {
                            if (isConnected()) {
                                Intent intent = new Intent(CartActivity.this, PaymentModeActivity.class);
                                intent.putExtra("totalPrice", sum + "");
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(CartActivity.this, NoInternetActivity.class));
                            }
                        } else {
                            RL.setVisibility(View.GONE);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.cartContainer, new AccountFragment()).commit();
                            Toast.makeText(CartActivity.this, "Do fill the credentials first in accounts to continue", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void init() {
        //init views
        cartItemRv = findViewById(R.id.cartItemRV);
        grandTotal = findViewById(R.id.grandTotalTV);
        placeOrder = findViewById(R.id.placeOrder);
        cartIsEmpty = findViewById(R.id.cartIsEmpty);
        detailCost = findViewById(R.id.detailCost);
        detailDelivery = findViewById(R.id.detailDelivery);
        acceptOrders = findViewById(R.id.acceptOrder);
        detailTotal = findViewById(R.id.detailTotal);
        table = findViewById(R.id.table);
        RL = findViewById(R.id.RL);
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork!=null) {
            return true;
        }
        else {
            return false;
        }
    }

    private void loadCartItems() {
        FirestoreRecyclerOptions<ModelCart> recyclerOptions = new FirestoreRecyclerOptions.Builder<ModelCart>().setQuery(cartRefrence, ModelCart.class).build();

        adapterCart = new AdapterCart(recyclerOptions);
        cartItemRv.setAdapter(adapterCart);

        cartRefrence.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    Log.d("AcceptOrder123", acceptOrder[0]);
                    if (acceptOrder[0].equals("false")) {
                        placeOrder.setVisibility(View.GONE);
                        acceptOrders.setVisibility(View.VISIBLE);
                    } else {
                        placeOrder.setVisibility(View.VISIBLE);
                        acceptOrders.setVisibility(View.GONE);
                    }
                    table.setVisibility(View.VISIBLE);
                    cartIsEmpty.setVisibility(View.GONE);
                    grandTotalPrice();
                } else {
                    grandTotal.setText("$0");
                    acceptOrders.setVisibility(View.GONE);
                    cartIsEmpty.setVisibility(View.VISIBLE);
                    table.setVisibility(View.GONE);
                    placeOrder.setVisibility(View.GONE);
                }
            }
        });
    }

    private void grandTotalPrice() {
        sum = 0;
        cartRefrence.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            sum += Float.valueOf(documentSnapshot.getString("Price").replace("rs", "").trim());
                            Log.d("price", sum + " !!");
                        }
                    }
                    detailCost.setText("₹" + sum);
                    if (sum<150) {
                        sum = sum+30;
                        detailDelivery.setText("₹30");
                    } else {
                        detailDelivery.setText("₹0");
                    }
                    detailTotal.setText("₹" + sum);
                    grandTotal.setText("₹" + sum);
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
