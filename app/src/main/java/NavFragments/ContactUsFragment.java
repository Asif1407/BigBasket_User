package NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bigbasket_user.R;


public class ContactUsFragment extends Fragment {

    String contect;
    TextView contactUsTv, websiteLinkTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);
        contactUsTv = view.findViewById(R.id.ContactUsTV);
        websiteLinkTv = view.findViewById(R.id.websiteLinkTV);
        contect = "Station Road, Durg,\n" +
                "Chhattisgarh, India\n" +
                "Pincode: 491001\n";
        contactUsTv.setText(contect);
        websiteLinkTv.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}