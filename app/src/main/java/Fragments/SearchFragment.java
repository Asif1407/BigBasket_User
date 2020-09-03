package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import Adapters.SearchAdapter;
import DataModels.Item;
import DataModels.Search;

public class SearchFragment extends Fragment {

    private EditText searchView;
    private RecyclerView  recyclerViewSearch;
    private Button searchBtn;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference ref;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        // Init.
        searchView = view.findViewById(R.id.search_bar);
        recyclerViewSearch = view.findViewById(R.id.recyclerViewSearch);
        searchBtn =  view.findViewById(R.id.searchBtn);

        ref=database.collection("Vegetables");

        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}


