package com.example.drecipe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

import com.example.drecipe.databinding.Fragment1Binding;
import com.example.drecipe.databinding.Fragment2Binding;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    Recipe f2_recipe;
    MyViewModel f2_viewModel;
    RecyclerView f2RecyclerView;
    F2_Adapter f2Adapter;
    List<Ingredient> f2_list_ingredient = new ArrayList<>();
    boolean isEditable = false;
    Button f2_edit;

    public Fragment2() {
        // Required empty public constructor
    }

    public void getRecipe_f2(Recipe recipe){
        f2_recipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fragment2Binding fragment2Binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_2,
                container,
                false);
        fragment2Binding.setLifecycleOwner(getViewLifecycleOwner());

        f2_viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        f2RecyclerView = fragment2Binding.f2RecyclerView;
        f2RecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        f2Adapter = new F2_Adapter(f2_list_ingredient, requireContext(), f2_viewModel);
        f2RecyclerView.setAdapter(f2Adapter);


        f2_viewModel.getRecipe().observe(getViewLifecycleOwner(),
                new Observer<Recipe>() {
                    @Override
                    public void onChanged(Recipe recipe) {
                        f2_recipe = recipe;
                        Log.d("f2_recipe", f2_recipe.getTitle() + "");
                        fragment2Binding.setF2Recipe(f2_recipe);
                        f2_list_ingredient.addAll(recipe.getIngredients());
                        f2Adapter.notifyDataSetChanged();
                    }
                });
        f2Adapter.setEditable(false);  // this is when onBindViewHolder is called one time

        // Inflate the layout for this fragment
        fragment2Binding.f2EditText.setEnabled(false);
        fragment2Binding.f2EditText.setFocusable(false);
        fragment2Binding.f2EditText.setFocusableInTouchMode(false);

        fragment2Binding.f2Category.setEnabled(false);
        fragment2Binding.f2Category.setFocusable(false);
        fragment2Binding.f2Category.setFocusableInTouchMode(false);

        fragment2Binding.f2Note.setEnabled(false);
        fragment2Binding.f2Note.setFocusable(false);
        fragment2Binding.f2Note.setFocusableInTouchMode(false);
        fragment2Binding.f2Edit.setText("Edit");



        fragment2Binding.f2Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    // Switch to non-editable mode
                    fragment2Binding.f2EditText.setEnabled(false);
                    fragment2Binding.f2EditText.setFocusable(false);
                    fragment2Binding.f2EditText.setFocusableInTouchMode(false);

                    fragment2Binding.f2Category.setEnabled(false);
                    fragment2Binding.f2Category.setFocusable(false);
                    fragment2Binding.f2Category.setFocusableInTouchMode(false);

                    fragment2Binding.f2Note.setEnabled(false);
                    fragment2Binding.f2Note.setFocusable(false);
                    fragment2Binding.f2Note.setFocusableInTouchMode(false);
                    fragment2Binding.f2Edit.setText("Edit");

                    f2Adapter.setEditable(false);


                    Recipe recipe = fragment2Binding.getF2Recipe();
                    if(recipe != null){
                        recipe.getIngredients().clear();
                        recipe.getIngredients().addAll(f2_list_ingredient);
                        f2_viewModel.updateRecipe(recipe);
                        Toast.makeText(requireActivity(), "Recipe updated", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(requireActivity(), "Can't update recipe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Switch to editable mode
                    fragment2Binding.f2EditText.setEnabled(true);
                    fragment2Binding.f2EditText.setFocusable(true);
                    fragment2Binding.f2EditText.setFocusableInTouchMode(true);

                    fragment2Binding.f2Category.setEnabled(true);
                    fragment2Binding.f2Category.setFocusable(true);
                    fragment2Binding.f2Category.setFocusableInTouchMode(true);

                    fragment2Binding.f2Note.setEnabled(true);
                    fragment2Binding.f2Note.setFocusable(true);
                    fragment2Binding.f2Note.setFocusableInTouchMode(true);

                    f2Adapter.setEditable(true);

                    fragment2Binding.f2Edit.setText("Save");

                }
                isEditable = !isEditable;
            }
        });

        fragment2Binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredient = new Ingredient("", "","");
                f2_list_ingredient.add(ingredient);
                f2Adapter.notifyItemInserted(f2_list_ingredient.size()-1);
                f2RecyclerView.scrollToPosition(f2_list_ingredient.size()-1);
                Toast.makeText(requireActivity(), "" + f2_list_ingredient.size(), Toast.LENGTH_SHORT).show();
            }
        });
        return fragment2Binding.getRoot();
    }
}