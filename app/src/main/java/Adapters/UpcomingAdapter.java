package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import DataModels.Item;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder>{

    private Context context;
    private List upComingItemList;

    public UpcomingAdapter(Context context, List<Item> upComingItemList) {
        this.context = context;
        this.upComingItemList = upComingItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_item_card, parent, false);
        // set the view's size, margins, padding and layout parameters.

        UpcomingAdapter.ViewHolder vh = new UpcomingAdapter.ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = (Item) upComingItemList.get(position);

        Picasso.get().load(item.getImageUrl()).into(holder.imageView);
        holder.upcomingItemTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return upComingItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        CardView cardView;
        TextView upcomingItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.cardView);
            upcomingItemTitle = itemView.findViewById(R.id.upcomingTitleTv);
        }
    }
}
