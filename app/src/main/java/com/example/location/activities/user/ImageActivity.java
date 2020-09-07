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
import com.example.location.helpers.ARManager;
import com.example.location.helpers.AnimationManager;
import com.google.android.material.bottomappbar.BottomAppBar;
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
            arManager.create3DModelSFB(anchor);
//            create3DModel(anchor);
        });
    }

    private void setId() {
        fabCamera = findViewById(R.id.btnCamera);
        fabVideo = findViewById(R.id.btnVideo);
        fabSearch = findViewById(R.id.btnSearch);
        fabExitFull = findViewById(R.id.BtnExitFull);
        fabExitFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation hideAnim = AnimationUtils.loadAnimation(ImageActivity.this, R.anim.scale_down);
                hideAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        fabExitFull.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _animationManager.slideUp(bottomAppBar);
                        Animation showAnim = AnimationUtils.loadAnimation(ImageActivity.this, R.anim.scale_up);
                        fabCamera.show();
                        fabSearch.show();
                        fabVideo.show();
                        fabCamera.startAnimation(showAnim);
                        fabSearch.startAnimation(showAnim);
                        fabVideo.startAnimation(showAnim);
                        _animationManager.slideUp(bottomAppBar);
                        isFullScreenMode = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void create3DModel(Anchor anchor) {
        //Log.d("TAG", "create3DModel: "+type);
        ModelRenderable
                .builder()
                .setSource(this, RenderableSource.builder().setSource(this, Uri.parse("aaa.gltf"),
                        RenderableSource.SourceType.GLTF2).setScale(1.0f)
                        .setRecenterMode(RenderableSource.RecenterMode.CENTER).build())
                .build()
                .thenAccept(modelRenderable -> {
                    addModelToScene(anchor, modelRenderable);
                })
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });

    }

    /**
     * Used to add the 3D model created in create3Dmodel to the scene
     *
     * @param anchor
     * @param modelRenderable
     */
    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        // anchorNode will position itself based on anchor
        AnchorNode anchorNode = new AnchorNode(anchor);
        // AnchorNode cannot be zoomed in or moved so a TransformableNode is created where AnchorNode is the parent
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        // Setting the angle of 3D model
        transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 180));
        transformableNode.setParent(anchorNode);
        //adding the model to the transformable node
        transformableNode.setRenderable(modelRenderable);
        //adding this to the scene
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }


}