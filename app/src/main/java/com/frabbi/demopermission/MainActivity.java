package com.frabbi.demopermission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.frabbi.demopermission.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabCover.setOnClickListener(view -> {
         //   selectImage();
            final CharSequence[] item = { "Camera", "Gallery","Cancel"};
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Choose")
                    .setItems(item,(dialogInterface, i) -> {
                        if(item[i].equals(item[0])){
                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,1);
                        }
                        if(item[i].equals(item[1])){
                            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,2);


                        }
                        if(item[i].equals(item[2])){
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();

        });

        binding.fabProfile.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Profile pic", Toast.LENGTH_LONG).show();
        });


    }

    private void selectImage() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, REQUEST_PERMISSION);
        } else {
            final CharSequence[] item = { "Camera", "Gallery","Cancel"};
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Choose")
                    .setItems(item,(dialogInterface, i) -> {
                        if(item[i].equals(item[0])){
                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,1);
                        }
                        if(item[i].equals(item[1])){
                            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,2);


                        }
                        if(item[i].equals(item[2])){
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private void requestStoragePermissions() {
        /* **************EXTERNAL STORAGE******************* */
        if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA))
        ) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of ......")
                    .setPositiveButton("ok", (dialogInterface, i) -> {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        }, REQUEST_PERMISSION);
                    })
                    .setNegativeButton("cancel", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, REQUEST_PERMISSION);
        }
        /* **************CAMERA******************* */
/*
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed CAM")
                    .setMessage("This permission is needed because of ......")
                    .setPositiveButton("ok", (dialogInterface, i) -> {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        }, REQUEST_PERMISSION);
                    })
                    .setNegativeButton("cancle", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, REQUEST_PERMISSION);
        }*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    // Toast.makeText(this,"Permission  MEDIA is not granted",Toast.LENGTH_LONG).show();

                }


                return;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
      //  requestStoragePermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 1){
                Bitmap pic = (Bitmap) data.getExtras().get("data");
                binding.coverPicId.setImageBitmap(pic);
            }
            if (requestCode == 2) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}