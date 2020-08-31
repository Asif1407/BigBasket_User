package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import DataModels.Item;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.MyViewHolder> {

    ArrayList<Item> Items;
    Context context;
    String uri = "https://ichef.bbci.co.uk/news/410/cpsprodpb/5655/production/_94810122_istock-494702400.jpg";
    public Item_Adapter(Context context, ArrayList<Item> itemNames) {
        this.context = context;
        this.Items = itemNames;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
//        holder.name.setText(itemNames.get(position));

        holder.description.setText(Items.get(position).getDescription());
        holder.price.setText((Items.get(position).getPrice()));

        Picasso.get().load(uri).into(holder.item_image);



        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                            }
        });

    }


    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,price;
        Button addtocart,buynow;
        ImageView item_image;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
//            name = (TextView) itemView.findViewById(R.id.name);

            item_image = itemView.findViewById(R.id.item_image);
            description = itemView.findViewById(R.id.item_description);
            price = itemView.findViewById(R.id.item_price);
            addtocart = itemView.findViewById(R.id.addtocart_button);
            buynow = itemView.findViewById(R.id.buynow_button);


        }
    }
}
