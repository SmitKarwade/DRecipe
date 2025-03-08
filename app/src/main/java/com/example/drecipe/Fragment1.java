package com.example.drecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;

import com.example.drecipe.databinding.Fragment1Binding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class Fragment1 extends Fragment {
    private Recipe fragrecipe;
    private int id;
    private MyViewModel viewModel;
    private Fragment1Binding fragment1Binding1;
    AdapterIngredients adapterIngredients;
    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    RecyclerView recyclerView3;
    Button button2;
    public Fragment1() {

    };
    public static Fragment1 newInstance(int recipeId) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt("RecipeId", recipeId);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(getArguments() != null){
//            id = getArguments().getInt("RecipeId");
//            Log.d("NewId", "" + id);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id = getArguments().getInt("RecipeId");

        // Inflate the layout for this fragment
        fragment1Binding1 = DataBindingUtil.inflate(inflater,
                R.layout.fragment_1,
                container,
                false);
        fragment1Binding1.setLifecycleOwner(getViewLifecycleOwner());
        View view = fragment1Binding1.getRoot();

        recyclerView3 = fragment1Binding1.recyclerView3;
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapterIngredients = new AdapterIngredients(ingredientList);
        recyclerView3.setAdapter(adapterIngredients);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        viewModel.getSelectedRecipe(id).observe(getViewLifecycleOwner(),
                new Observer<Recipe>() {
                    @Override
                    public void onChanged(Recipe recipe) {
                        if(recipe != null){
                            fragrecipe = recipe;
                            ingredientList.clear();
                            ingredientList.addAll(fragrecipe.getIngredients());
                            adapterIngredients.notifyDataSetChanged();
                            fragment1Binding1.setFragrecipe(fragrecipe);
                            viewModel.setRecipe(fragrecipe);
                        }else {
                            fragrecipe = null;
                            Toast.makeText(requireContext(), "Recipe is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent intent = new Intent(requireContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//            }
//        });
        button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}