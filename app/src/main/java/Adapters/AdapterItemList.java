package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;

import java.util.ArrayList;
import java.util.List;

import DataModels.History;
import DataModels.ModelItemList;

public class AdapterItemList extends RecyclerView.Adapter<AdapterItemList.HolderItems> {

    private Context context;
    public ArrayList<ModelItemList> itemList;

    public AdapterItemList(Context context, ArrayList<ModelItemList> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_items_cardview, parent, false);
        return new HolderItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItems holder, int position) {

        ModelItemList modelItems = itemList.get(position);
        holder.titleTv.setText(modelItems.getTitle());
        holder.quantityTv.setText(modelItems.getQuantity()+" (" + modelItems.getUnit()+")");
        holder.priceTv.setText(modelItems.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class HolderItems extends RecyclerView.ViewHolder {

        private TextView titleTv, quantityTv, priceTv;

        public HolderItems(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.titleTV);
            quantityTv = itemView.findViewById(R.id.quantityTV);
            priceTv = itemView.findViewById(R.id.priceTV);
        }
    }
}
