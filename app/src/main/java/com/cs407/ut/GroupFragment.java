package com.cs407.ut;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.ut.adapter.LeftListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {
    private View rootView;
    private RecyclerView leftRecyclerView;
    private LeftListAdapter mLeftListAdapter;

    private List<String> leftDataList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.activity_group,container,false);

        leftRecyclerView = rootView.findViewById(R.id.group_list);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        leftDataList.add("Fashion");
        leftDataList.add("Life");
        leftDataList.add("Vintage");
        leftDataList.add("Renovation");
        leftDataList.add("Recycle");
        mLeftListAdapter = new LeftListAdapter(leftDataList);
        leftRecyclerView.setAdapter(mLeftListAdapter);

    }

}
