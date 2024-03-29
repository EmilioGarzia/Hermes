package unipa.prog3.model.io.transaction.commands;

import java.io.File;

/**
 * Specializzazione di un Command per rinominare un file sul file system
 */
public class FileRenameCommand implements Command {
    private final String oldPath, newPath;

    public FileRenameCommand(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    @Override
    public boolean execute() {
        return new File(oldPath).renameTo(new File(newPath));
    }

    @Override
    public boolean undo() {
        return new File(newPath).renameTo(new File(oldPath));
    }
}
