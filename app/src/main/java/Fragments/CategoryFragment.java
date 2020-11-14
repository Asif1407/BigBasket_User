package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GithubAuthCredential;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Adapters.Item_Adapter;
import DataModels.Item;
import DataModels.Offers;

public class CategoryFragment extends Fragment {

    private ViewPager main_TabPager;
    private TabLayout main_Tabs;

    private SectionPagerClass sectionPagerClass;
    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        main_TabPager= (ViewPager) view.findViewById(R.id.main_TabPager);
        main_Tabs= (TabLayout) view.findViewById(R.id.main_Tabs);


       sectionPagerClass = new SectionPagerClass(getActivity().getSupportFragmentManager());

       // to link the TabLayout to the ViewPager
        main_Tabs.setupWithViewPager(main_TabPager);

        main_TabPager.setAdapter(sectionPagerClass);
        return (view);
    }
}
