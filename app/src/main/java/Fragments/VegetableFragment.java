package Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapters.Item_Adapter;
import Adapters.Offer_Adapter;
import DataModels.Item;

public class VegetableFragment extends Fragment {

    // Firebase
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference ref = database.collection("Vegetables");

    // Layouts
    private RecyclerView recyclerViewVegetable;
    private ArrayList<Item> mList;
    private Item_Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public VegetableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vegetable, container, false);

        Init(view);
        addDataToList();
        InitRV(view);
        return view;
    }

    private void InitRV(View view) {
        mList = new ArrayList<>();
        adapter = new Item_Adapter(getContext(),mList);

        recyclerViewVegetable.setHasFixedSize(true);
        recyclerViewVegetable.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        // Adding Already Existed data.
        adapter.insertData(mList);

        recyclerViewVegetable.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Adding new Data;
                final List<Item> updated = new ArrayList<>();
                ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (QueryDocumentSnapshot snapshot:value){
                            Item item = snapshot.toObject(Item.class);
                            updated.add(item);
                        }
                        adapter.updateData(updated);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void Init(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerViewVegetable = view.findViewById(R.id.recyclerViewVegetable);
    }

    private void addDataToList() {
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mList.clear(); // So that it loads up completely.
                for (QueryDocumentSnapshot snapshot:value){
                    Item item = snapshot.toObject(Item.class);
                    mList.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}