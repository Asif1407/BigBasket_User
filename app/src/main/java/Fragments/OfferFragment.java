package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bigbasket_user.R;

public class OfferFragment extends Fragment {

    private ImageView offerFruitsIV;
    private ImageView offerVegetablesIV;
    private Button shop_now_button;

    public OfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_offer, container, false);

        offerFruitsIV= view.findViewById(R.id.fruitsOffersIV);
        offerVegetablesIV= view.findViewById(R.id.offersVegetableIV);
        shop_now_button= view.findViewById(R.id.shop_now_button);

        offerVegetablesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Taking you to offer section in category fragments", Toast.LENGTH_SHORT).show();
            }
        });

        offerFruitsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Taking you to offer section in category fragments", Toast.LENGTH_SHORT).show();
            }
        });

        shop_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Taking you to offer section in category fragments", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
