package NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigbasket_user.R;

public class AboutUsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String about, about2;

    private TextView aboutTV, about2TV;

    public AboutUsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
//    public static AboutUsFragment newInstance(String param1, String param2) {
//        AboutUsFragment fragment = new AboutUsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        aboutTV = view.findViewById(R.id.aboutUs);
        about2TV = view.findViewById(R.id.aboutUs2);
        about = "The Green Tokri is an Indian Firm with its base in Durg, Chhattisgarh and is licensed owner of website " +
                "‘thegreentokri.in’. It has a wide product range of Fresh Fruits & Vegetables along with few selected exotic " +
                "verities, with every product delivered to the door step in sanitized and packed condition at most affordable " +
                "prices in Durg-Bhilai.\n" +
                "\n" +
                "We guarantee on time delivery and best quality product to remove burden from customer’s head for daily veggies shopping.";
        about2 = "The Green Tokri helps you in hassle free buying of daily Fruits and Veggies since the customer doesn’t need " +
                "to go to market place to buy the produce which saves their fuel, money and also maintains a peaceful mind by " +
                "avoiding a crowded place such as a vegetable market along with the road traffic jams, parking issues, carrying " +
                "heavy bags, etc. We also guarantee fresh and best quality produce that can be bought by just few clicks which makes " +
                "our weekly shopping easy and quick.";
        aboutTV.setText(about);
        about2TV.setText(about2);
        return view;
    }
}