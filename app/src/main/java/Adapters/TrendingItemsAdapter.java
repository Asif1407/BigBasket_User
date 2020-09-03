package Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.CartActivity;
import com.example.bigbasket_user.ItemDetailActivity;
import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import DataModels.Item;
import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.internal.FailingClientStream;

import static android.os.Build.VERSION_CODES.Q;

public class TrendingItemsAdapter extends RecyclerView.Adapter<TrendingItemsAdapter.ViewHolder> {

    Context mContext;
    List<Item> mList;

    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Cart");
    private FirebaseUser currentUSer;

    public TrendingItemsAdapter(Context mContext, List<Item> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Item item = mList.get(position);
        holder.item_title.setText(mList.get(position).getTitle());
        holder.item_price.setText(mList.get(position).getPrice());
        holder.item_qty.setText(mList.get(position).getQuantity());

        Picasso.get().load(mList.get(position).getImageUrl()).into(holder.itemsCircleView);

        holder.trendingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // Passing data
                // Calling other Activity & sending data.
                Intent itemsDetailIntent = new Intent(v.getContext(),ItemDetailActivity.class);
                itemsDetailIntent.putExtra("Title",item.getTitle());
                itemsDetailIntent.putExtra("Price",item.getPrice());
                itemsDetailIntent.putExtra("Quantity",item.getQuantity());
                itemsDetailIntent.putExtra("Description",item.getDescription());
                itemsDetailIntent.putExtra("Image",item.getImageUrl());
                v.getContext().startActivity(itemsDetailIntent);
            }
        });

        // We will implement this in other style. AfterWards.
        holder.add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActIntent = new Intent(v.getContext(), CartActivity.class);
                addingDataToCart(item);
                v.getContext().startActivity(ActIntent);
            }
        });
    }

    private void addingDataToCart(Item item) {
        currentUSer = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = currentUSer.getUid();

        Map<String, String> cart = new HashMap<>();
        cart.put("Title",item.getTitle());
        cart.put("Price",item.getPrice());
        cart.put("Quantity",item.getQuantity());
        cart.put("Description",item.getDescription());
        cart.put("ImageUrl",item.getImageUrl());

        ref.document(Uid).collection("newItems").document(item.getTitle()).set(cart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Log.d("DR", "DocumentSnapshot added with ID: " + ref.getId());
                    Toast.makeText(mContext, "Item Added Successfully :)", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ErrorCart",e.getMessage());
            }
        });

//        ref.document(Uid).collection(item.getTitle()).add(cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemsCircleView;
        TextView item_title;
        TextView item_price;
        TextView item_qty;
        TextView item_description;
        Button add_to_cart_button;
        CardView trendingCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemsCircleView =itemView.findViewById(R.id.itemsCircleView);
            item_title =itemView.findViewById(R.id.item_title);
            item_price =itemView.findViewById(R.id.item_price);
            item_qty =itemView.findViewById(R.id.item_quantity);
            item_description =itemView.findViewById(R.id.item_description);
            add_to_cart_button = itemView.findViewById(R.id.add_to_cart_button);

            // Card View
            trendingCart = itemView.findViewById(R.id.trendingCard);
        }
    }
}