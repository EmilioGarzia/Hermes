package unipa.prog3.model.io.util;

import java.util.Vector;

public class FileInvoker {
    private final Vector<Command> commands;
    private int currentCommand;

    public FileInvoker() {
        commands = new Vector<>();
    }

    public void executeCommand(Command command) {
        commands.add(command);
        while(currentCommand < commands.size())
            if (!command.execute()) {
                restore();
                break;
            } else currentCommand++;
    }

    public void restore() throws RuntimeException {
        for (; currentCommand >= 0; currentCommand--)
            if (!commands.get(currentCommand).undo())
                throw new RuntimeException("File restoring failed!");
    }
}
