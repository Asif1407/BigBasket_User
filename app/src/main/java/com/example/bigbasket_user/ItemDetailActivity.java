package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.HashMap;
import java.util.Map;

import DataModels.Item;

public class ItemDetailActivity extends AppCompatActivity {

    // Init of xml.
    Toolbar toolbar;
    TextView title;
    TextView price;
    TextView quantity;
    TextView description;
    TextView instockTextView;
    CarouselView carouselView;
    Button addToCart;

    // Firebase.
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Cart");
    private FirebaseUser currentUSer;

    private Item item = new Item();
    String getTitle;
    String getPrice;
    String getQuantity;
    String getDescription;
    String getImage;
    String getSinglePrice;
    String unit;

    final int[] sampleImages= {R.drawable.vege,R.drawable.fruit,R.drawable.fssai,R.drawable.carouselone};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Link XML to Java file.
        toolbar = findViewById(R.id.toolbar);
        title= (TextView) findViewById(R.id.title);
        instockTextView = (TextView) findViewById(R.id.instockTextView);
        price = (TextView) findViewById(R.id.price);
        quantity = (TextView) findViewById(R.id.quantity);
        description = (TextView) findViewById(R.id.description);
        addToCart = findViewById(R.id.addToCartButton);

        // To help reading data effectively.
        description.setMovementMethod(new ScrollingMovementMethod());

        // For Toolbar
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Item Details");


        // FOR carousel View
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        Log.i("Testing_log","Is every thing working fine na?");

        // Calling Various Functions here.
        carouselView();
        settingData();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActIntent = new Intent(v.getContext(), CartActivity.class);
                addingDataToCart(item);
                v.getContext().startActivity(ActIntent);
            }
        });
    }

    private void settingData() {

        Log.i("Testing_log","Is every thing working fine na?");

        // Getting Data's
        Intent getInfo = getIntent();
        getTitle = getInfo.getExtras().getString("Title");
        getPrice = getInfo.getExtras().getString("Price");
        getQuantity = getInfo.getExtras().getString("Quantity");
        getDescription = getInfo.getExtras().getString("Description");
        getImage = getInfo.getExtras().getString("Image");
        getSinglePrice = getInfo.getExtras().getString("SinglePrice");
        unit = getInfo.getExtras().getString("Unit");

        // Setting data
        title.setText(getTitle);
        price.setText(getPrice);
        quantity.setText(getQuantity);
        description.setText(getDescription);
    }

    private void carouselView() {
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ItemDetailActivity.this, "Clicked Item =" + position + " will be applied soon.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addingDataToCart(Item item) {

        currentUSer = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = currentUSer.getUid();

        Map<String, String> cart = new HashMap<>();
        cart.put("Title", getTitle);
        cart.put("Price", getPrice);
        cart.put("Quantity", getQuantity);
        cart.put("Description", getDescription);
        cart.put("SinglePrice",getSinglePrice);
        cart.put("Unit",unit);
        cart.put("ImageUrl", getImage);

        ref.document(Uid).collection("newItems").document(getTitle).set(cart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("DR", "DocumentSnapshot added with ID: " + ref.getId());
                            Toast.makeText(getApplicationContext() , "Item Added Successfully :)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ErrorCart", e.getMessage());
            }
        });
    }
}