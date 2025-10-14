package com.example.test3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerActivity extends AppCompatActivity {

    private String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] animalImages = {
            R.drawable.lion,
            R.drawable.tiger,
            R.drawable.monkey,
            R.drawable.dog,
            R.drawable.cat,
            R.drawable.elephant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        // 初始化RecyclerView
        RecyclerView rvAnimal = findViewById(R.id.rv_animal);

        // 设置布局管理器（线性布局，类似ListView）
        rvAnimal.setLayoutManager(new LinearLayoutManager(this));

        // 设置适配器
        AnimalAdapter adapter = new AnimalAdapter(animalNames, animalImages);
        rvAnimal.setAdapter(adapter);
    }
}