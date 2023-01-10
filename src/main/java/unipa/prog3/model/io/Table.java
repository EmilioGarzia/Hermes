package unipa.prog3.model.io;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Table {
    public static final String delimiter = ";";
    private static final String dataPath = System.getProperty("user.home") + File.separator + "prog3" + File.separator;

    private final String fileName;
    private RandomAccessFile file;
    private long lastPosition;

    public Table(String fileName) {
        this.fileName = fileName;
        try {
            File dir = new File(dataPath);
            if (!dir.exists() && !dir.mkdir())
                throw new FileNotFoundException();
            openFile(new File(dataPath + fileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String data) {
        try {
            file.seek(lastPosition);
            file.writeBytes(data + "\n");
            lastPosition = file.getFilePointer();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Long, String> selectRecords(Predicate<String> condition) {
        HashMap<Long, String> selected = new HashMap<>();
        long pos = 0;

        try {
            file.seek(pos);
            while (pos < file.length()) {
                String record = file.readLine();
                if (condition == null || condition.test(record))
                    selected.put(pos, record);
                pos = file.getFilePointer();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return selected;
    }

    public void updateRecords(Predicate<String> condition, Supplier<String> newData) {
        HashMap<Long, String> data = selectRecords(condition);
        if (data.isEmpty()) return;

        for (long pos : data.keySet())
            data.replace(pos, newData.get());
        update(data);
    }

    private void update(HashMap<Long, String> records) {
        // Aggiorna la tabella utilizzando un file temporaneo
        String tempFilePath = dataPath + "." + fileName + ".tmp";

        try {
            file.seek(0);
            RandomAccessFile raf = new RandomAccessFile(tempFilePath, "rw");
            raf.seek(0);
            while (file.getFilePointer() < file.length()) {
                long pos = file.getFilePointer();
                String oldData = file.readLine();
                raf.writeBytes(records.getOrDefault(pos, oldData) + "\n");
            }
            raf.close();

            File tempFile = new File(tempFilePath);
            File originalFile = new File(dataPath + fileName);
            File oldFile = new File(dataPath + "." + fileName + ".old");
            if (!originalFile.renameTo(oldFile) || !tempFile.renameTo(originalFile) || !oldFile.delete()) {
                // Implement rollback
            } else openFile(originalFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile(File f) throws IOException {
        file = new RandomAccessFile(f, "rw");
        lastPosition = file.length();
    }
}
