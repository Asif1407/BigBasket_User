package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.ItemDetailActivity;
import com.example.bigbasket_user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import DataModels.Item;
import DataModels.Search;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> mList;

    public SearchAdapter(Context mContext, List<Item> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_demo,parent,false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Item item = mList.get(position);

        holder.title.setText(mList.get(position).getTitle());
        Picasso.get().load(item.getImageUrl()).into(holder.image);

        // when A item is clicked in searchBar
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
                v.getContext().startActivity(itemsDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setFilter(List<Item> listItem){
        mList = new ArrayList<>();
        mList.addAll(listItem);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView price;
        private ImageView image;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
