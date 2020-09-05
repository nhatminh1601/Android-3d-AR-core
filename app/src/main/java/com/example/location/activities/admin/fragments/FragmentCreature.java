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
import com.example.location.activities.admin.AdminActivity;
import com.example.location.adapters.ImageGroupAdapter;
import com.example.location.dummy.ImageGroupDummy;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.ImageGroup;
import com.example.location.model.Museum;

import java.util.ArrayList;
import java.util.List;

public class FragmentCreature extends Fragment implements OnItemClickListener {
    Context context;
    AdminActivity main;
    RecyclerView recyclerView;
    ImageGroupAdapter imageGroupAdapter;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ImageGroup> imageGroups = new ArrayList<>();

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
        view = inflater.inflate(R.layout.fragment_creature, null);
        SetAdapter();
        return view;
    }

    private void SetAdapter() {
        recyclerView = view.findViewById(R.id.recyclerViewDiscover);
        layoutManager=new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        imageGroupAdapter= new ImageGroupAdapter(imageGroups,this);
        recyclerView.setAdapter(imageGroupAdapter);
    }

    public void renewAdapter(Museum museum) {
        setImageGroup(museum);
        recyclerView = view.findViewById(R.id.recyclerViewDiscover);
        layoutManager=new GridLayoutManager(view.getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        imageGroupAdapter= new ImageGroupAdapter(imageGroups,this);
        recyclerView.setAdapter(imageGroupAdapter);
        imageGroupAdapter.notifyDataSetChanged();
    }

    private void setImageGroup(Museum museum) {
        if (museum == null || museum.getImages() == null) {
            return;
        }

        ImageGroupDummy imageGroupDummy = new ImageGroupDummy();
        List<ImageGroup> list = imageGroupDummy.list();
        for (int i = 0; i < list.size(); i++) {
            if (museum.getImages().contains(list.get(i).getId())) {
                imageGroups.add(list.get(i));
            }
        }
    }

    @Override
    public void onItemClick(Object o) {
        Log.d("TAG", "aaaa: "+o.toString());
    }
}
