package com.example.lv_music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Activity.SingerActivity;
import com.example.lv_music.Model.Singer;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListSingerAdapter extends RecyclerView.Adapter<ListSingerAdapter.SingerViewHolder> {

    Context context;
    List<Singer> singers;

    public ListSingerAdapter(List<Singer> singers) {
        this.singers = singers;
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_list_singer_item, parent, false);
        return new SingerViewHolder(view);
    }

    // gán dữ liệu
    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        Singer singer = singers.get(position);

        if(singer.getRealName() != null) {
            holder.tvSingerName.setText(singer.getStageName());
        }
        else {
            holder.tvSingerName.setText("...");
        }

        try {
            Picasso.get()
                    .load(singer.getAvatar())
                    .into(holder.imgSinger);
        }
        catch (IllegalArgumentException e){
            Log.d("BBB", e.getMessage());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingerActivity.class);
                intent.putExtra("singer", singer);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return singers == null ? 0 : singers.size();
    }

    class SingerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSinger;
        TextView tvSingerName;

        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSinger = itemView.findViewById(R.id.imgSinger);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
        }
    }
}
