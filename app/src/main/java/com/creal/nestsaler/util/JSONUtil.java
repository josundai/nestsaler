package com.creal.nestsaler.util;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jinshui on 15-12-25.
 */
public class JSONUtil {

    private static final String tag = "XYK-JSONUtil";


    public static String getString(JSONObject object, String key, String defaultValue){
        try {
            return object.getString(key);
        } catch (JSONException e) {
            Log.e(tag, e.getMessage(), e);
            return defaultValue;
        }
    }


    public static JSONObject toJSONObject(String jsonString) throws JSONException {
        JsonParser parser = new JsonParser();
        JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
        return toJSONObject(object);
    }

    public static JSONObject toJSONObject(JsonObject object) throws JSONException {
        Iterator<Map.Entry<String, JsonElement>> entryIterator = object.entrySet().iterator();
        JSONObject jsonObject = new JSONObject();
        while(entryIterator.hasNext()){
            Map.Entry<String, JsonElement> entry = entryIterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if(value.isJsonArray()){
                jsonObject.put(key, toJSONArray((JsonArray)value));
            }else if(value.isJsonObject()){
                jsonObject.put(key, toJSONObject((JsonObject)value));
            }else if(value.isJsonNull()){
                Log.e(tag, "Found a JsonNull with key " + key);
            }else if(value.isJsonPrimitive()){
                jsonObject.put(key, value.getAsString());
            }
        }
        return jsonObject;
    }

    public static JSONArray toJSONArray(JsonArray jsonArray) throws JSONException {
        JSONArray array = new JSONArray();
        for(int i = 0; i < jsonArray.size(); i++){
            JsonElement value = jsonArray.get(i);
            if(value.isJsonArray()){
                array.put(i, toJSONArray((JsonArray)value));
            }else if(value.isJsonObject()){
                array.put(i, toJSONObject((JsonObject)value));
            }else if(value.isJsonNull()){
                Log.e(tag, "Found a JsonNull with key " + i);
            }else if(value.isJsonPrimitive()){
                array.put(i, value.getAsString());
            }
        }
        return array;
    }
}
