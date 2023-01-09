package unipa.prog3.model.io;

import java.io.*;
import java.util.HashMap;
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

            file = new RandomAccessFile(dataPath + fileName, "rw");
            lastPosition = file.length();
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
                String record = file.readLine().trim();
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
        HashMap<Long, String> oldData = selectRecords(condition);
        for (long pos : oldData.keySet())
            update(pos, newData.get());
    }

    private void update(long pos, String data) {
        try {
            file.seek(pos);
            String oldData = file.readLine();
            if (oldData.length() >= data.length()) {
                // Sovrascrive il nuovo record su quello vecchio
                file.seek(pos);
                file.writeBytes(data);
                int overflow = oldData.length() - data.length();
                for (int i = 0; i < overflow; i++)
                    file.writeChar(' ');
            } else {
                // Aggiorna la tabella creando un nuovo file
                String tempFilePath = dataPath + "." + fileName + ".tmp";
                RandomAccessFile raf = new RandomAccessFile(tempFilePath, "rw");
                file.seek(0);
                while (file.getFilePointer() < file.length()) {
                    if (file.getFilePointer() != pos)
                        raf.writeBytes(file.readLine().trim());
                    else {
                        raf.writeBytes(data + "\n");
                        file.readLine();
                    }
                }
                raf.close();

                File tempFile = new File(tempFilePath);
                File originalFile = new File(dataPath + fileName);
                File oldFile = new File(dataPath + "." + fileName + ".old");
                if (!originalFile.renameTo(oldFile) || !tempFile.renameTo(originalFile) || !oldFile.delete()) {
                    // Implement rollback
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
