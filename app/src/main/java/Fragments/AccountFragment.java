package Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bigbasket_user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    //database
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    //views
    private ImageView profileIv;
    private TextInputEditText nameEt, phoneEt, addressEt;
    private Spinner pinCodeSpinner;
    private Button saveBtn;
    ProgressDialog mProgressDialog;
    //permission request code
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;
    private static final int IMAGE_PICK_GALLERY_CODE =300;
    private static final int IMAGE_PICK_CAMERA_CODE =400;

    private String[] cameraPermission;
    private String[] storagePermission;
    ArrayAdapter<String> spinnerAdapter;
    private String[] pinCodeArray;
    private String[] pinCodeAreaArray;
    private Uri image_uri;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //init database
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        //init views
        profileIv = view.findViewById(R.id.profileImage);
        nameEt = view.findViewById(R.id.nameEt);
        phoneEt = view.findViewById(R.id.phoneEt);
        addressEt = view.findViewById(R.id.addressEt);
        pinCodeSpinner = view.findViewById(R.id.pinCodeSpinner);
        saveBtn = view.findViewById(R.id.saveBtn);
        pinCodeArray = new String[]{"491001", "490020", "490006", "490001", "490009", "490022", "490023", "490029"};
        pinCodeAreaArray = new String[]{"491001(Durg city)", "490020(Nehru Nagar)", "490006(Risali)", "490001 (Adarsh Nagar)", "490009 (Hudco)", "490022 (Jawahar Nagar)", "490023 (Supela)", "490029 (Surya Vihar)"};
        spinnerAdapter = new ArrayAdapter<>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, pinCodeAreaArray);
        pinCodeSpinner.setAdapter(spinnerAdapter);
        //init progress dialog
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
        //init permission array
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        loadDetails();
        //proile image view click listener
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });
        //update button view click listener
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });

        return view;
    }

    private void loadDetails() {

        DocumentReference docRef = fstore.collection("Users").document(mAuth.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nameEt.setText(documentSnapshot.getString("name"));
                phoneEt.setText(documentSnapshot.getString("phone"));
                addressEt.setText(documentSnapshot.getString("address"));
                String pinCode = documentSnapshot.getString("pinCode");
                pinCodeSpinner.setSelection(Arrays.asList(pinCodeAreaArray).indexOf(pinCode));
                try {
                    if (!documentSnapshot.getString("profileImage").isEmpty()) {
                        Picasso.get().load(documentSnapshot.getString("profileImage")).placeholder(R.drawable.ic_account).into(profileIv);
                    } else {
                        Picasso.get().load(R.drawable.ic_account).placeholder(R.drawable.ic_account).into(profileIv);
                    }
                } catch (Exception z) {
                    Picasso.get().load(R.drawable.ic_account).placeholder(R.drawable.ic_account).into(profileIv);
                }
            }
        });
    }

    String name, phone, address;
    private void inputData() {

        name = nameEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();
        address = addressEt.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Name not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length()<9){
            Toast.makeText(getActivity(), "Please enter valid phone number...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address) || address.length()<8){
            Toast.makeText(getActivity(), "Please enter valid Address...", Toast.LENGTH_SHORT).show();
            return;
        }
        updateDetails();
    }

    private void updateDetails() {

        mProgressDialog.setMessage("Update product...");
        mProgressDialog.show();
        int code = Arrays.asList(pinCodeAreaArray).indexOf(pinCodeSpinner.getSelectedItem().toString());
        final String pinCode = pinCodeArray[code];
        Log.d("MAGE_PICK_CAMERA_CODE", image_uri+"");
        if (image_uri==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("phone", phone);
            map.put("address", address);
            map.put("pinCode", pinCode);
            DocumentReference docRef = fstore.collection("Users").document(mAuth.getUid());
            docRef.set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getActivity(), "Details Updated...", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            String fileAndPath = "profile_image/"+ ""+ mAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(fileAndPath);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask =  taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downlodeImageUri = uriTask.getResult();

                            if (uriTask.isSuccessful()){
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", name);
                                map.put("phone", phone);
                                map.put("address", address);
                                map.put("pinCode", pinCode);
                                map.put("profileImage", ""+downlodeImageUri);

                                DocumentReference docRef = fstore.collection("Users").document(mAuth.getUid());
                                docRef.update(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mProgressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Details Updated...", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                                        Log.d("Error", e+"");
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", e+"");
                }
            });;
        }
    }

    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            if (!checkCemeraPermission()){
                                requestCemeraPermission();
                            }
                            else {
                                pickFromCamera();
                            }
                        }else if (which==1){
                            if (!checkStoragePermission()){
                                requestStoragePermission();
                            }
                            else {
                                pickFromGallery();
                            }
                        }
                    }
                }).create().show();
    }

    private boolean checkCemeraPermission(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {

        Intent gallleryIntent = new Intent(Intent.ACTION_PICK);
        gallleryIntent.setType("image/*");
        startActivityForResult(gallleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp_Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description");

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Log.d("Image Uri", image_uri+"");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestStoragePermission(){
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private void requestCemeraPermission(){
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length >  0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(getActivity(), "Please enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{

                if (grantResults.length >  0){
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(getActivity(), "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                profileIv.setImageURI(image_uri);
                Log.d("MAGE_PICK_CAMERA_CODE", image_uri+"");
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){

                profileIv.setImageURI(image_uri);
                Log.d("MAGE_PICK_CAMERA_CODE", image_uri+"");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
