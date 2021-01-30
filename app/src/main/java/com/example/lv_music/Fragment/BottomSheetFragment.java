package com.example.lv_music.Fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lv_music.R;
import com.example.lv_music.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class BottomSheetFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_CAMERA = 123;
    private static final int REQUEST_CODE_GALLERY = 234;
    FragmentBottomSheetBinding mBinding;
    onImageChosenListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentBottomSheetBinding.inflate(inflater,container,false);
        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.tvOpenCamera.setOnClickListener(view-> {
            openCamera();
        });

        mBinding.tvOpenGallery.setOnClickListener(view -> {
            openGallery();
        });
    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void openGallery() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        } else {
            EasyPermissions.requestPermissions(this,
                    "Cấp quyền truy cập gallery",
                    REQUEST_CODE_GALLERY,
                    perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d("ABC", "Permission denied");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            mBinding.ivAvatar.setImageBitmap(bitmap);
            mListener.onCameraImageChosen(data);
        }

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            mListener.onGalleryImageChosen(uri);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                mBinding.ivAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    public void openCamera() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), perms)) {
            // Already have permission, do the thing
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } else {
            // Do not have permissions, request them now
            Log.d("ABC", "request permission");
            EasyPermissions.requestPermissions(this,
                    "Cấp quyền truy cập camera",
                    REQUEST_CODE_CAMERA,
                    perms);
        }
    }

    public interface onImageChosenListener{
        void onCameraImageChosen(Intent data);
        void onGalleryImageChosen(Uri uri);
    }

    public void setOnImageChosenListener(onImageChosenListener listener){
        mListener = listener;
    }

}