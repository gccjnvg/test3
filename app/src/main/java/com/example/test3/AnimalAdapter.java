package com.example.test3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private String[] animalNames;
    private int[] animalImages;

    // 构造方法：传入数据
    public AnimalAdapter(String[] animalNames, int[] animalImages) {
        this.animalNames = animalNames;
        this.animalImages = animalImages;
    }

    // 1. 创建ViewHolder（加载列表项布局）
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_card, parent, false);
        return new AnimalViewHolder(view);
    }

    // 2. 绑定数据到ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        holder.ivAnimal.setImageResource(animalImages[position]);
        holder.tvAnimal.setText(animalNames[position]);

        // 项点击事件
        holder.itemView.setOnClickListener(v -> {
            String animal = animalNames[position];
            Toast.makeText(v.getContext(), "RecyclerView选中：" + animal, Toast.LENGTH_SHORT).show();
        });
    }

    // 3. 获取数据总数
    @Override
    public int getItemCount() {
        return animalNames.length;
    }

    // ViewHolder：持有列表项控件
    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAnimal;
        TextView tvAnimal;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAnimal = itemView.findViewById(R.id.iv_card_animal);
            tvAnimal = itemView.findViewById(R.id.tv_card_animal);
        }
    }
}