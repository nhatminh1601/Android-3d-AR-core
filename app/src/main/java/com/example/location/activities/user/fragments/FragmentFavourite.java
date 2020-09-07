package com.example.location.activities.user.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.adapters.FavouriteAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;

import java.util.ArrayList;

public class FragmentFavourite extends Fragment implements OnItemClickListener {
    Context context;
    MainActivity main;
    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Image> images;

    public static FragmentFavourite newInstance() {
        FragmentFavourite fragmentDiscover = new FragmentFavourite();
        return fragmentDiscover;
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
         view = inflater.inflate(R.layout.fragment_favourite, null);
         SetAdapter();
        return view;
    }

    private void SetAdapter() {
        images= new ArrayList<>();
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));
        images.add(new Image(1,"Hình 1","test", "hình ảnh phong cảnh",1,R.drawable.khampha,1));

        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        layoutManager= new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favouriteAdapter= new FavouriteAdapter(images,this);
        recyclerView.setAdapter(favouriteAdapter);
    }

    @Override
    public void onItemClick(Object o) {

    }
}
