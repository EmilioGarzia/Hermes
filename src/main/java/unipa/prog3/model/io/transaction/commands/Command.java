package unipa.prog3.model.io.transaction.commands;

public interface Command {
    boolean execute();
    boolean undo();
}
