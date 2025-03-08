package com.example.drecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {
    FragmentAdapter fragmentAdapter;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Recipe recipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //intent
        Intent intent = getIntent();
        String id  = intent.getStringExtra("Recipe");
        int recipeId = Integer.parseInt(id);



        // 1way
//        Bundle bundle = new Bundle();
//        bundle.putInt("RecipeId", recipeId);
//
//        // Fragment instance
//        Fragment1 fragment1 = new Fragment1();
//        fragment1.setArguments(bundle);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
// 2nd way
        fragmentAdapter.addFragment(Fragment1.newInstance(recipeId));  // first create fragment and the set to the adapter
        fragmentAdapter.addFragment(new Fragment2());

        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("View");
                }
                if(position == 1){
                    tab.setText("Edit");
                }
            }
        }).attach();
    }
}