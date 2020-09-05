package com.example.location.activities.user.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.user.MuseumActivity;
import com.example.location.adapters.CustomAdapter;
import com.example.location.dummy.MuseumTypeDummy;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.MuseumType;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements OnItemClickListener {
    ArrayList<MuseumType> museumTypes;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    View view;
    CustomAdapter customAdapter;
    Context context;
    MainActivity main;

    public static FragmentHome newInstance() {
        FragmentHome fragmentHome = new FragmentHome();
        return fragmentHome;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getContext();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        SetAdapter();
        return view;
    }

    private void SetAdapter() {
        MuseumTypeDummy museumTypeDummy = new MuseumTypeDummy();
        museumTypes = (ArrayList<MuseumType>) museumTypeDummy.list();
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        customAdapter = new CustomAdapter(museumTypes,this);
        recyclerView.setAdapter(customAdapter);


    }

    public void search(String text) {
        customAdapter.getFilter().filter(text);
    }

    @Override
    public void onItemClick(Object o) {
        Log.d("TAG", "test: " + o.toString());
        MuseumType data = (MuseumType) o;
        Intent intent = new Intent(this.getContext(), MuseumActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
