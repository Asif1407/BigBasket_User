package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import DataModels.Search;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context mContext;
    private List mList;

    public SearchAdapter(Context mContext, List mList) {
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

        final Search search = (Search) mList.get(position);
        holder.textView.setText(search.getTitle());
        Picasso.get().load(search.getImageUrl()).into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageUrl = itemView.findViewById(R.id.imageUrl);
        }
    }
}
