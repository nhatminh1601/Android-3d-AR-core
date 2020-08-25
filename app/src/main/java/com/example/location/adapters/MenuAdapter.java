package com.example.location.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.location.R;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> implements Filterable {
    ArrayList<Place> arr;
    ArrayList<Place> placeAll;
    private OnItemClickListener onItemClickListener;
    int type;

    public MenuAdapter(ArrayList<Place> arr, OnItemClickListener onItemClickListener) {
        this.arr = arr;
        this.onItemClickListener = onItemClickListener;
        type = 0;
        placeAll = new ArrayList<>();
        placeAll.addAll(arr);
    }

    public MenuAdapter(ArrayList<Place> arr, OnItemClickListener onItemClickListener, int type) {
        this.arr = arr;
        this.onItemClickListener = onItemClickListener;
        this.type = type;
        placeAll = new ArrayList<>();
        placeAll.addAll(arr);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_main;
        if (type == 1) {
            layout = R.layout.item_admin;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getDataBind(arr.get(position), holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: " + arr.size());
        return arr.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Place> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(arr);
            } else {
                for (Place item : placeAll) {
                    if (item.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
            arr.addAll((Collection<? extends Place>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView bg;
        TextView title;
        Place place;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.itemCard);
            bg = itemView.findViewById(R.id.imgBg);
            title = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.fede);
                    itemView.startAnimation(animation);
                    onItemClickListener.onItemClick(place);
                }
            });
        }

        public void getDataBind(Place place, Context context) {
            this.place = place;
            title.setText(place.getName());
            if (!place.getUrl().isEmpty()){
                Glide.with(context).load(place.getUrl()).placeholder(R.drawable.bg2).error(R.drawable.bg2).into(bg);
            }
        }
    }
}
