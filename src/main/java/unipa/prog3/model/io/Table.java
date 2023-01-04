package unipa.prog3.model.io;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;
import java.util.function.Predicate;

public class Table extends HashMap<String, Long> {
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

    public boolean addRecord(String data) {
        try {
            writeRecord(lastPosition, data);
            put(data, lastPosition);
            lastPosition = file.getFilePointer();
            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Vector<String> selectRecords(Predicate<String> condition) {
        Vector<String> selected = new Vector<>();
        try {
            file.seek(0);
            while (file.getFilePointer() < file.length()) {
                String record = file.readLine().trim();
                if (condition == null || condition.test(record))
                    selected.add(record);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return selected;
    }

    public boolean updateRecord(String data) {
        try {
            long pos = get(data);
            file.seek(pos);
            String oldData = file.readLine();
            if (oldData.length() >= data.length()) {
                // Sovrascrive il nuovo record su quello vecchio
                file.seek(pos);
                file.writeBytes(data);
                int overflow = oldData.length() - data.length();
                for (int i = 0; i < overflow; i++)
                    file.writeChar(' ');
                return true;
            } else {
                // Aggiorna la tabella creando un nuovo file
                String tempFilePath = dataPath + "." + fileName + ".tmp";
                RandomAccessFile raf = new RandomAccessFile(tempFilePath, "w");
                file.seek(0);
                while(file.getFilePointer() < file.length()) {
                    if (file.getFilePointer() != pos)
                        raf.writeBytes(file.readLine().trim());
                    else {
                        raf.writeBytes(data+"\n");
                        file.readLine();
                    }
                }
                raf.close();

                File tempFile = new File(tempFilePath);
                File originalFile = new File(dataPath + fileName);
                File oldFile = new File(dataPath + "." + fileName + ".old");
                if (originalFile.renameTo(oldFile) && tempFile.renameTo(originalFile)) {
                    oldFile.delete();
                    return true;
                } else {
                    // Implement rollback
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean writeRecord(long pos, String data) {
        try {
            file.seek(pos);
            file.writeBytes(data + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
