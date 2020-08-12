package com.example.location.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {
    ArrayList<Menu> arr;
    private OnItemClickListener onItemClickListener;

    public MenuAdapter(ArrayList<Menu> arr, OnItemClickListener onItemClickListener) {
        this.arr = arr;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getDataBind(arr.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: " + arr.size());
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView title;
        Menu menu;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.imgBg);
            title = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.fede);
                    itemView.startAnimation(animation);
                    onItemClickListener.onItemClick(menu);
                }
            });
        }

        public void getDataBind(Menu menu) {
            this.menu = menu;
            title.setText(menu.getName());
        }
    }
}
