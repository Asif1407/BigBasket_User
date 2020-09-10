package Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbasket_user.DeliveredItemsActivity;
import com.example.bigbasket_user.Model.ModelCart;
import com.example.bigbasket_user.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DataModels.History;
import Fragments.HistoryFragment;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HolderHistory> {

    private Context context;
    public ArrayList<History> historyList;

    public HistoryAdapter(Context context, ArrayList<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_history, parent, false);
        return new HolderHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderHistory holder, int position) {
        History modelHistory = historyList.get(position);
        String name = modelHistory.getName();
        String orderId = modelHistory.getTimeStamp();
        String number = modelHistory.getpNumber();
        String totalAmount = modelHistory.getTotalPrice();
        final String date = modelHistory.getTimeStamp();
        String orderStatus = modelHistory.getOrderStatus();
        String paymentStatus = modelHistory.getPaymentStatus();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        String formatedData = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.nameTv.setText(name);
        holder.orderIdTv.setText("Order ID: "+orderId);
        holder.numberTv.setText(number);
        holder.totalAmountTv.setText("Total amount: ₹"+totalAmount);
        holder.dateTv.setText(formatedData);
        holder.orderStatusTv.setText(orderStatus);
        holder.paymentStatusTv.setText(paymentStatus);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeliveredItemsActivity.class);
                intent.putExtra("timeStamp", date);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HolderHistory extends RecyclerView.ViewHolder {

        private TextView nameTv, orderIdTv, numberTv, totalAmountTv, dateTv, orderStatusTv, paymentStatusTv;
        public HolderHistory(@NonNull View itemView) {
            super(itemView);

            nameTv =itemView.findViewById(R.id.nameTV);
            orderIdTv = itemView.findViewById(R.id.orderIdTV);
            numberTv = itemView.findViewById(R.id.numberTV);
            totalAmountTv = itemView.findViewById(R.id.totalAmountTV);
            dateTv =itemView.findViewById(R.id.dateTV);
            orderStatusTv =itemView.findViewById(R.id.orderStatusTV);
            paymentStatusTv =itemView.findViewById(R.id.paymentStatusTV);
        }
    }
}
