package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bigbasket_user.MainActivity;
import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import Adapters.TrendingItemsAdapter;
import DataModels.Item;

public class HomeFragment extends Fragment {


    private SearchView search_bar_Main;
    private CarouselView carouselView;
    private Button shop_now_button;


    // RecyclerView
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference collectionReference= database.collection("Products");


    private RecyclerView trendingRecyclerView;
    private TrendingItemsAdapter trendingItemsAdapter;

    final int[] sampleImages= {R.drawable.carouselone, R.drawable.carouseltwo, R.drawable.carouselthree,
            R.drawable.carouselfour, R.drawable.carouselfive};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        search_bar_Main = view.findViewById(R.id.search_bar_Main);

        // FOR carousel View
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        shop_now_button= view.findViewById(R.id.shop_now_button);

        // For RecyclerView of Trending Items.
        trendingRecyclerView= view.findViewById(R.id.trendingRecyclerView);
        Log.d("Adapter","IS adapter Working??");

        // Calling Various Functions here.

        carouselView();
        setUpTrendingItemAdapter();
        shopNowButton();

        return view;
    }

    private void shopNowButton() {
        shop_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fragment HomeFragment= new HomeFragment<>();
                Fragment CategoryFragment = new CategoryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,CategoryFragment).commit();

            }
        });
    }

    private void setUpTrendingItemAdapter() {

        Query query= collectionReference;

        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();

        // was missing this line of code.
        trendingItemsAdapter= new TrendingItemsAdapter(options);

        trendingRecyclerView.setHasFixedSize(true);
        trendingRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        trendingRecyclerView.setAdapter(trendingItemsAdapter);

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
                Toast.makeText(getActivity(), "Clicked Item =" + position + " will be applied soon.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        trendingItemsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        trendingItemsAdapter.stopListening();
    }
}
