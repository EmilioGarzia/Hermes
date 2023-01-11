package unipa.prog3.model.io;

import unipa.prog3.model.io.transaction.*;
import unipa.prog3.model.io.transaction.commands.Command;
import unipa.prog3.model.io.transaction.commands.FileDeleteCommand;
import unipa.prog3.model.io.transaction.commands.FileRenameCommand;

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
            openFile(new File(dataPath + fileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String data) {
        TransactionInvoker invoker = new TransactionInvoker();
        invoker.addCommand(new Command() {
            @Override
            public boolean execute() {
                try {
                    file.seek(lastPosition);
                    file.writeBytes(data + "\n");
                    lastPosition = file.getFilePointer();
                    return true;
                } catch(IOException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean undo() {
                return true;
            }
        });
        invoker.commit();
    }

    public HashMap<Long, String> selectRecords(Predicate<String> condition) {
        HashMap<Long, String> selected = new HashMap<>();

        TransactionInvoker invoker = new TransactionInvoker();
        invoker.addCommand(new Command() {
            @Override
            public boolean execute() {
                try {
                    long pos = 0;
                    file.seek(pos);
                    while (pos < file.length()) {
                        String record = file.readLine();
                        if (condition == null || condition.test(record))
                            selected.put(pos, record);
                        pos = file.getFilePointer();
                    }
                    return true;
                } catch(IOException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean undo() {
                return false;
            }
        });

        invoker.commit();
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
        String originalPath = dataPath + fileName;
        String tempFilePath = dataPath + "." + fileName + ".tmp";
        String oldPath = dataPath + "." + fileName + ".old";

        TransactionInvoker invoker = new TransactionInvoker();
        invoker.addCommand(new Command() {
            @Override
            public boolean execute() {
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
                    return true;
                } catch(IOException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean undo() {
                return new File(tempFilePath).delete();
            }
        });

        invoker.addCommand(new FileRenameCommand(originalPath, oldPath));
        invoker.addCommand(new FileRenameCommand(tempFilePath, originalPath));
        invoker.addCommand(new FileDeleteCommand(oldPath));
        invoker.addCommand(new Command() {
            @Override
            public boolean execute() {
                try {
                    openFile(new File(originalPath));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public boolean undo() {
                try {
                    file.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        invoker.commit();
    }

    private void openFile(File f) throws IOException {
        file = new RandomAccessFile(f, "rw");
        lastPosition = file.length();
    }
}
