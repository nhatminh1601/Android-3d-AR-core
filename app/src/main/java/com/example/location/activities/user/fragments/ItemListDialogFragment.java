package com.example.location.activities.user.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.location.R;
import com.example.location.activities.user.ImageActivity;
import com.example.location.adapters.ImageAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ItemListDialogFragment extends BottomSheetDialogFragment implements OnItemClickListener {
    RecyclerView.LayoutManager layoutManager;
    ImageAdapter imageAdapter;
    ArrayList<Image> images;
    ImageActivity imageActivity;
    Context context;


    // TODO: Customize parameters
    public static ItemListDialogFragment newInstance() {
        final ItemListDialogFragment fragment = new ItemListDialogFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getContext();
            imageActivity = (ImageActivity) getActivity();
        } catch (IllegalAccessError e) {
            throw new IllegalStateException("ImageAction must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        images = new ArrayList<>();
        images.add(new Image(1, "Hình 1", "aaa.gltf", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/carnotaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/ceratosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/cryolophosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/diabloceratops.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/diplodocus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/fossilcoelophysis.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/fossilcoelophysissecond.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/fossilstegosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/gorgosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/skeletondiplodocus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/skeletongomphothereskull.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "dinosaur/skeletonteratophoneus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        final RecyclerView recyclerView = (RecyclerView) view;
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(images, this, 2);
        recyclerView.setAdapter(imageAdapter);

    }


    @Override
    public void onItemClick(Object o) {
        getDialog().dismiss();
        Image data = (Image) o;
        imageActivity.setImage(data);
    }
}