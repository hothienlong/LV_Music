package com.example.lv_music.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lv_music.Fragment.BottomSheetFragment;
import com.example.lv_music.Fragment.ChangeInfoFragment;
import com.example.lv_music.R;
import com.example.lv_music.databinding.ActivityAccountBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AccountActivity extends AppCompatActivity {
    public static View mLlAccount;
    ActivityAccountBinding mBinding;
    View.OnClickListener mOnClickListener;
    public static final String KEY_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.tbAccount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.tbAccount.setNavigationOnClickListener(view ->
                finish()
        );

        mLlAccount = mBinding.llAccount;
        initOnClickListener();

        Toast.makeText(this, "Tính năng đang được phát triển", Toast.LENGTH_SHORT).show();
    }

    private void initOnClickListener() {
        mOnClickListener = v -> {
            Bundle bundle = new Bundle();
            String title = "";
            switch (v.getId()){
                case R.id.rlName:
                    title = "Đổi tên";
                    break;
                case R.id.rlEmail:
                    title = "Đổi email";
                    break;
                case R.id.rlAvatar:
                    title = "Đổi avatar";
                    break;
                case R.id.rlPassword:
                    title = "Đổi password";
                    break;
            }
            bundle.putString(KEY_TITLE,title);
            mBinding.llAccount.setVisibility(View.GONE);
            ChangeInfoFragment frmChangeInfo = new ChangeInfoFragment();
            frmChangeInfo.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.llAccountMain,frmChangeInfo)
//                    .add(R.id.llAccount,frmChangeInfo)
                    .addToBackStack(null)
                    .commit();
        };


        mBinding.rlName.setOnClickListener(mOnClickListener);
        mBinding.rlEmail.setOnClickListener(mOnClickListener);
        mBinding.rlPassword.setOnClickListener(mOnClickListener);
        mBinding.rlAvatar.setOnClickListener(view ->{
            BottomSheetFragment fragmentBottomSheet = new BottomSheetFragment();
            fragmentBottomSheet.show(this.getSupportFragmentManager(),null);
            fragmentBottomSheet.setOnImageChosenListener(new BottomSheetFragment.onImageChosenListener() {
                @Override
                public void onCameraImageChosen(Intent data) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mBinding.ivBigAvatar.setImageBitmap(bitmap);
                    fragmentBottomSheet.dismiss();
                }

                @Override
                public void onGalleryImageChosen(Uri uri) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        mBinding.ivBigAvatar.setImageBitmap(bitmap);
                        fragmentBottomSheet.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}