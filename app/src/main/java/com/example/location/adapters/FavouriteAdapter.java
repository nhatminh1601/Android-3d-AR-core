package com.example.location.adapters;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.common.VNCharacterUtils;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> implements Filterable {
    ArrayList<Image> arr;
    ArrayList<Image> imageAll;
    private OnItemClickListener onItemClickListener;

    public FavouriteAdapter(ArrayList<Image> arr, OnItemClickListener onItemClickListener) {
        this.arr = arr;
        this.onItemClickListener = onItemClickListener;
        imageAll = new ArrayList<>();
        imageAll.addAll(arr);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getDataBind(arr.get(position), holder.itemView.getContext());
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Image> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0 || charSequence == "") {
                filteredList.addAll(imageAll);
            } else {
                for (Image item : imageAll) {
                    if (VNCharacterUtils.removeAccent(item.getDesc()).toLowerCase().contains(VNCharacterUtils.removeAccent(charSequence.toString()).toLowerCase())) {
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
            arr.addAll((Collection<? extends Image>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView title;
        TextView description;
        Image image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bg=itemView.findViewById(R.id.background);
            description=itemView.findViewById(R.id.description);
            title= itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation myAnim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.click);
                    itemView.startAnimation(myAnim);
                    onItemClickListener.onItemClick(image);
                }
            });
        }

        public void getDataBind(Image image, Context context) {
            this.image=image;
            title.setText(image.getName());
            description.setText(image.getDesc());
            bg.setImageDrawable(context.getResources().getDrawable(image.getImage()));
        }
    }
}
