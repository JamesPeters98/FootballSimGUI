package com.james.footballsim;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.ixeption.libgdx.transitions.FadingGame;

import java.io.*;


public class JsonConverter extends FadingGame {

    static Gson gson;
    static Info info;

    public static void main(String args[]){
        FileSaveJSON fileSave = new FileSaveJSON();
        gson = new GsonBuilder().create();
        info = fileSave.readClass(Info.class, "data");
        toJson(info);
        fromJson();
    }

    public static void toJson(Info info){
        try (Writer writer = new FileWriter(FileSaveJSON.filePath+"data.json")) {
            gson.toJson(info, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fromJson(){
        try (Writer writer = new FileWriter(FileSaveJSON.filePath+"data.json")) {
            gson.toJson(info, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
