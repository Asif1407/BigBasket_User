package NavFragments;

import android.net.Uri;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbasket_user.MainActivity;
import com.example.bigbasket_user.R;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataModels.Item;
import DataModels.ReviewModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<ReviewModel> mList;

    // For Review
    ReviewManager manager;
    ReviewInfo info;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        // Connecting to the Java File.
        recyclerView = view.findViewById(R.id.recyclerViewReview);
        mList = new ArrayList<>();
        gettingData();
        postReview();
        return view;
    }

    private void postReview() {
        manager = ReviewManagerFactory.create(getContext());
        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(Task<ReviewInfo> task) {

                if (task.isSuccessful()){
                    info = task.getResult();

//                    This code works in a Activity only

//                    Task<Void> flow = manager.launchReviewFlow(MainActivity.this,info);
//
//                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void result) {
//
//                        }
//                    });
                }else{
                    Toast.makeText(getContext(), "Error Review", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void gettingData() {

        Toast.makeText(getContext(), "Problem is here", Toast.LENGTH_SHORT).show();
        adapter = new ReviewAdapter(getContext(),mList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference reference = database.collection("Review");


        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value!= null){

                for (QueryDocumentSnapshot snapshot:value){
                    ReviewModel review = snapshot.toObject(ReviewModel.class);
                    mList.add(review);
                }
                adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
