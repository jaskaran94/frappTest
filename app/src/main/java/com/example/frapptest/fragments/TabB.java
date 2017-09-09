package com.example.frapptest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frapptest.R;
import com.example.frapptest.adapters.ModelAdapter;
import com.example.frapptest.models.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class TabB extends Fragment{

    private List<Model> mModelList;
    private ModelAdapter adapter;
    private RecyclerView recyclerView;
    TextView error;

    public static TabB newInstance(Bundle bundle){
        TabB fragment = new TabB();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelList = new ArrayList<>();
        adapter = new ModelAdapter(mModelList, getActivity(), "B");
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        error = (TextView) rootView.findViewById(R.id.error);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        if (mModelList.size() == 0){
            error.setVisibility(View.VISIBLE);
            error.setText(getString(R.string.no_favorites));
        }
        return rootView;
    }

    public void updateList(Model model, boolean marked){
        if (marked){
            mModelList.add(model);
        }else {
            mModelList.remove(model);
        }
        if (mModelList.size() == 0){
            error.setVisibility(View.VISIBLE);
            error.setText(getString(R.string.no_favorites));
        }else {
            error.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
}
