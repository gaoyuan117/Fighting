package com.xiangying.fighting.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.bean.LoginUser;
import com.xiangying.fighting.bean.TalkBeans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ray on 2016/9/12.
 */
public class JsonParse  {

    public static <T> T  parseCommonInfo(String json,Class<T> tClass) {
        if (json == null) return null;
        Gson gson = new Gson();
        T t = gson.fromJson(json,tClass);
        return t;
    }

    /**
     * 解析登录用户
     * */
    public static LoginUser ParseLoginUserInfo(String json){
        Gson gson =new Gson();
        LoginUser data=gson.fromJson(json,LoginUser.class);
//        Logger.v(data.getHx().getEasemob());
        return data;
    }

    /**
     * 解析用户信息列表
     * */
    public static ArrayList<TalkBeans> ParseUserList(String json){
        ArrayList<TalkBeans> data=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(json);
            JSONArray jsonArray=object.getJSONArray("data");
            if(jsonArray==null){
                return data;
            }
            json=jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return data;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TalkBeans>>(){}.getType();
        data = gson.fromJson(json,type);
        Logger.v("ParseUserList",data.get(0).getNickname());
        return data;
    }

    /**
     * 解析用户信息
     * */
    public static TalkBeans ParseUser(String json){
        try {
            JSONObject object=new JSONObject(json);
            json=object.getJSONObject("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson =new Gson();
        TalkBeans data=gson.fromJson(json,TalkBeans.class);
        Logger.v(data.getNickname());
        return data;
    }
}
