package NavFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import DataModels.ReviewModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    Context context;
    List<ReviewModel> mList;

    public ReviewAdapter(Context context, List<ReviewModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final ReviewModel review = mList.get(position);
       holder.name.setText(review.getName());
       holder.description.setText(review.getDescription());

       holder.ratingBar.setRating(5);

       Picasso.get().load(review.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imageView;
        private TextView name;
        private TextView description;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name= itemView.findViewById(R.id.name);
            description= itemView.findViewById(R.id.description);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}

