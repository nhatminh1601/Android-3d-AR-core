package com.example.location.helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.location.activities.user.ImageActivity;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ARManager {
    public ArFragment arFragment;
    Context context;
    ImageActivity activity;

    public ARManager(ArFragment arFragment, Context context) {
        this.arFragment = arFragment;
        this.context = context;
        this.activity = (ImageActivity) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void create3DModel(Anchor anchor, String name) {
        //Log.d("TAG", "create3DModel: "+type);
        ModelRenderable
                .builder()
                .setSource(context, RenderableSource.builder().setSource(context, Uri.parse(name),
                        RenderableSource.SourceType.GLTF2).setScale(1.0f)
                        .setRecenterMode(RenderableSource.RecenterMode.CENTER).build())
                .build()
                .thenAccept(modelRenderable -> {
                    addModelToScene(anchor, modelRenderable, name);
                })
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void create3DModelSFB(Anchor anchor, String name) {
        //Log.d("TAG", "create3DModel: "+type);
        ModelRenderable
                .builder()
                .setSource(context, Uri.parse(name))
                .build()
                .thenAccept(modelRenderable -> {
                    addModelToScene(anchor, modelRenderable, name);
                })
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable, String name) {
        // anchorNode will position itself based on anchor
        AnchorNode anchorNode = new AnchorNode(anchor);
        // AnchorNode cannot be zoomed in or moved so a TransformableNode is created where AnchorNode is the parent
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        // Setting the angle of 3D model
//        transformableNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 180));
        transformableNode.setParent(anchorNode);
        //adding the model to the transformable node
        transformableNode.setRenderable(modelRenderable);
        //adding this to the scene
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();

        transformableNode.setOnTouchListener((hitResult, motionEvent) -> {
            activity.showOptionsMenu(anchorNode, name);
            return true;
        });
    }
}
