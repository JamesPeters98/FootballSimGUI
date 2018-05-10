package com.james.footballsim.Simulator;

import com.badlogic.gdx.Gdx;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Random;

/**
 * Created by James on 07/02/2018.
 */
public class NameGenerator {

    private Random rand;
    private Reader in;
    private List<CSVRecord> names;

    private static NameGenerator nameGenerator;

    public static NameGenerator getInstance(){
        if(nameGenerator == null){
            try {
                nameGenerator = new NameGenerator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nameGenerator;
    }

    private NameGenerator() throws IOException {
        rand = new Random();
        Reader in = Gdx.files.internal("csv/names.csv").reader();
        names = CSVFormat.EXCEL.withHeader().parse(in).getRecords();
    }

    public String getFirstName(){
        return names.get(rand.nextInt(names.size())).get("First Name");
    }

    public String getLastName(){
        return names.get(rand.nextInt(names.size())).get("Last Name");
    }


}
