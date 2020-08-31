package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;

import java.util.List;

import DataModels.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<History> mData;

    public HistoryAdapter(Context mContext, List<History> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.cardview_history,parent,false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.username.setText(mData.get(position).getUsername());
        holder.date.setText(mData.get(position).getDate());
        holder.time.setText(mData.get(position).getTime());
        holder.totalProducts.setText(mData.get(position).getTotalProducts());
        holder.totalAmount.setText(mData.get(position).getTotalAmount());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView date;
        public TextView time;
        public TextView totalProducts;
        public TextView totalAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            totalProducts = itemView.findViewById(R.id.totalProducts);
            totalAmount = itemView.findViewById(R.id.totalAmount);
        }
    }
}
