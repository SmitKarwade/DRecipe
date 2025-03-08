package com.example.drecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drecipe.databinding.ActivityMainBinding;
import com.example.drecipe.databinding.RecipeItemsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements onItemLongClick{
    private ActivityMainBinding activityMainBinding;

    private ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    private ArrayList<Ads_Recipe> adsRecipeArrayList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private Ads_adapter adsAdapter;

    public MyViewModel myViewModel;
    private DB_Recipe dbRecipe;
    ImageView imageView4;
    TextView textView;
    private ExecutorService executorService;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    ViewPager2 viewPager22;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable;
    int currentPage=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }


        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        activityMainBinding.button.setBackgroundResource(R.color.transparent);

        viewPager22 = activityMainBinding.viewPager22;

        executorService = Executors.newSingleThreadExecutor();

        // tool bar
        Toolbar toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);
        // Remove default title (app name)
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //imageView4 = findViewById(R.id.imageView4);
        textView = findViewById(R.id.textView);

        dbRecipe = DB_Recipe.getInstance(this);

        // recyclerview1
        recyclerView = activityMainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // recyclerview2
//        recyclerView2 = activityMainBinding.recyclerView2;
//        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        recyclerView2.setHasFixedSize(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerAdapter = new RecyclerAdapter(this, recipeArrayList, this, myViewModel);

        //adapter2
        adsAdapter = new Ads_adapter(this, adsRecipeArrayList);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        // Initialize data in background thread

        initializeAds();

        myViewModel.getAllReceipe().observe(this,
                new Observer<List<Recipe>>() {
                    @Override
                    public void onChanged(List<Recipe> recipes) {
                        recipeArrayList.clear();
                        for (Recipe r: recipes
                             ) {
                            recipeArrayList.add(r);
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });

        recyclerView.setAdapter(recyclerAdapter);

        //recyclerView2.setAdapter(adsAdapter);
        viewPager22.setAdapter(adsAdapter);

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapter.onDelete();
                activityMainBinding.button.setBackgroundResource(R.color.transparent);
                activityMainBinding.button5.setBackgroundResource(R.color.transparent);
                activityMainBinding.button5.setText("");
            }
        });
        activityMainBinding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Confirm deletion !")
                                .setMessage("Are you sure you want to delete all recipes ?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                recyclerAdapter.deleteAll();
                                                activityMainBinding.button5.setText("");
                                                activityMainBinding.button5.setBackgroundResource(R.color.transparent);
                                                activityMainBinding.button.setBackgroundResource(R.color.transparent);
                                            }
                                        }).setNegativeButton("cancel", null).show();

            }
        });


        activityMainBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewRecipe.class);
                startActivity(intent);
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == adsRecipeArrayList.size()) {
                    currentPage = 0;
                }
                viewPager22.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Change page every 3 seconds
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    private void initializeAds() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Ads_Recipe ad1 = new Ads_Recipe("Get to know more about this", R.drawable.kfood);
                Ads_Recipe ad2 = new Ads_Recipe("Now make it in your home", R.drawable.kfood2);
                Ads_Recipe ad3 = new Ads_Recipe("Wanna try this new one", R.drawable.kfood3);
                adsRecipeArrayList.add(ad1);
                adsRecipeArrayList.add(ad2);
                adsRecipeArrayList.add(ad3);
            }
        });
    }
    

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                // Perform search operation here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search text change
                if(newText.isEmpty()){
                    recyclerAdapter = new RecyclerAdapter(MainActivity.this, recipeArrayList);
                    recyclerView.setAdapter(recyclerAdapter);
                }
                search(newText);
                // For example, you can filter your data as the user types
                return false;
            }
            public void search(String query){
                ArrayList<Recipe> selectedRecipeList = new ArrayList<>();
                // Handle search query submission
                myViewModel.getAllReceipe().observe(MainActivity.this,
                        new Observer<List<Recipe>>() {
                            @Override
                            public void onChanged(List<Recipe> recipes) {
                                selectedRecipeList.clear();
                                for (Recipe r:recipes
                                ) {
                                    if(r.getTitle().toLowerCase().contains(query.toLowerCase()) || r.getTitle().toUpperCase().contains(query.toUpperCase())){
                                        selectedRecipeList.add(r);
                                    }
                                }
                                recyclerAdapter = new RecyclerAdapter(MainActivity.this, selectedRecipeList);
                                recyclerView.setAdapter(recyclerAdapter);
                            }
                        });
            }
        });
        return true;

    }

    @Override
    public void onLongClick(boolean isInSelectionMode) {
        if(isInSelectionMode){
            activityMainBinding.button.setBackgroundResource(R.drawable.trash);
            activityMainBinding.button5.setText("Delete all");
        }
    }
}