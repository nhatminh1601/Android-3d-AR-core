package com.example.location.activities.admin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.admin.AdminActivity;
import com.example.location.adapters.DiscoverAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Museum;

import java.util.ArrayList;

public class FragmentCreature extends Fragment implements OnItemClickListener {
    Context context;
    AdminActivity main;
    RecyclerView recyclerView;
    DiscoverAdapter discoverAdapter;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Museum> museums;

    public static FragmentCreature newInstance() {
        FragmentCreature fragmentDiscover = new FragmentCreature();
        return fragmentDiscover;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getContext();
            main = (AdminActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, null);
        SetAdapter();
        return view;
    }

    private void SetAdapter() {
        museums = new ArrayList<>();
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        museums.add(new Museum(1, "Test", R.drawable.khampha, "this is test"));
        recyclerView = view.findViewById(R.id.recyclerViewDiscover);
        layoutManager=new GridLayoutManager(view.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        discoverAdapter= new DiscoverAdapter(museums,this);
        recyclerView.setAdapter(discoverAdapter);
    }

    @Override
    public void onItemClick(Object o) {
        Log.d("TAG", "aaaa: "+o.toString());
    }
}
