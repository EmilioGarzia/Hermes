package unipa.prog3.model.io.util;

public interface Command {
    boolean execute();
    boolean undo();
}
