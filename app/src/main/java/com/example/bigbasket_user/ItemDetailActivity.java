package com.example.bigbasket_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class ItemDetailActivity extends AppCompatActivity {

    // Init of xml.
    Toolbar toolbar;
    TextView title;
    TextView price;
    TextView quantity;
    TextView description;
    TextView instockTextView;
    CarouselView carouselView;


    final int[] sampleImages= {R.drawable.carouselone, R.drawable.carouseltwo, R.drawable.carouselthree,
            R.drawable.carouselfour, R.drawable.carouselfive};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Link XML to Java file.
        toolbar = findViewById(R.id.toolbarMain);
        title= (TextView) findViewById(R.id.title);
        instockTextView = (TextView) findViewById(R.id.instockTextView);
        price = (TextView) findViewById(R.id.price);
        quantity = (TextView) findViewById(R.id.quantity);
        description = (TextView) findViewById(R.id.description);

        // For Toolbar
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Item Details");


        // FOR carousel View
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        Log.i("Testing_log","Is every thing working fine na?");

        // Calling Various Functions here.
        carouselView();
        settingData();
    }

    private void settingData() {

        Log.i("Testing_log","Is every thing working fine na?");

        // Getting Data's
        Intent getInfo = getIntent();
        String getTitle = getInfo.getExtras().getString("Title");
        String getPrice = getInfo.getExtras().getString("Price");
        String getQuantity = getInfo.getExtras().getString("Quantity");
        String getDescription = getInfo.getExtras().getString("Description");
        String getImage = getInfo.getExtras().getString("Image");

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
}