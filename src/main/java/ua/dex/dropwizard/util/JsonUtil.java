package ua.dex.dropwizard.util;

import com.google.gson.Gson;

/**
 * Created by dexter on 11.02.17.
 */
public class JsonUtil {

    private static final Gson gson = new Gson();


    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz){
        try{
         return gson.fromJson(json, clazz);
        } catch (Exception e){
            return null;
        }
    }
}
