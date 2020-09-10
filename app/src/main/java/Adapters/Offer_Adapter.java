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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DataModels.Item;
import DataModels.Offers;

public class Offer_Adapter extends RecyclerView.Adapter<Offer_Adapter.ViewHolder>  {

    Context mContext;
    ArrayList<Offers> mList;


    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Cart");
    private FirebaseUser currentUSer;

    public Offer_Adapter(Context mContext, ArrayList<Offers> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_layout,parent,false);
        return new Offer_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Offers offers = mList.get(position);
        holder.title.setText(mList.get(position).getTitle());
        holder.price.setText(mList.get(position).getPrice());
        holder.qty.setText(mList.get(position).getQuantity());
        holder.qUnit.setText(mList.get(position).getUnit());
        Picasso.get().load(mList.get(position).getImageUrl()).into(holder.imageView);

        holder.offerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passing data
                // Calling other Activity & sending data.
                Intent itemsDetailIntent = new Intent(v.getContext(), ItemDetailActivity.class);
                itemsDetailIntent.putExtra("Title",offers.getTitle());
                itemsDetailIntent.putExtra("Price",offers.getPrice());
                itemsDetailIntent.putExtra("Quantity",offers.getQuantity());
                itemsDetailIntent.putExtra("Description",offers.getDescription());
                itemsDetailIntent.putExtra("Image",offers.getImageUrl());
                itemsDetailIntent.putExtra("Unit", offers.getUnit());
                v.getContext().startActivity(itemsDetailIntent);
            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActIntent = new Intent(v.getContext(), CartActivity.class);
                addingDataToCart(offers);
                v.getContext().startActivity(ActIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CardView offerCardView;
        public TextView title;
        public TextView price;
        public TextView qty;
        private TextView qUnit;
        public Button addToCartBtn;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offerCardView = itemView.findViewById(R.id.offerCardView);
            title = itemView.findViewById(R.id.title);
            qUnit = itemView.findViewById(R.id.qUnitTV);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.quantity);
            addToCartBtn = itemView.findViewById(R.id.addToCartButton);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private void addingDataToCart(Offers offers) {
        currentUSer = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = currentUSer.getUid();

        Map<String, String> cart = new HashMap<>();
        cart.put("Title",offers.getTitle());
        cart.put("Price",offers.getPrice());
        cart.put("Quantity",offers.getQuantity());
        cart.put("Description",offers.getDescription());
        cart.put("ImageUrl",offers.getImageUrl());
        cart.put("SinglePrice",offers.getSinglePrice());
        cart.put("Unit", offers.getUnit());

        ref.document(Uid).collection("newItems").document(offers.getTitle()).set(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
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

//        ref.add(cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d("DR", "DocumentSnapshot added with ID: " + documentReference.getId());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.w("ErrorCart",e.getMessage());
//            }
//        });
    }
}



