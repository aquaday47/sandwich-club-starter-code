package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich currWich = new Sandwich();
        try{
            JSONObject obj = new JSONObject(json);

            //should I be adding nested try{} blocks around each of these here?
            //get name Object from json by String key "name"
            JSONObject name = obj.getJSONObject(Sandwich.ParseDataKeys.name_json_key);

            //set by string from JSON object main name
            currWich.setMainName(name.getString(Sandwich.ParseDataKeys.main_name_json_key));

            //get aka array -- cast to List<String> to fit data model
            JSONArray akaNames = name.getJSONArray(Sandwich.ParseDataKeys.aka_json_key);

            if (akaNames != JSONObject.NULL && akaNames != null && akaNames.length() > 0) {
                List<String> currList = new ArrayList<String>();
                for (int i = 0; i < akaNames.length(); i++) {
                    if (akaNames.getString(i) != null) {
                        currList.add(akaNames.getString(i));
                    }
                }
                currWich.setAlsoKnownAs((currList));
            }



            //place of origin
            String origin = obj.getString(Sandwich.ParseDataKeys.origin_json_key);
            currWich.setPlaceOfOrigin(origin);

            //get and set Desc.
            String desc = obj.getString(Sandwich.ParseDataKeys.desc_json_key);
            currWich.setDescription(desc);

            //image
            String imgPath = obj.getString(Sandwich.ParseDataKeys.image_json_key);
            currWich.setImage(imgPath);

            //ingredients
            JSONArray ingredArr = obj.getJSONArray(Sandwich.ParseDataKeys.ingredients_json_key);
            if (ingredArr != JSONObject.NULL && ingredArr != null && ingredArr.length()>0){
                List<String> ingredList = new ArrayList<String>();
                for (int i = 0; i < ingredArr.length(); i++) {
                    if (ingredArr.getString(i) != null){
                        ingredList.add(ingredArr.getString(i));
                    }
                }
                currWich.setIngredients(ingredList);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }


        return currWich;
    }
}