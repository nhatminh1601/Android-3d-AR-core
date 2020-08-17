package com.example.location;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.location.models.Place;
import com.example.location.models.Storage;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CameraAdminActivity extends AppCompatActivity {
    private CloudAnchorFragment arFragment;
    private ArrayList anchorList;
    Button btnSave;
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");
    ArrayList<Place> dataPlaces = new ArrayList<>();
    Place data;

    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED
    }

    private Anchor anchor;
    private AnchorNode anchorNode;
    private AppAnchorState appAnchorState = AppAnchorState.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            data = (Place) extras.getSerializable("item");
            Log.d("TAG", "onCreate: " + data.toString());
        }
        setContentView(R.layout.activity_camera_admin);
        getPlaces();
        btnSave = findViewById(R.id.btnSave);
        anchorList = new ArrayList();
        // Context of the entire application is passed on to TinyDB
        Storage storage = new Storage(getApplicationContext());
//        Button resolve = findViewById(R.id.resolve);

        arFragment = (CloudAnchorFragment) getSupportFragmentManager().findFragmentById(R.id.arAdminFragment);
        // This part of the code will be executed when the user taps on a plane
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            //Will be used in Admin Mode

            Log.d("HIT_RESULT:", hitResult.toString());
            // Used to render 3D model on the top of this anchor
            anchor = arFragment.getArSceneView().getSession().hostCloudAnchor(hitResult.createAnchor());
            appAnchorState = AppAnchorState.HOSTING;
            showToast("Adding Arrow to the scene");
            Log.d("TAG", "onCreate: " + anchor.toString());
            create3DModel(anchor);

        });

        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

            if (appAnchorState != AppAnchorState.HOSTING)
                return;
            Anchor.CloudAnchorState cloudAnchorState = anchor.getCloudAnchorState();

            if (cloudAnchorState.isError()) {
                showToast(cloudAnchorState.toString());
            } else if (cloudAnchorState == Anchor.CloudAnchorState.SUCCESS) {
                appAnchorState = AppAnchorState.HOSTED;
                String anchorId = anchor.getCloudAnchorId();
                anchorList.add(anchorId);
                storage.addListString("data", anchorList);


                showToast("Anchor hosted successfully. Anchor Id: " + anchorId);
            }

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePlaces(data.getId(), anchorList);
            }
        });
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    /**
     * Used to build a 3D model
     *
     * @param anchor
     */
    private void create3DModel(Anchor anchor) {
        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("model-triangulated.sfb"))
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

    private void updatePlaces(int id, ArrayList<String> anchorList) {
        if (!anchorList.isEmpty()) {
            int index = -1;
            boolean isExist = false;
            for (int i = 0; i < dataPlaces.size(); i++) {
                if (id == dataPlaces.get(i).getId()) {
                    index = i;
                    isExist = true;
                    break;
                }
            }

            if (isExist) {

                dataPlaces.get(index).setAnchors(anchorList);
            }
            placesRef.setValue(dataPlaces);

        }

    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataPlaces.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Place place = child.getValue(Place.class);
                    if (place != null) {
                        dataPlaces.add(place);
                    }
                }
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}