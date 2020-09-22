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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.user.ImageActivity;
import com.example.location.adapters.FavouriteAdapter;
import com.example.location.dummy.ImageGroupDummy;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.example.location.model.ImageGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentFavourite extends Fragment implements OnItemClickListener {
    DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference("images");
    ArrayList<Image> images = new ArrayList<>();
    Context context;
    MainActivity main;
    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> favorites;

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
            favorites = main.getFavorites();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, null);
        getImageList();

        return view;
    }

    private void getImageList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image image = child.getValue(Image.class);
                    if (image != null) {
                        images.add(image);
                    }
                }
                SetAdapter();
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        imageRef.addValueEventListener(eventListener);
    }

    private void SetAdapter() {
        ArrayList<Image> arrayList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (favorites.contains(images.get(i).getGroup().toString())) {
                arrayList.add(images.get(i));
            }
        }

        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        layoutManager= new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favouriteAdapter= new FavouriteAdapter(arrayList,this);
        recyclerView.setAdapter(favouriteAdapter);
    }

    public void updateAdapter(ArrayList<String> favorites) {
        ArrayList<Image> arrayList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (favorites.contains(images.get(i).getGroup().toString())) {
                arrayList.add(images.get(i));
            }
        }

        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        layoutManager= new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        favouriteAdapter= new FavouriteAdapter(arrayList,this);
        recyclerView.setAdapter(favouriteAdapter);
        favouriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Object o) {
        Image data = (Image) o;
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
