package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mBinding;
    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        try {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.ic_error_face)
                .into(mBinding.imageIv);

        //I'd like to apply a setContentDescription for the error-image I load
        //not yet sure how to implement that yet.
        //possible solutions: (if in Kotlin or C#, write extension  method for
        //the method that executes a check for filename to set content
        // But, I don't believe Java allows extension methods for core libraries

        // TODO Create a listener for Error on Picasso.Builder type thing
        // rendering that specific drawable (or loading it, idk)
        // and
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Handle Aka list creation, stringBuilding, View exposure/hiding
        StringBuilder sbAkas = new StringBuilder();
        List<String> akaList = sandwich.getAlsoKnownAs();
        mBinding.akaLabel.setVisibility(View.VISIBLE);
        if (akaList != null){
            for (String akaName: akaList){
                sbAkas.append(akaName);
                sbAkas.append(", ");
            }
        } else {
            mBinding.akaLabel.setVisibility(View.GONE);
            mBinding.alsoKnownTv.setVisibility(View.GONE);
        }
        mBinding.alsoKnownTv.setText(sbAkas.toString());

        //set Description
        mBinding.descriptionTv.setText(sandwich.getDescription());

        //Handling Ingredient List StringBuilding, view control
        StringBuilder sbIngred = new StringBuilder();
        List<String> ingList = sandwich.getIngredients();

        //Thought about creation a method to do this SHOWING AND HIDING VIEWS BUT THEY'RE
        //TV specific -- idk
        mBinding.ingredientsTv.setVisibility(View.VISIBLE);
        mBinding.ingredLabel.setVisibility(View.VISIBLE);

        if (ingList != null) {
            for (String ing: ingList){
                sbIngred.append(ing);
                sbIngred.append(", ");
            }
        } else{
            mBinding.ingredientsTv.setVisibility(View.GONE);
            mBinding.ingredLabel.setVisibility(View.GONE);
        }

        mBinding.ingredientsTv.setText(sbIngred.toString());
        //Handling Place of Origin
        mBinding.originTv.setText(sandwich.getPlaceOfOrigin());

        mBinding.originTv.setVisibility(View.VISIBLE);
        mBinding.originLabel.setVisibility(View.VISIBLE);

        if (sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin().length()==0 ){
            mBinding.originTv.setVisibility(View.GONE);
            mBinding.originLabel.setVisibility(View.GONE);
        }
    }
}
