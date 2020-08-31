package com.example.bigbasket_user.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.Model.ModelCart;
import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class AdapterCart extends FirestoreRecyclerAdapter<ModelCart, AdapterCart.HolderCart> {

    public AdapterCart(@NonNull FirestoreRecyclerOptions<ModelCart> options) {
        super(options);
    }

    @NonNull
    @Override
    public HolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_cart, parent, false);
        return new HolderCart(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final HolderCart holder, int position, @NonNull final ModelCart modelCart) {

        final String title = modelCart.getTitle();
        String category = modelCart.getCategory();
        String itemImage = modelCart.getProductImage();
        final String[] quantity = {modelCart.getQuantity()};
        final String[] price = {modelCart.getPrice()};
        final String productId = modelCart.getProductId();


        holder.titleTv.setText(title);
        holder.categoryTv.setText(category);
        holder.priceTv.setText(price[0]);
        holder.quantityTv.setText(quantity[0]);
        try {
            Picasso.get().load(itemImage).placeholder(R.drawable.ic_cart_white).into(holder.productIconIv);
        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_cart_white);
        }

        final int singlePrice = Integer.valueOf(price[0])/Integer.valueOf(quantity[0]);
        final int[] cost = {Integer.valueOf(price[0])};
        final int[] quant = {Integer.valueOf(quantity[0])};
        holder.increasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant[0]++;
                cost[0] = cost[0] +singlePrice;
                holder.quantityTv.setText(String.valueOf(quant[0]));
                holder.priceTv.setText(String.valueOf(cost[0]));
                updateQuantity(String.valueOf(cost[0]), String.valueOf(quant[0]),v, productId);
            }
        });
        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quant[0]>1) {
                    quant[0]--;
                    cost[0]=cost[0]-singlePrice;
                    holder.quantityTv.setText(String.valueOf(quant[0]));
                    holder.priceTv.setText(String.valueOf(cost[0]));
                    updateQuantity(String.valueOf(cost[0]), String.valueOf(quant[0]),v, productId);
                }

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("DELETE")
                        .setMessage("Sure want to delete product " + title + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteProduct(productId, v);
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void updateQuantity(String s, String s1, final View v, String id) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getUid()).collection("cart").document(id);
        docRef.update("price", s, "quantity", s1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(v.getContext(), "Ho gaya", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Nahi huaa", e+"");
                        Toast.makeText(v.getContext(), "Nahi huaa\n"+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteProduct(String id, final View v){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        fstore.collection("Users").document(mAuth.getUid()).collection("cart").document(id).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(v.getContext(), "Item remove from cart...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), e+" While removing item from cart..", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    class HolderCart extends RecyclerView.ViewHolder{

        private ImageView productIconIv;
        private TextView titleTv, categoryTv, priceTv, quantityTv;
        private ImageButton deleteBtn, increasBtn, decreaseBtn;

        public HolderCart(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIV);
            titleTv = itemView.findViewById(R.id.titleTV);
            categoryTv = itemView.findViewById(R.id.categoryTV);
            priceTv = itemView.findViewById(R.id.discountPriceTV);
            quantityTv = itemView.findViewById(R.id.quantityTV);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            increasBtn = itemView.findViewById(R.id.increasBtn);
            decreaseBtn = itemView.findViewById(R.id.decreaseBtn);
        }

    }

}
