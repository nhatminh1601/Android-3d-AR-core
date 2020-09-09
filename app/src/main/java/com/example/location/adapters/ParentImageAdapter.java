package com.example.location.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.ParentImage;

import java.util.ArrayList;

public class ParentImageAdapter extends RecyclerView.Adapter<ParentImageAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private OnItemClickListener onItemClickListener;
    ArrayList<ParentImage> parentImages = new ArrayList<>();

    public ParentImageAdapter(ArrayList<ParentImage> parentImages, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.parentImages.addAll(parentImages);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_item_image_grid, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder parentViewHolder, int position) {

        ParentImage parentImage = parentImages.get(position);
        parentViewHolder.parentItemTitle.setText(parentImage.getName());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(parentViewHolder.childRecyclerView.getContext(), 2);
        ImageAdapter childItemAdapter = new ImageAdapter((ArrayList) parentImage.getImages(), onItemClickListener, 1);
        parentViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.childRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.childRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return parentImages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView parentItemTitle;
        private RecyclerView childRecyclerView;

        MyViewHolder(final View itemView) {
            super(itemView);

            parentItemTitle = itemView.findViewById(R.id.parent_item_title);
            childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}
