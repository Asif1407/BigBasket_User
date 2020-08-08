package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nullable;

import DataModels.Item;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.VERSION_CODES.Q;

public class TrendingItemsAdapter extends FirestoreRecyclerAdapter<Item, TrendingItemsAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> mList;

    public TrendingItemsAdapter(@NonNull FirestoreRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        holder.item_description.setText(model.getDescription());
        holder.item_title.setText(model.getTitle());
        Picasso.get().load(model.getImageUrl()).into(holder.itemsCircleView);

//        holder.add_to_cart_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Taking you to Cart Activity", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_items,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView itemsCircleView;
        TextView item_title;
        TextView item_description;
        Button add_to_cart_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemsCircleView=itemView.findViewById(R.id.itemsCircleView);
            item_title=itemView.findViewById(R.id.item_title);
            item_description=itemView.findViewById(R.id.item_description);
            add_to_cart_button= itemView.findViewById(R.id.add_to_cart_button);

        }
    }


}
