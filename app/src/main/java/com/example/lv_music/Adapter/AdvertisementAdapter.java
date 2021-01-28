package com.example.lv_music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Model.Advertisement;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

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
        View view = inflater.inflate(R.layout.layout_advertisement_item, null);

        ImageView imgAdvertisement = view.findViewById(R.id.imgAdvertisement);
        TextView tvContent = view.findViewById(R.id.tvContentAdvertisement);

        Picasso.get().load(advertisements.get(position).getImage()).into(imgAdvertisement);

        tvContent.setText(advertisements.get(position).getContent());

        container.addView(view); // add view vào view group của context truyền vào (relative layout của advertisement fragment)


        // bắt sự kiện click advertisement
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaySongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("advertisement", advertisements.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.setSelected(event.getAction() == MotionEvent.ACTION_DOWN);
//                return true;
//            }
//        });
        return view;
    }
}
