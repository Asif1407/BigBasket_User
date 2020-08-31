package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bigbasket_user.Adapter.AdapterCart;
import com.example.bigbasket_user.Model.ModelCart;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    //progress dialog
    private ProgressDialog mProgressDialog;
    //Model adapter class
    private AdapterCart adapterCart;
    private CollectionReference cartRefrence;

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
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        cartRefrence = fstore.collection("Users").document(mAuth.getUid()).collection("cart");
        //init progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);

        loadCartItems();
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
                    cartIsEmpty.setVisibility(View.GONE);
                    grandTotalPrice();
                } else {
                    grandTotal.setText("$0");
                    cartIsEmpty.setVisibility(View.VISIBLE);
                    placeOrder.setVisibility(View.GONE);
                }
            }
        });
    }
    private void grandTotalPrice() {
        final ArrayList<Integer> list = new ArrayList<>();
        cartRefrence.orderBy("price").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                // RealTime OnEventLisnt. --> for loop --> String Total --> SetText.
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
