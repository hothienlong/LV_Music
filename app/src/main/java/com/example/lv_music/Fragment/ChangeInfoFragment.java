package com.example.lv_music.Fragment;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lv_music.Activity.AccountActivity;
import com.example.lv_music.R;
import com.example.lv_music.databinding.FragmentChangeInfoBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class ChangeInfoFragment extends Fragment {
    private static final int REQUEST_CODE_CAMERA = 123;
    private static final int REQUEST_CODE_GALLERY = 234;
    FragmentChangeInfoBinding mBinding;
    String mTitle;
    String newName;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extra = getArguments();
        mTitle = extra.getString(AccountActivity.KEY_TITLE);
        mBinding.tvTitle.setText(mTitle);
        mBinding.edtNew.setHint("Nhập " + mTitle.substring(4) + " mới");
        mBinding.btnUpdate.setOnClickListener(v -> {
            newName = mBinding.edtNew.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(getActivity(), "Hãy nhập " + mTitle.substring(4) + " mới", Toast.LENGTH_SHORT).show();
                return;
            }
//            else if (user != null) {
//                switch (mTitle) {
//                    case "Đổi tên":
//                        updateName(newName);
//                        break;
//                    case "Đổi email":
//                        popUpConfirmDialog(0);
//                        //https://firebase.google.com/docs/auth/android/manage-users#re-authenticate_a_user
//                        break;
//                    case "Đổi avatar":
//
//                        break;
//                    case "Đổi password":
//                        popUpConfirmDialog(1);
//                        break;
//                }
//            }
        });

    }

//    private void popUpConfirmDialog(int i) {
//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//        View promptView = layoutInflater.inflate(R.layout.confirm_info_dialog, null);
//        AlertDialog.Builder confirmDialogBuilder =
//                new AlertDialog.Builder(getActivity(), R.style.RoundedCornersDialog);
//        confirmDialogBuilder.setView(promptView);
//        AlertDialog confirmDialog = confirmDialogBuilder.show();
//        Button btnConfirm = promptView.findViewById(R.id.btn_dialog_yes);
//        Button btnCancel = promptView.findViewById(R.id.btn_dialog_no);
//        EditText edtEmail = promptView.findViewById(R.id.edtEmail);
//        EditText edtPassword = promptView.findViewById(R.id.edtPassword);
//
//        btnConfirm.setOnClickListener(view -> {
//            String email = edtEmail.getText().toString().trim();
//            String password = edtPassword.getText().toString().trim();
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(getActivity(), "Hãy nhập thông tin xác nhận!", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                if (i == 0) {
//                    updateEmail(newName, email, password);
//                } else {
//                    updatePassword(newName, email, password);
//                }
//                confirmDialog.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(view -> {
//            confirmDialog.dismiss();
//        });
//
//    }
//
//    private void updatePassword(String newPassword, String email, String password) {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential credential = EmailAuthProvider
//                .getCredential(email, password);
//
//// Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d("ABC", "User re-authenticated.");
//                        currentUser.updatePassword(newPassword)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getActivity(), "Cập nhật password thành công", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                            Log.d("ABC", "Error: " + task.getException().getMessage() + email);
//                                        }
//                                    }
//                                });
//                    }
//                });
//    }
//
//    private void updateEmail(String newEmail, String email, String password) {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        AuthCredential credential = EmailAuthProvider
//                .getCredential(email, password);
//
//// Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d("ABC", "User re-authenticated.");
//                        currentUser.updateEmail(newEmail)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getActivity(), "Cập nhật email thành công", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                });
//    }
//
//    private void updateName(String newName) {
//        UserProfileChangeRequest profileUpdates =
//                new UserProfileChangeRequest.Builder().setDisplayName(newName).build();
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentChangeInfoBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AccountActivity.mLlAccount.setVisibility(View.VISIBLE);
    }
}