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
import com.example.location.interfaces.OnItemLongClick;
import com.example.location.model.Image;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ItemListDialogFragment extends BottomSheetDialogFragment implements OnItemClickListener, OnItemLongClick {
    RecyclerView.LayoutManager layoutManager;
    ImageAdapter imageAdapter;
    ArrayList<Image> images;
    ImageActivity imageActivity;
    Context context;
    RecyclerView recyclerView;


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
            images = imageActivity.getImages();
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
        recyclerView = (RecyclerView) view;
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(images, this, this, 2, 1);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public void onItemClick(Object o) {
        getDialog().dismiss();
        Image data = (Image) o;
        imageActivity.setImage(data);
    }

    @Override
    public void onItemLongClick(Object o, View view) {
        Image data = (Image) o;
        Log.d("TAG", "onItemLongClick: " + data.toString());

        new SimpleTooltip.Builder(context)
                .anchorView(view)
                .text((data.getLongDesc() != "" && data.getLongDesc() != null) ? data.getLongDesc() : data.getDesc())
                .gravity(Gravity.TOP)
                .animated(true)
                .build()
                .show();
    }
}