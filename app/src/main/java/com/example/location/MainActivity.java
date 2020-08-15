package com.example.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.helpers.TempData;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    ImageView imgBg;
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    RecyclerView.LayoutManager layoutManager;
    TempData data;
    ArrayList<Place> dataPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        SetViewId();
        SetAnimationBG();
        SetAdapter();


    }

    private void SetViewId() {
        imgBg = findViewById(R.id.imgBg);
        recyclerView = findViewById(R.id.lvMenu);
    }

    private void SetAdapter() {
        data = new TempData();
        dataPlaces = new ArrayList<>();
        dataPlaces = data.GetData();
        layoutManager= new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter= new MenuAdapter(dataPlaces, this);

        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAnimationBG() {
        AnimationDrawable animationDrawable = (AnimationDrawable) imgBg.getDrawable();
        animationDrawable.setEnterFadeDuration(100);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    @Override
    public void onItemClick(Object o) {
        Place data= (Place) o;
        Log.d("TAG", "onItemClick: "+data.toString() );
    }
}