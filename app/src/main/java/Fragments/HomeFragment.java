package Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.bigbasket_user.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import Adapters.Item_Adapter;
import Adapters.TrendingItemsAdapter;
import Adapters.UpcomingAdapter;
import DataModels.Item;

public class HomeFragment extends Fragment {

    // Main Screen of the App. Two RV, Carousel.

    // Firebase Setup.
    final private FirebaseFirestore database = FirebaseFirestore.getInstance();
    final private CollectionReference ref = database.collection("Trending");
    final private CollectionReference upComingRef = database.collection("UpcomingItems");

    // List for Carousel Images.
    public ArrayList<String> cList;

    // For Trending Item Adapter.
    List<Item> mList = new ArrayList<>();
    TrendingItemsAdapter adapter;

    // For UpComing Items Adapter.
    List<Item> upComingList = new ArrayList<>();
    UpcomingAdapter upComingAdapter;

    // Declaration of XML views.
    private CarouselView carouselView;
    private RecyclerView trendingRecyclerView;
    private RecyclerView upComingRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, null, false);

        Init(view);
        SetUpCarousel(view);

        //Calling Various Functions here.
        setUpTrending();
        UpcomingItems();
        return view;
    }

    private void SetUpCarousel(View view) {
        // Getting Data from Firebase.
        cList = new ArrayList<>();
        database.collection("Carousel").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    int count=0;
                    for (QueryDocumentSnapshot snapshot : value) {
                        cList.add(snapshot.getString("Carousel"));
                        Log.i("carouselView", snapshot.getString("Carousel")+" "+count);
                    }
                    carouselView.setPageCount(cList.size());
                } else {
                    Log.i("Value 123", "empty");
                }
            }
        });

        // Setting the Images to Carousel View.
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(final int position, final ImageView imageView) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(cList.get(position))
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@Nullable Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imageView.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }

                        });

                carouselView.setImageClickListener(new ImageClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(getActivity(), "UpComing! ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void Init(View v) {
        carouselView = v.findViewById(R.id.carouselView);
        // For RecyclerView of Trending Items.
        trendingRecyclerView = v.findViewById(R.id.trendingRecyclerView);
        //UpcomingItem Implementation.
        upComingRecyclerView = v.findViewById(R.id.upComingRecyclerView);
    }

    private void UpcomingItems() {
        // Sending Data to Adapter.
        upComingRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot : value) {
                        Item item = snapshot.toObject(Item.class);
                        upComingList.add(item);
                    }
                    upComingAdapter.notifyDataSetChanged();

                } else {
                    Log.d("Error", error.getMessage());
                }
                Log.d("DataAdapter", mList + "");
            }
        });


        upComingAdapter = new UpcomingAdapter(getContext(), upComingList);
        upComingRecyclerView.setHasFixedSize(true);
        upComingRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        upComingRecyclerView.setAdapter(upComingAdapter);
    }

    private void setUpTrending() {
        ref.whereEqualTo("Tag", "True").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot : value) {
                        Item item = snapshot.toObject(Item.class);
                        mList.add(item);
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d("Error", error.getMessage());
                }
                Log.d("DataAdapter", mList + "");
            }
        });


        adapter = new TrendingItemsAdapter(getContext(), mList);
        trendingRecyclerView.setHasFixedSize(true);
        trendingRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        trendingRecyclerView.setAdapter(adapter);
    }
}