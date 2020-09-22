package com.example.location.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.location.R;
import com.example.location.common.VNCharacterUtils;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.ImageGroup;
import com.example.location.model.Museum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageGroupAdapter extends RecyclerView.Adapter<ImageGroupAdapter.MyViewHolder> implements Filterable {
    ArrayList<ImageGroup> arr;
    ArrayList<ImageGroup> imageGroups;
    private OnItemClickListener onItemClickListener;

    public ImageGroupAdapter(ArrayList<ImageGroup> arr, OnItemClickListener onItemClickListener) {
        this.arr = arr;
        this.onItemClickListener = onItemClickListener;
        imageGroups = new ArrayList<>();
        imageGroups.addAll(arr);
    }

    @NonNull
    @Override
    public ImageGroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGroupAdapter.MyViewHolder holder, int position) {
        holder.getDataBind(arr.get(position), holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ImageGroup> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0 || charSequence == "") {
                filteredList.addAll(imageGroups);
            } else {
                for (ImageGroup item : imageGroups) {
                    if (VNCharacterUtils.removeAccent(item.getDescription()).toLowerCase().contains(VNCharacterUtils.removeAccent(charSequence.toString()).toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arr.clear();
            arr.addAll((Collection<? extends ImageGroup>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView title;
        TextView description;
        ImageGroup imageGroup;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.background);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(imageGroup);
                }
            });
        }

        public void getDataBind(ImageGroup imageGroup, Context context) {
            this.imageGroup = imageGroup;
            bg.setImageDrawable(context.getResources().getDrawable(imageGroup.getImage()));
            title.setText(imageGroup.getName());
            description.setText(imageGroup.getDescription());

        }

    }
}
