package com.example.lv_music.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Model.SongItem;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MVAdapter extends RecyclerView.Adapter<MVAdapter.ViewHolder> {

    Context context;
    List<SongItem> songItems;
    private onMVClickedListener mListener;

    public MVAdapter(Context context, List<SongItem> songItems) {
        this.context = context;
        this.songItems = songItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.layout_mv_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongItem currentSong = songItems.get(position);
        holder.tvTitle.setText(currentSong.getName());
        holder.tvSinger.setText(currentSong.getLstSingerNames().get(0));
        try {
            Picasso.get().load(currentSong.getImage()).into(holder.ivMV);
        }
        catch (IllegalArgumentException e){
            Log.d("BBB", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return songItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSinger;
        ImageView ivMV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSinger = itemView.findViewById(R.id.tvSinger);
            ivMV = itemView.findViewById(R.id.imgMV);

            itemView.setOnClickListener(view ->
                    mListener.onMvClicked(songItems
                            .get(getAdapterPosition())
                            .getMv_link()));
        }

    }

    public interface onMVClickedListener{
        void onMvClicked(String mv_link);
    }

    public void setOnMVClickedListener(onMVClickedListener listener){
        mListener = listener;
    }
}
