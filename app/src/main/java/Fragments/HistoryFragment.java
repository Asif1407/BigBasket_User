package Fragments;

import android.content.Context;
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
import android.widget.Toast;

import com.example.bigbasket_user.Adapter.AdapterCart;
import com.example.bigbasket_user.CartActivity;
import com.example.bigbasket_user.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import Adapters.HistoryAdapter;
import DataModels.History;
import DataModels.Item;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRV;
    private HistoryAdapter adapterHistory;
    private ArrayList<History> orderList;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private CollectionReference collRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRV = view.findViewById(R.id.historyRV);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        orderList = new ArrayList<>();
        adapterHistory = new HistoryAdapter(getContext(), orderList);

        fstore.collection("Delivery").whereEqualTo("UId", mAuth.getUid())
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot snapshot:value) {
                    History history = snapshot.toObject(History.class);
                    orderList.add(history);
                }
                adapterHistory.notifyDataSetChanged();
            }
        });
        historyRV.setHasFixedSize(true);
        historyRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        historyRV.setAdapter(adapterHistory);

        return view;
    }

}