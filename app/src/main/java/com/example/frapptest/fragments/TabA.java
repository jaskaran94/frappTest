package com.example.frapptest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frapptest.ConnectionOfflineException;
import com.example.frapptest.R;
import com.example.frapptest.adapters.ModelAdapter;
import com.example.frapptest.api.ApiAdapter;
import com.example.frapptest.api.ApiService;
import com.example.frapptest.api.BaseApiCallback;
import com.example.frapptest.models.Model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class TabA extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<Model> mModels;
    private ModelAdapter adapter;

    public static TabA newInstance(Bundle bundle){
        TabA fragment = new TabA();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModels = new ArrayList<>();
        adapter = new ModelAdapter(mModels);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
        getData();
        return rootView;
    }

    public void getData(){
        ApiService service;
        try{
            service = ApiAdapter.getApiService(getActivity());
        } catch (ConnectionOfflineException e) {
            return;
        }
        Call<List<Model>> call = service.getData();
        call.enqueue(new BaseApiCallback<List<Model>>(getCurrentActivity()) {
            @Override
            public void onSuccess(List<Model> models) {
                mModels.addAll(models);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean updateProgress(boolean error) {
                return true;
            }

        });
    }
}
