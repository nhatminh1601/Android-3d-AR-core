package com.example.location;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.location.models.Place;
import com.example.location.models.Type;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.HashMap;

public class CameraUserActivity extends AppCompatActivity {
    private CloudAnchorFragment arFragment;
    private static final double MIN_OPENGL_VERSION = 3.0;
    private Anchor anchor;
    private AnchorNode anchorNode;

    Place data;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_user);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            data = (Place) extras.getSerializable("item");
            Log.d("TAG", "onCreate: " + data.toString());
        }
        arFragment = (CloudAnchorFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        btnBack= findViewById(R.id.btnBack);
        ImageButton btLoad = findViewById(R.id.btnLoad);
        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbbb", "onClick: " + data.getAnchors().size());
                for (int i = 0; i < data.getAnchors().size(); i++) {
                    String anchorId = data.getAnchors().get(i).getId();
                    if (anchorId.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No anchor Id found", Toast.LENGTH_LONG).show();
                        return;
                    }else{
                        Anchor resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor(anchorId);
                        create3DModel(resolvedAnchor,data.getAnchors().get(i));
                    }
                }
            }
        });

        goBack();
    }

    private void goBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e("TAG", "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e("TAG", "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    /**
     * Used to build a 3D model
     *
     * @param anchor
     */
    private void create3DModel(Anchor anchor, com.example.location.models.Anchor anchor1) {
        Log.d("dđ", "create3DModel: "+anchor);
        ModelRenderable
                .builder()
                .setSource(this, RenderableSource.builder().setSource(this, Uri.parse(anchor1.getType().getUrl()),
                        RenderableSource.SourceType.GLTF2).setScale(0.1f).setRecenterMode(RenderableSource.RecenterMode.ROOT).build())
                .build()
                .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
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
        anchorNode = new AnchorNode(anchor);
        // AnchorNode cannot be zoomed in or moved so a TransformableNode is created where AnchorNode is the parent
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        // Setting the angle of 3D model
        transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 180));
        transformableNode.setParent(anchorNode);
        //adding the model to the transformable node
        transformableNode.setRenderable(modelRenderable);
        //adding this to the scene
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }

}