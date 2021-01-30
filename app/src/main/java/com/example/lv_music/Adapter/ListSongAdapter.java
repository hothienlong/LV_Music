package com.example.lv_music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Activity.MainActivity;
import com.example.lv_music.Activity.PlaySongActivity;
import com.example.lv_music.Activity.SongsCategoryActivity;
import com.example.lv_music.Fragment.ListSongFragment;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.SongViewHolder> {

    Context context;
    ArrayList<SongItem> songItems; //dùng array list thay vì list mới intent đc

    public ListSongAdapter(ArrayList<SongItem> songItems) {
        this.songItems = songItems;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_list_song_item, parent, false);
        return new SongViewHolder(view);
    }

    // gán dữ liệu
    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
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
            Log.d("BBB", e.getMessage());
//            holder.imgSong.setImageResource(R.drawable.ic_unknown2);
        }

        // bắt sự kiện bấm vào 1 bài hát
        if(context instanceof MainActivity || context instanceof SongsCategoryActivity){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaySongActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putParcelableArrayList("listsongitem", songItems);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        else if(context instanceof PlaySongActivity){
            // Nếu đang ở PlaySongActivity thì xử lí cách khác
            holder.tvSongName.setTextColor(Color.WHITE);
            holder.tvSingerName.setTextColor(Color.WHITE);
            holder.optionSongItem.setImageResource(R.drawable.ic_more_white);
        }

    }

    @Override
    public int getItemCount() {
        return songItems == null ? 0 : songItems.size();
    }

    // lưu lại view để khi gọi lại chỉ cần lấy ra => tăng hiệu suất
    class SongViewHolder extends RecyclerView.ViewHolder{

        TextView tvSongName, tvSingerName;
        ImageView imgSong, optionSongItem;

        // ánh xạ
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            imgSong = itemView.findViewById(R.id.imgSong);
            optionSongItem = itemView.findViewById(R.id.optionSongItem);
        }
    }
}
