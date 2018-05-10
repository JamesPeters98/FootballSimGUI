package com.james.footballsim;

import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class JsonConverter {

    public static <T> T toJson(T t, Class<T> type){
        Gson gson = new Gson();
        String json = gson.toJson(t);
        T o = gson.fromJson(json, type);
        try {
            Output output = new Output(new FileOutputStream("saves/data.json"));
            output.writeString(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }
}
