package unipa.prog3.model;

import java.io.*;
import java.util.Vector;

public class DataManager {
    private static final String dataPath = System.getProperty("user.home") + File.separator + "prog3" + File.separator;
    public static final String delimiter = ";";

    private static DataManager instance;

    public String[] readData(Table table) {
        String fullPath = dataPath + table.getFileName();
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

    public boolean insertData(Table table, String data) {
        String fullPath = dataPath + table.getFileName();
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(fullPath, true))) {
            writer.println(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    public enum Table {
        COURIERS("couriers.csv"),
        CLIENTS("clients.csv"),
        PACKAGES("packages.csv");

        private final String fileName;

        Table(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }
}
