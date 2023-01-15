package unipa.prog3.model.io.transaction;

import unipa.prog3.model.io.transaction.commands.Command;

import java.util.Vector;

/**
 * Agente che si occupa dell'esecuzione di una coda di transazioni,
 * ossia accessi atomici ai file sul file system
 */
public class TransactionInvoker {
    private final Vector<Command> commands;
    private int currentCommand;

    public TransactionInvoker() {
        commands = new Vector<>();
    }

    /**
     * Aggiunge un comando alla coda
     * @param command Comando da aggiungere alla coda
     */
    public void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Esegue tutti i comandi presenti nella coda in un thread separato
     */
    public void commit() {
        // Crea un nuovo thread per eseguire la transazione
        Thread thread = new Thread(() -> {
            while (currentCommand < commands.size())
                if (!commands.get(currentCommand).execute()) {
                    // Prova ad effettuare il rollback della transazione se fallisce
                    try {
                        restore();
                    } catch (Exception e) {
                        System.err.println("La transazione è fallita ed alcune modifiche non sono state annullate!");
                    }
                    break;
                } else currentCommand++;
        });

        // Lancia il thread
        thread.start();

        // Attende che il thread finisca le sue operazioni
        while(thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Effettua a ritroso il ripristino dei comandi eseguiti.
     * @throws Exception Quando un ripristino non è andato a buon fine.
     */
    public void restore() throws Exception {
        for (; currentCommand >= 0; currentCommand--)
            if (!commands.get(currentCommand).undo())
                throw new Exception("File restoring failed!");
    }
}
