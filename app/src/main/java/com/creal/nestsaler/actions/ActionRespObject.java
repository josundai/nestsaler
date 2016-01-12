package com.creal.nestsaler.actions;

import org.json.JSONException;
import org.json.JSONObject;

public interface ActionRespObject<T> {
    T fillWithJSON(JSONObject jsonObject) throws JSONException;
}
