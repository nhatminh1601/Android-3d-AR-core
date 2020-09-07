package com.example.location.activities.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.location.R;
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
    private Anchor anchor;
    private TransformableNode anchorNode;
    private ModelRenderable zeroRenderable;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        // This part of the code will be executed when the user taps on a plane
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {


            Anchor anchor = hitResult.createAnchor();
            create3DModel(anchor);
        });
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
            activity.finish();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close, menu);
        MenuItem menuItem = menu.findItem(R.id.btnClose);
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