package com.example.location.activities.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.CamcorderProfile;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.location.helpers.VideoRecorder;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference("images");
    private static final String TAG = "TAG";
    private static final double MIN_OPENGL_VERSION = 3.0;
    private static final int CAMERA_PERMISSION_CODE = 100;
    public ArFragment arFragment;
    ARManager arManager;
    BottomAppBar bottomAppBar;
    FloatingActionButton fabCamera, fabVideo, fabSearch, fabExitFull, fabClose, fabInfo, fabRemove;
    private boolean isFullScreenMode = false;
    private AnimationManager _animationManager;
    TakePhoto takePhoto;
    private Context _context;
    String imageAnchor = "dinosaur/allosaurus.sfb";
    CoordinatorLayout main_content;
    VideoRecorder videoRecorder;
    Image image;
    ArrayList<Image> images = new ArrayList<>();

    Image choosingImage;
    AnchorNode choosingAnchorNode;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        image = (Image) getIntent().getSerializableExtra("data");
        setImageAnchor(image);
        getImageList();
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
            if (imageAnchor.indexOf(".sfb") == -1) {
                arManager.create3DModel(anchor, imageAnchor);
            } else {
                arManager.create3DModelSFB(anchor, imageAnchor);
            }

        });
        _context = this;

        setListFragmentImage();
        takePhoto();
        Recording();
    }

    private void getImageList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image img = child.getValue(Image.class);
                    if (img != null && img.getGroup().equals(image.getGroup())) {
                        images.add(img);
                    }
                }
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        imageRef.addValueEventListener(eventListener);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    private void setImageAnchor(Image image) {
        Log.d(TAG, "setImageAnchor: "+image);
        if (image.getUrl() != null && !image.getUrl().isEmpty()) {
            imageAnchor = image.getUrl();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Recording() {
        videoRecorder = new VideoRecorder();
        int orientation = getResources().getConfiguration().orientation;
        videoRecorder.setVideoQuality(CamcorderProfile.QUALITY_2160P, orientation);
        videoRecorder.setSceneView(arFragment.getArSceneView());
        fabVideo.setOnClickListener(this::toggleRecording);
        fabVideo.setEnabled(true);
        //fabVideo.setImageResource(R.drawable.camera);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void toggleRecording(View unusedView) {
        boolean recording = videoRecorder.onToggleRecord();
        if (recording) {
            fabVideo.setImageResource(R.drawable.videocam_off);
        } else {
            fabVideo.setImageResource(R.drawable.videocam);
            String videoPath = videoRecorder.getVideoPath().getAbsolutePath();

            // Send  notification of updated content.
            ContentValues values = new ContentValues();
            values.put(MediaStore.Video.Media.TITLE, "Sceneform Video");
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            values.put(MediaStore.Video.Media.DATA, videoPath);
            getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            File photoFile = new File(videoPath);
            Uri photoURI = FileProvider.getUriForFile(this,
                    this.getPackageName() + ".ar.codelab.name.provider",
                    photoFile);
            Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
            intent.setDataAndType(photoURI, "video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }

    private void takePhoto() {
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                takePhoto.takePhoto(arFragment.getArSceneView(), _context, main_content);
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
        main_content = findViewById(R.id.main_content);
        fabCamera = findViewById(R.id.btnCamera);
        fabVideo = findViewById(R.id.btnVideo);
        fabSearch = findViewById(R.id.btnSearch);
        fabExitFull = findViewById(R.id.BtnExitFull);
        fabClose = findViewById(R.id.btnClose);
        fabInfo = findViewById(R.id.btnInfo);
        fabRemove = findViewById(R.id.btnRemove);
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
        fabClose.setVisibility(View.INVISIBLE);
        fabRemove.setVisibility(View.INVISIBLE);
        fabInfo.setVisibility(View.INVISIBLE);
        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClose.setVisibility(View.INVISIBLE);
                fabRemove.setVisibility(View.INVISIBLE);
                fabInfo.setVisibility(View.INVISIBLE);
            }
        });
        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo();
            }
        });
        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAnchorNode();
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

    private static final String REQUIRED_PERMISSIONS[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    /**
     * Check to see we have the necessary permissions for this app.
     */
    public static boolean hasCameraPermission(Activity activity) {
        for (String p : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, p) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check to see we have the necessary permissions for this app,
     * and ask for them if we don't.
     */
    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS,
                CAMERA_PERMISSION_CODE);
    }

    /**
     * Check to see if we need to show the rationale for this permission.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity) {
        for (String p : REQUIRED_PERMISSIONS) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, p)) {
                return true;
            }
        }
        return false;
    }

    public void showOptionsMenu(AnchorNode anchorNode, String url) {
        choosingAnchorNode = anchorNode;
        choosingImage = getImageByUrl(url);
        fabClose.setVisibility(View.VISIBLE);
        fabInfo.setVisibility(View.VISIBLE);
        fabRemove.setVisibility(View.VISIBLE);
    }

    private Image getImageByUrl(String url) {
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getUrl().equals(url)) {
                return images.get(i);
            }
        }
        return null;
    }

    private void showInfo() {
        if (choosingImage != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(_context);
            builder.setMessage(choosingImage.getDesc()).show();
        }
    }

    private void removeAnchorNode() {
        if (choosingAnchorNode != null) {
            arFragment.getArSceneView().getScene().removeChild(choosingAnchorNode);
            choosingAnchorNode.getAnchor().detach();
            choosingAnchorNode.setParent(null);
        }
    }
}