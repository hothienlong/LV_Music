package com.example.lv_music.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lv_music.Model.Singer;
import com.example.lv_music.R;

public class SingerActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvSingerName, tvBirthdate, tvCountry, tvInfomation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);
        addControls();
        catchIntent();
    }

    private void catchIntent() {
        Intent intent = getIntent();

        if(intent != null){
            if(intent.hasExtra("singer")){
                Singer singer = (Singer) intent.getSerializableExtra("singer");
                tvSingerName.setText(singer.getRealName());
                tvBirthdate.setText(singer.getBirthdate());
                tvCountry.setText(singer.getCountry());
                tvInfomation.setText(singer.getInformation());

                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(singer.getStageName());
                actionBar.setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    private void addControls() {
        toolbar = findViewById(R.id.singerToolbar);
        tvSingerName = findViewById(R.id.tvSingerName);
        tvBirthdate = findViewById(R.id.tvBirthdate);
        tvCountry = findViewById(R.id.tvCountry);
        tvInfomation = findViewById(R.id.tvInfomation);
    }
}