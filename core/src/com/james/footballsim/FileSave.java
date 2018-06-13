package com.james.footballsim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSave {

    AsyncExecutor asyncExecutor = new AsyncExecutor(10);

    private static Kryo kryo;
    private Timer timer;
    public static Output output;
    public static Input input;
    boolean corruptFile = false;

    String filePath = Gdx.files.getLocalStoragePath()+"saves/";

    FileSave(){
        kryo = new Kryo();
        timer = new Timer();

    }

    public boolean isEmptyDirectory(String fileName){
        new File(filePath).mkdirs();
        File file = new File(filePath+fileName+".sav");

        try {
            boolean bool = file.createNewFile();
            if(bool) Gdx.app.log("FileSaver", "Created new file "+fileName);
            else Gdx.app.log("FileSaver", "No new file "+fileName);
            return bool;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public <T> void saveClass(T o, String fileName){
        Thread t = new Thread(() -> {
            Kryo kryo = new Kryo();
            Output output = null;
            try {
                output = new Output(new FileOutputStream(filePath+fileName+".sav"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            kryo.writeClassAndObject(output,o);
            output.close();
        });

        t.start();
    }

    public <T> T readClass(Class<T> type, String fileName){
        T file;

        if(isEmptyDirectory(fileName)){
            file = null;
            setCorruptFile();
            return file;
        }

        isEmptyDirectory(fileName);

        try {
            input = new Input(new FileInputStream(filePath+fileName+".sav"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            file = (T) kryo.readClassAndObject(input);
        } catch (KryoException e){
            e.printStackTrace();
            file = null;
            setCorruptFile();
        }

        input.close();
        return file;
    }

    public void saveInfo(){
        saveClass(FootballSim.info,"data");
        Gdx.app.log("FileSaver", "Saved info");
    }

    public void setCorruptFile(){
        corruptFile = true;
    }

    public boolean isCorruptFile() {
        return corruptFile;
    }

    public Kryo kryo(){
        return kryo;
    }
}
