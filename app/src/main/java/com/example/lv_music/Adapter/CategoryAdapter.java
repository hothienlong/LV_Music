package com.example.lv_music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lv_music.Activity.SongsCategoryActivity;
import com.example.lv_music.Model.Category;
import com.example.lv_music.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ko hỏi parent từ đâu ra (mặc định vậy rồi...)
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    // gán dữ liệu
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Picasso.get()
                .load(categories.get(position).getImage())
                .into(holder.imgCategory);

//        holder.tvCategoryName.setText(categories.get(position).getName());
        Picasso.get().setLoggingEnabled(true); //log lỗi khi load picasso
        // bắt sự kiện click category
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongsCategoryActivity.class);
                intent.putExtra("category", categories.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    // lưu lại view để khi gọi lại chỉ cần lấy ra => tăng hiệu suất
    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCategory;
//        TextView tvCategoryName;

        // ánh xạ
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
//            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }

}
