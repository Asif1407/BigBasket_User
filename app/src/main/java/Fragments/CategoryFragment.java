package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.GithubAuthCredential;

import java.util.ArrayList;

import Adapters.Item_Adapter;
import DataModels.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    ArrayList<Item> Items = new ArrayList<>();
    TabLayout tabLayout;
    ArrayList<String> imageurls = new ArrayList<>();



    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        tabLayout = view.findViewById(R.id.categorytab);


        final RecyclerView recyclerView = view.findViewById(R.id.itemsRecyclerView);
        // set a LinearLayoutManager with default vertical orientation
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        final Item_Adapter ItemAdapter = new Item_Adapter(getContext(),Items);
//
//        Item item = new Item("Title","Description","56","yes","yes","url","imageurls");
//        Item item1 = new Item("Title","Description","56","yes","yes","imageurls");

//        Items.add(item);
//        Items.add(item1);
        ItemAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(ItemAdapter);





        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==1){


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Inflate the layout for this fragment
        return (view);


    }
}
