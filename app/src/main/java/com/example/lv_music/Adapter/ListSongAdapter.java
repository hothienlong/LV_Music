package com.example.lv_music.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Model.Song;
import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Logger;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.SongViewHolder> {

    List<SongItem> songItems;

    public ListSongAdapter(List<SongItem> songItems) {
        this.songItems = songItems;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
            holder.imgSong.setImageResource(R.drawable.ic_unknown);
        }


    }

    @Override
    public int getItemCount() {
        return songItems == null ? 0 : songItems.size();
    }

    // lưu lại view để khi gọi lại chỉ cần lấy ra => tăng hiệu suất
    class SongViewHolder extends RecyclerView.ViewHolder{

        TextView tvSongName, tvSingerName;
        ImageView imgSong;

        // ánh xạ
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSingerName = itemView.findViewById(R.id.tvSingerName);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            imgSong = itemView.findViewById(R.id.imgSong);
        }
    }
}
