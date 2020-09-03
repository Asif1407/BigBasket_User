package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import Adapters.HistoryAdapter;
import DataModels.History;

public class HistoryFragment extends Fragment {

    private ArrayList<History> orderList;
    private RecyclerView historyRV;
    private HistoryAdapter adapterHistory;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private DocumentReference docRef;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRV = view.findViewById(R.id.historyRV);
        fstore =  FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        return view;
    }
}