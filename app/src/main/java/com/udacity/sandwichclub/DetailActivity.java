package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnownAsBuilder = new StringBuilder();
        for (int i = 0; alsoKnownAsList.size() > i; i++) {
            alsoKnownAsBuilder.append(alsoKnownAsList.get(i));
            if (alsoKnownAsList.size() > 1 && i != alsoKnownAsList.size() - 1) {
                alsoKnownAsBuilder.append(", ");
            }
        }

        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (int i = 0; ingredientsList.size() > i; i++) {
            ingredientsBuilder.append(ingredientsList.get(i));
            if (ingredientsList.size() > 1 && i != ingredientsList.size() - 1) {
                ingredientsBuilder.append(", ");
            }
        }

        String alsoKnownAsString = alsoKnownAsBuilder.toString();
        String ingredientsString = ingredientsBuilder.toString();

        alsoKnownAsTV.setText(alsoKnownAsString);
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTV.setText(sandwich.getDescription());
        ingredientsTV.setText(ingredientsString);
    }
}
