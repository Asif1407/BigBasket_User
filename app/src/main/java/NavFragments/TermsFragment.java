package NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;

public class TermsFragment extends Fragment {

    public TermsFragment() {
        // Required empty public constructor
    }

    String term, Cancellation, Replacement, Modification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_terms, container, false);
        return view;
    }
}