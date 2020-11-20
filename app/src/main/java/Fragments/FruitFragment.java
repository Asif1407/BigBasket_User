package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapters.Item_Adapter;
import DataModels.Item;

public class FruitFragment extends Fragment {

    // Layouts
    private RecyclerView recyclerViewVegetable;
    private ArrayList<Item> mList;
    private Item_Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    // Firebase
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference ref = database.collection("Fruits");


    public FruitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vegetable, container, false);

        addDataToList();

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        recyclerViewVegetable = view.findViewById(R.id.recyclerViewVegetable);
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

        return view;
    }

    private void addDataToList() {
        
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot snapshot:value){
                    Item item = snapshot.toObject(Item.class);
                    mList.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}