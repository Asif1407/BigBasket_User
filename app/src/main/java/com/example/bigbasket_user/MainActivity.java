package com.example.bigbasket_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import Fragments.AccountFragment;
import Fragments.CategoryFragment;
import Fragments.HomeFragment;
import Fragments.OfferFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btm_navigation;
    private Fragment selectorFragment;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Hello there");

        /*
        Adding XML files to java Files here.
        */

        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        // As we don't want to go back from here. And we have already given the title.
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        btm_navigation = findViewById(R.id.btm_navigation);



        // As by default we want home fragment to show up.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        // As by default we want Home Item to be selected.
        btm_navigation.setSelectedItemId(R.id.home_btm);

        // Calling Various Functions.
        bottomNavigationView();
    }



    private void bottomNavigationView(){
        btm_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home_btm:
                        selectorFragment= new HomeFragment();
                        break;

                    case R.id.categories_btm:
                        selectorFragment= new CategoryFragment();
                        break;

                    case R.id.offers_btm:
                        selectorFragment= new OfferFragment();
                        break;

                    case R.id.accounts_btm:
                        selectorFragment= new AccountFragment();
                        break;
                }

                if (selectorFragment!= null){
                    //Replacing the fragments accordingly.
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                }

                return true;
            }
        });

    }
}
