package com.example.location.activities.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.example.location.R;
import com.example.location.activities.user.fragments.ItemListDialogFragment;
import com.example.location.helpers.ARManager;
import com.example.location.helpers.AnimationManager;
import com.example.location.helpers.TakePhoto;
import com.example.location.model.Image;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private static final double MIN_OPENGL_VERSION = 3.0;
    public ArFragment arFragment;
    ARManager arManager;
    BottomAppBar bottomAppBar;
    FloatingActionButton fabCamera, fabVideo, fabSearch, fabExitFull;
    private boolean isFullScreenMode = false;
    private AnimationManager _animationManager;
    TakePhoto takePhoto;
    private Context _context;
    String imageAnchor = "dinosaur/allosaurus.sfb";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        setId();
        _animationManager = new AnimationManager(this);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setUpBottomAppBar();
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        // This part of the code will be executed when the user taps on a plane
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            arManager = new ARManager(arFragment, this);
            arManager.create3DModelSFB(anchor, imageAnchor);
        });
        _context = this;
        setListFragmentImage();
        takePhoto();
    }

    private void takePhoto() {
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                takePhoto.takePhoto(arFragment.getArSceneView(), _context);
            }
        });
    }

    private void setListFragmentImage() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = ItemListDialogFragment.newInstance();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "list image");
            }
        });
    }

    private void setId() {
        fabCamera = findViewById(R.id.btnCamera);
        fabVideo = findViewById(R.id.btnVideo);
        fabSearch = findViewById(R.id.btnSearch);
        fabExitFull = findViewById(R.id.BtnExitFull);
        takePhoto = new TakePhoto();
        fabExitFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabExitFull.setVisibility(View.INVISIBLE);
                _animationManager.slideUp(bottomAppBar);
                Animation showAnim = AnimationUtils.loadAnimation(ImageActivity.this, R.anim.scale_up);
                fabCamera.show();
                fabSearch.show();
                fabVideo.show();
                fabCamera.startAnimation(showAnim);
                fabSearch.startAnimation(showAnim);
                fabVideo.startAnimation(showAnim);
                isFullScreenMode = false;
            }
        });
    }

    private void setUpBottomAppBar() {
        bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnFull:
                        handleBtnFull();
                        break;
                }
                return false;
            }
        });
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initFullScreenMode(View view) {
        _animationManager.setBottomBarHeight(bottomAppBar.getHeight());

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);

//        spinner.setVisibility(View.GONE);
        showExitFullScreenFab();
    }

    private void showExitFullScreenFab() {
        Animation showAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        fabExitFull.setVisibility(View.VISIBLE);
        fabExitFull.startAnimation(showAnim);
    }

    private void handleBtnFull() {
        Animation hideAnim = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fabCamera.hide();
                fabSearch.hide();
                fabVideo.hide();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabCamera.startAnimation(hideAnim);
        fabSearch.startAnimation(hideAnim);
        fabVideo.startAnimation(hideAnim);
        _animationManager.slideDown(bottomAppBar);
        isFullScreenMode = true;
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
//            activity.finish();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close, menu);
        MenuItem menuItem = menu.findItem(R.id.btnFull);
        return super.onCreateOptionsMenu(menu);
    }


    public void setImage(Image data) {
        imageAnchor = data.getUrl();
    }
}