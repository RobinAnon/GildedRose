package edu.insightr.gildedrose;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;

public class JSONReaderClass {

    public static Item[] readJSON(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FileReader file = new FileReader(path);
            Item[] myObjects = mapper.readValue(file, Item[].class);
            return myObjects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (null);
    }


}