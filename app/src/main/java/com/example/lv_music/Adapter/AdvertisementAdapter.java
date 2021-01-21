package com.example.lv_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Dữ liệu: Advertisement -> View
public class AdvertisementAdapter extends PagerAdapter {
    Context context;
    List<Advertisement> advertisements;

    public AdvertisementAdapter(Context context, List<Advertisement> advertisements) {
        this.context = context;
        this.advertisements = advertisements;
    }

    @Override
    public int getCount() {
        return advertisements.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    // trả về view
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.advertisement_item, null);

        ImageView imgAdvertisement = view.findViewById(R.id.imgAdvertisement);
        TextView tvContent = view.findViewById(R.id.tvContentAdvertisement);

        Picasso.get().load(advertisements.get(position).getImage()).into(imgAdvertisement);
        tvContent.setText(advertisements.get(position).getContent());

        container.addView(view); // add view vào view group của context truyền vào (relative layout của advertisement fragment)

        return view;
    }
}
