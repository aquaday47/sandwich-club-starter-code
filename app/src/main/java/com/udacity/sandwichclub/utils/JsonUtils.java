package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    private static JSONObject obj;
    private static JSONObject name;


    public static Sandwich parseSandwichJson(String json) {
        Sandwich currWich = new Sandwich();


        try{
            obj = new JSONObject(json);
        } catch (JSONException e){
            e.printStackTrace();
        }
            //should I be adding nested try{} blocks around each of these here?
            //get name Object from json by String key "name"
        if (obj != null){
           try{
                name = obj.getJSONObject(Sandwich.ParseDataKeys.name_json_key);
            } catch (JSONException e){
                e.printStackTrace();
            }


            //set by string from JSON object main name
            if (name != null)

            try{
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
            } catch (JSONException e){
                e.printStackTrace();
            }




            //place of origin
            try{
                String origin = obj.getString(Sandwich.ParseDataKeys.origin_json_key);
                currWich.setPlaceOfOrigin(origin);
            } catch (JSONException e){
                e.printStackTrace();
            }



            //get and set Desc.
            try{
                String desc = obj.getString(Sandwich.ParseDataKeys.desc_json_key);
                currWich.setDescription(desc);
            } catch (JSONException e){
                e.printStackTrace();
            }


            //image
            try{
                String imgPath = obj.getString(Sandwich.ParseDataKeys.image_json_key);
                currWich.setImage(imgPath);
            } catch (JSONException e){
                e.printStackTrace();
            }


            //ingredients
            try{
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




        }

        return currWich;
    }
}