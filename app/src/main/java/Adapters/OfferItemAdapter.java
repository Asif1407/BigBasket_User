package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import DataModels.Offers;

public class OfferItemAdapter extends FirestoreRecyclerAdapter<Offers, OfferItemAdapter.ViewHolder> {
    
    Context mContext;
    List mList;

    public OfferItemAdapter(@NonNull FirestoreRecyclerOptions<Offers> options, Context mContext, List mList) {
        super(options);
        this.mContext = mContext;
        this.mList = mList;
    }

//    public OfferItemAdapter(@NonNull FirestoreRecyclerOptions<Offers> options){
//        super(options);
//
//    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Offers model) {

        holder.tittle.setText(mList.get(position).toString());
        holder.rate.setText(mList.get(position).toString());

        Log.d("DataAdapterClass", mList+"");
//        holder.tittle.setText(model.getTitle());
//        holder.rate.setText(model.getRate());
        Picasso.get().load(model.getImageUrl()).into(holder.imageView);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_layout,parent,false);

        Toast.makeText(view.getContext(), "Is adapter calling itself??", Toast.LENGTH_SHORT).show();
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CardView offerCardView;
        public TextView tittle;
        public TextView rate;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offerCardView = itemView.findViewById(R.id.offerCardView);
            tittle = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
