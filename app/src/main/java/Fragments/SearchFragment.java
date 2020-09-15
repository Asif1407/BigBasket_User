package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Adapters.SearchAdapter;
import DataModels.Item;
import DataModels.Search;

public class SearchFragment extends Fragment {

    // Init.
    private SearchView searchText;
    private ImageButton searchBtn;
    private RecyclerView recyclerView;

    // RecyclerView
    private List<Item> mainData;
    private SearchAdapter adapter;

    // Firebase
    private FirebaseFirestore database= FirebaseFirestore.getInstance();


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        // Connecting To java file
        searchText = view.findViewById(R.id.searchText);
        searchBtn = view.findViewById(R.id.searchBtn);
        recyclerView = view.findViewById(R.id.searchRecyclerView);

        //
        mainData = new ArrayList<>();

        adapter = new SearchAdapter(getContext(),mainData);

        // RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                Log.i("SearchItem",mainData.toString()+"1");
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                final List<Item> filtermodelist = filter(mainData,newText);
                adapter.setFilter(filtermodelist);
                mainData.clear();
                Log.i("SearchItem",mainData.toString()+"12");
                return true;
            }
        });

        return view;
    }

    private void search(String text) {

        // To get the total List of Items.
        // For Fruits Items.
        database.collection("Fruits").orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()){
                    for (QueryDocumentSnapshot snapshot: value){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }else{
                    Log.w("Error",error.getMessage());
                }
            }
        });

        database.collection("Vegetables").orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()){
                    for (QueryDocumentSnapshot snapshot: value){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }else{
                    Log.w("Error",error.getMessage());
                }
            }
        });

        database.collection("Trending").orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()){
                    for (QueryDocumentSnapshot snapshot: value){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }else{
                    Log.w("Error",error.getMessage());
                }
            }
        });

        database.collection("Offers").orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()){
                    for (QueryDocumentSnapshot snapshot: value){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }else{
                    Log.w("Error",error.getMessage());
                }
            }
        });

        database.collection("Others").orderBy("Title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()){
                    for (QueryDocumentSnapshot snapshot: value){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }else{
                    Log.w("Error",error.getMessage());
                }
            }
        });

    }

    private List<Item> filter(List<Item> list, String query){
        query = query.toLowerCase();

        final List<Item> filterModelList = new ArrayList<>();
        for (Item model:list){
            final String text = model.getTitle().toLowerCase();
            if (text.startsWith(query)){
                filterModelList.add(model);
            }
        }
        return  filterModelList;
    }
}


