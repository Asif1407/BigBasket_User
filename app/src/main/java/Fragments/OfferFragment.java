package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapters.OfferItemAdapter;
import Adapters.Offer_Adapter;
import Adapters.TrendingItemsAdapter;
import DataModels.Offers;

public class OfferFragment extends Fragment {

    private RecyclerView offerRecyclerView;
    public Offer_Adapter adapter;
    public ArrayList<Offers> mList= new ArrayList<>();


    // FireStore Init.
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Offers");

    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_offer, container, false);

        offerRecyclerView = view.findViewById(R.id.offerRecyclerView);

        // Calling Functions.
        settingUpAdapter();

        return view;
    }

    private void settingUpAdapter() {

        adapter = new Offer_Adapter(getContext(),mList);
        offerRecyclerView.setHasFixedSize(true);
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        offerRecyclerView.setAdapter(adapter);

        ref.whereEqualTo("Tag","True").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!value.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot: value){
                        Offers offers = snapshot.toObject(Offers.class);
                        mList.add(offers);
                    }
                    adapter.notifyDataSetChanged();

                }else{
                    Log.d("Error",error.getMessage());
                    // request.time < timestamp.date(2020, 8, 28);
                }
                Log.d("DataAdapter",mList+"");
            }
        });
    }
}
