package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigbasket_user.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.HistoryAdapter;
import DataModels.History;

public class HistoryFragment extends Fragment {

    private List<History> mdata;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter adapter;



    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mdata = new ArrayList<>();
        mdata.add(new History("Niket_Jain","20/08/2020","12:00 A.M","5 products"
                ,"281.00 /-"));
        mdata.add(new History("Arjav_Jain","21/08/2020","14:00 A.M","3 products",
                "189.00 /-"));

        historyRecyclerView= view.findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HistoryAdapter(getContext(),mdata);
        historyRecyclerView.setAdapter(adapter);

        return view;
    }
}