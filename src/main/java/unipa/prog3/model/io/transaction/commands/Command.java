package unipa.prog3.model.io.transaction.commands;

/**
 * Interfaccia per l'implementazione del design pattern Command
 */
public interface Command {
    /**
     * Esegue un'operazione
     * @return true se l'operazione è andata a buon fine, false in caso contrario
     */
    boolean execute();

    /**
     * Ripristina l'operazione
     * @return true se il ripristino è andato a buon fine, false in caso contrario
     */
    boolean undo();
}
