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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.CartActivity;
import com.example.bigbasket_user.ItemDetailActivity;
import com.example.bigbasket_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataModels.Item;
import Util.DiffUtilCallbacks;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.MyViewHolder> {

    ArrayList<Item> Items;
    Context context;

    private FirebaseUser currentUSer;
    private FirebaseFirestore database= FirebaseFirestore.getInstance();
    private CollectionReference ref= database.collection("Cart");


    public Item_Adapter(Context context, ArrayList<Item> itemNames) {
        this.context = context;
        this.Items = itemNames;
    }

    // Applying DifUtil Method
    public void insertData(List<Item> insertList){

        DiffUtilCallbacks callbacks = new DiffUtilCallbacks(Items,insertList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callbacks);

        Items.addAll(insertList);
        result.dispatchUpdatesTo(this);
        // Here this indicates to the whole adapter Class.
    }

    public void updateData(List<Item> newList){

        DiffUtilCallbacks callbacks = new DiffUtilCallbacks(Items,newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callbacks);

        // Clearing previous Data.
        Items.clear();
        Items.addAll(newList);
        result.dispatchUpdatesTo(this);
        // Here this indicates to the whole adapter Class.
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        // set the view's size, margins, padding and layout parameters.

        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

        final Item item = Items.get(position);
        String tag;

        // Setting Data.
        holder.title.setText(Items.get(position).getTitle());
        holder.price.setText((Items.get(position).getPrice()));
        holder.unit.setText(Items.get(position).getUnit());
        holder.quantity.setText(Items.get(position).getQuantity());
        tag = Items.get(position).getTag();
        if (tag.equals("True")) {
            holder.addToCart.setVisibility(View.VISIBLE);
            holder.stock.setVisibility(View.GONE);
        } else {
            holder.addToCart.setVisibility(View.GONE);
            holder.stock.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(item.getImageUrl()).into(holder.item_image);

        // Add to Cart Method
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActIntent = new Intent(v.getContext(), CartActivity.class);
                addingDataToCart(item);
                v.getContext().startActivity(ActIntent);
            }
        });

        // item Detail
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passing data
                // Calling other Activity & sending data.
                Intent itemsDetailIntent = new Intent(v.getContext(), ItemDetailActivity.class);
                itemsDetailIntent.putExtra("Title",item.getTitle());
                itemsDetailIntent.putExtra("Price",item.getPrice());
                itemsDetailIntent.putExtra("Quantity",item.getQuantity());
                itemsDetailIntent.putExtra("Description",item.getDescription());
                itemsDetailIntent.putExtra("Image",item.getImageUrl());
                itemsDetailIntent.putExtra("Unit", item.getUnit());
                itemsDetailIntent.putExtra("Tag", item.getTag());
                v.getContext().startActivity(itemsDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title, price, unit, quantity, stock;
        Button addToCart;
        ImageView item_image;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's

            cardView = itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.item_price);
            quantity = itemView.findViewById(R.id.quantity);
            unit = itemView.findViewById(R.id.qUnitTV);
            item_image = itemView.findViewById(R.id.item_image);
            addToCart = itemView.findViewById(R.id.addToCartButton);
            stock = itemView.findViewById(R.id.stock);
        }
    }

    private void addingDataToCart(Item item) {
        currentUSer = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = currentUSer.getUid();

        Map<String, String> cart = new HashMap<>();
        cart.put("Title", item.getTitle());
        cart.put("Price", item.getPrice());
        cart.put("SinglePrice", item.getSinglePrice());
        cart.put("Quantity", item.getQuantity());
        cart.put("Description", item.getDescription());
        cart.put("ImageUrl", item.getImageUrl());
        cart.put("Unit", item.getUnit());

        ref.document(Uid).collection("newItems").document(item.getTitle()).set(cart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Log.d("DR", "DocumentSnapshot added with ID: " + ref.getId());
                            Toast.makeText(context, "Item Added Successfully :)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ErrorCart", e.getMessage());
            }
        });
    }
}
