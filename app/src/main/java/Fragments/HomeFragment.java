package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbasket_user.GreenTokri;
import com.example.bigbasket_user.MainActivity;
import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Adapters.SearchAdapter;
import Adapters.TrendingItemsAdapter;
import DataModels.Item;
import DataModels.Offers;
import DataModels.Search;

public class HomeFragment  extends Fragment {

    // For Trending Item Adapter.
    List<Item> mList = new ArrayList<>();
    TrendingItemsAdapter adapter;

    // Firebase
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Trending");

    // Layout
    private TextView search_bar_Main;
    private CarouselView carouselView;
    private Button shop_now_button;

    private RecyclerView trendingRecyclerView;

    final int[] sampleImages= {R.drawable.carouselone, R.drawable.carouseltwo, R.drawable.carouselthree,
            R.drawable.carouselfour, R.drawable.carouselfive};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, null, false);

        search_bar_Main = view.findViewById(R.id.search_bar_Main);

        // FOR carousel View
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        shop_now_button= view.findViewById(R.id.shop_now_button);


        // For RecyclerView of Trending Items.
        trendingRecyclerView= view.findViewById(R.id.trendingRecyclerView);

        // Calling Various Functions here.
        carouselView();
        setUpTrendingItemAdapter(view.getContext());
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

    private void setUpTrendingItemAdapter(Context context) {

        ref.whereEqualTo("Tag","True").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot: value){
                        Item item = snapshot.toObject(Item.class);
                        mList.add(item);
                    }
                    adapter.notifyDataSetChanged();

                }else{
                    Log.d("Error",error.getMessage());
                    // request.time < timestamp.date(2020, 8, 28);
                }
                Log.d("DataAdapter",mList+"");
            }
        });


        adapter = new TrendingItemsAdapter(getContext(),mList);
        trendingRecyclerView.setHasFixedSize(true);
        trendingRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        trendingRecyclerView.setAdapter(adapter);

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
}
