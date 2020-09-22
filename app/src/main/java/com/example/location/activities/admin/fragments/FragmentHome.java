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

import com.example.location.R;
import com.example.location.activities.admin.AdminActivity;
import com.example.location.interfaces.OnItemClickListener;

public class FragmentHome extends Fragment implements OnItemClickListener {
    View view;
    Context context;
    AdminActivity main;

    public static FragmentHome newInstance() {
        FragmentHome fragmentHome = new FragmentHome();
        return fragmentHome;
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
        view = inflater.inflate(R.layout.fragment_museum, null);
        return view;
    }

    @Override
    public void onItemClick(Object o) {
        Log.d("TAG", "test: "+o.toString());
    }
}
