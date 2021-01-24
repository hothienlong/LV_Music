package com.example.lv_music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongsCategoryAdapter extends RecyclerView.Adapter<SongsCategoryAdapter.SongsCategoryViewHolder> {

    Context context;
    List<SongItem> songItems;

    @NonNull
    @Override
    public SongsCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_list_song_item, parent, false);
        return new SongsCategoryViewHolder(view);
    }

    // gán dữ liệu
    @Override
    public void onBindViewHolder(@NonNull SongsCategoryViewHolder holder, int position) {
        SongItem songItem = songItems.get(position);

        holder.tvSongName.setText(songItem.getName());

        holder.tvSingerName.setText(songItem.getLstSingerNames()
                .toString()
                .substring(1, songItem.getLstSingerNames().toString().length()-1)
        );


        try {
            Picasso.get().load(songItems.get(position).getImage()).into(holder.imgSong);
        }
        catch (IllegalArgumentException e){
//            holder.imgSong.setImageResource(R.drawable.ic_unknown2);
        }

        // bắt sự kiện bấm vào 1 bài hát
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlaySongActivity.class);
                intent.putExtra("song", songItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songItems == null ? 0 : songItems.size();
    }

    // lưu lại view để khi gọi lại chỉ cần lấy ra => tăng hiệu suất
    class SongsCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongName, tvSingerName;
        ImageView imgSong;

        // ánh xạ
        public SongsCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            imgSong = itemView.findViewById(R.id.imgSong);
        }
    }
}
