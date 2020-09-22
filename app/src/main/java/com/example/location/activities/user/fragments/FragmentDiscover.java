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
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.user.ImageGroupActivity;
import com.example.location.adapters.DiscoverAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Museum;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentDiscover extends Fragment implements OnItemClickListener {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    ValueEventListener valueEventListener;
    Context context;
    MainActivity main;
    RecyclerView recyclerView;
    DiscoverAdapter discoverAdapter;
    View view;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Museum> museums = new ArrayList<>();

    public static FragmentDiscover newInstance() {
        FragmentDiscover fragmentDiscover = new FragmentDiscover();
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

    @Override
    public void onStop() {
        super.onStop();
        if ( museumsRef != null ) {
            museumsRef.removeEventListener(valueEventListener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, null);
        getMuseumList();
        return view;
    }

    private void getMuseumList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                museums = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Museum museum = child.getValue(Museum.class);
                    if(museum != null){
                        museums.add(museum);
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
        valueEventListener = eventListener;
        museumsRef.addValueEventListener(eventListener);
    }

    private void SetAdapter() {
        recyclerView = view.findViewById(R.id.recyclerViewDiscover);
        layoutManager=new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        discoverAdapter= new DiscoverAdapter(museums,this);
        recyclerView.setAdapter(discoverAdapter);
    }

    @Override
    public void onItemClick(Object o) {
        Museum data = (Museum) o;
        Intent intent = new Intent(view.getContext(), ImageGroupActivity.class);
        intent.putExtra("museumData", data);
        startActivity(intent);
        Log.d("TAG", "aaaa: "+o.toString());
    }
}
