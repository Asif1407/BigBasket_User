package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Adapters.SearchAdapter;
import DataModels.Item;
import DataModels.Search;

public class SearchFragment extends Fragment {

    // Init.
    private EditText searchText;
    private Button searchBtn;
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

        // Search the Data.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchItem = searchText.getText().toString();
                search(searchItem);
            }
        });


        return view;
    }

    private void search(String text) {

        // To get the total List of Items.
        // For Trending Items.

        database.collection("Trending").orderBy("Title").startAt(text)
                .endAt(text + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
//                   mainData.clear();
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error",e.getMessage());
            }
        });

        // For Vegetables Items.

        database.collection("Vegetables").orderBy("Title").startAt(text)
                .endAt(text + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
//                    mainData.clear();
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error",e.getMessage());
            }
        });

        // For Fruits Items.

        database.collection("Fruits").orderBy("Title").startAt(text)
                .endAt(text + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
//                    mainData.clear();
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error",e.getMessage());
            }
        });

        // For Others Items.

        database.collection("Others").orderBy("Title").startAt(text)
                .endAt(text + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    mainData.clear();
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                        final Item item = snapshot.toObject(Item.class);
//                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error",e.getMessage());
            }
        });

        // For Offers Items.

        database.collection("Offers").orderBy("Title").startAt(text)
                .endAt(text + "\uf8ff").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
//                    mainData.clear();
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                        final Item item = snapshot.toObject(Item.class);
                        mainData.add(item);
                        Log.i("Data",item.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error",e.getMessage());
            }
        });
    }
}


