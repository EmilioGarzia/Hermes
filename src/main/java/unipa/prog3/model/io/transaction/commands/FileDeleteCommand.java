package unipa.prog3.model.io.transaction.commands;

import java.io.File;

/**
 * Specializzazione di un Command per l'eliminazione di un file sul file system
 */
public class FileDeleteCommand implements Command {
    private final File file;

    public FileDeleteCommand(String filePath) {
        file = new File(filePath);
    }

    @Override
    public boolean execute() {
        return file.delete();
    }

    @Override
    public boolean undo() {
        // Un file cancellato non può essere ripristinato
        return false;
    }
}
