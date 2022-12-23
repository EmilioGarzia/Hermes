package unipa.prog3.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.function.IntFunction;

public class DataManager {
    private static final String dataPath = System.getProperty("user.home") + File.separator + "prog3" + File.separator;

    private static DataManager instance;

    public String[] readData(String fileName) {
        String fullPath = dataPath + fileName;
        try(BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            Vector<String> data = new Vector<>();
            while(reader.ready())
                data.add(reader.readLine());
            return data.toArray(i -> new String[] {data.get(i)});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }
}
