package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject rootJsonObject = new JSONObject(json);
        JSONObject name = rootJsonObject.getJSONObject("name");
        String mainName = name.getString("mainName");

        JSONArray alsoKnownAsJsonArray = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
        }

        String placeOfOrigin = rootJsonObject.getString("placeOfOrigin");
        String description = rootJsonObject.getString("description");
        String image = rootJsonObject.getString("image");

        JSONArray ingredientsJsonArray = rootJsonObject.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredients.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}