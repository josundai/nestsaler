package com.creal.nestsaler.actions;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class CommonPaginationAction<T extends ActionRespObject<T>> extends PaginationAction<T> {
    private static final String tag = "TT-ComPaginationAction";
    private Map<String, String> mParameter;
    private Class<T> mClass;
    public CommonPaginationAction(Context context, int pageIndex, int pageSize, String url, Map parameter, Class<T> tClass){
        super(context, pageIndex, pageSize);
        mServiceId = url;
        mURL = url;
        mParameter = parameter;
        mClass = tClass;
    }

    public CommonPaginationAction(Context context, int pageIndex, int pageSize, String url, Map parameter, Class<T> tClass, String contentsKey){
        this(context, pageIndex, pageSize, url, parameter, tClass);
        setContentsKey(contentsKey);
    }

    public CommonPaginationAction cloneCurrentPageAction(){
        CommonPaginationAction action = new CommonPaginationAction(
                mAppContext,
                getPageIndex(),
                getPageSize(),
                mURL,
                mParameter,
                mClass,
                getContentsKey()
        );
        return action;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(mParameter != null) {
            Iterator<Map.Entry<String, String>> entryIterator = mParameter.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, String> entry = entryIterator.next();
                parameters.put(entry.getKey(), entry.getValue());
            }
        }
        return parameters;
    }

    public T convertJsonToResult(JSONObject item) throws JSONException{
        try {
            if(mClass != null) {
                T t = mClass.newInstance();
                return t.fillWithJSON(item);
            }
            return null;
        } catch (Exception e) {
            Log.e(tag, "Failed to create pagination item : " + mClass.getSimpleName(), e);
            return null;
        }
    }
}
