package Fragments;

import android.content.Context;
import android.graphics.Bitmap;
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

    // For Trending Item Adapter.
    List<Item> mList = new ArrayList<>();
    TrendingItemsAdapter adapter;

    // Firebase
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference ref = database.collection("Trending");

    // For UpComing Items Adapter.
    private RecyclerView upComingRecyclerView;
    List<Item> upComingList = new ArrayList<>();
    UpcomingAdapter upComingAdapter;

    // Firebase
    private CollectionReference upComingRef = database.collection("UpcomingItems");

    // Layout
    private TextView search_bar_Main;
    private CarouselView carouselView;
    private Button shop_now_button;

    private RecyclerView trendingRecyclerView;

    final int[] sampleImages = {R.drawable.vege, R.drawable.fruit, R.drawable.fssai, R.drawable.carouselone};
    public ArrayList<String> cList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//      Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, null, false);

        search_bar_Main = view.findViewById(R.id.search_bar_Main);
//      Carousel Image from Firebase;
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
//      FOR carousel View
//        carouselView.setPageCount(sampleImages.length);
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(final int position, final ImageView imageView) {
            Glide.with(getContext())
                    .asBitmap()
                    .load(cList.get(position))
//                    .load(sampleImages[position])
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@Nullable Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                    });

//                Picasso.get().load(cList.get(position)).into(imageView);
                carouselView.setImageClickListener(new ImageClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(getActivity(), "Clicked Item =" + position + " will be applied soon.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//      For RecyclerView of Trending Items.
        trendingRecyclerView = view.findViewById(R.id.trendingRecyclerView);

//      Calling Various Functions here.
        setUpTrendingItemAdapter(view.getContext());

        //UpcomingItem Implementation.
        upComingRecyclerView = view.findViewById(R.id.upComingRecyclerView);
        upcomingItems();
        return view;
    }

    private void upcomingItems() {
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

    private void setUpTrendingItemAdapter(Context context) {

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
        trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        trendingRecyclerView.setAdapter(adapter);
    }
}