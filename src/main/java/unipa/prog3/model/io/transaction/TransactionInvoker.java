package unipa.prog3.model.io.transaction;

import unipa.prog3.model.io.transaction.commands.Command;

import java.util.Vector;

public class TransactionInvoker {
    private final Vector<Command> commands;
    private int currentCommand;

    public TransactionInvoker() {
        commands = new Vector<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void commit() {
        // Crea un nuovo thread per eseguire la transazione
        Thread thread = new Thread(() -> {
            while (currentCommand < commands.size())
                if (!commands.get(currentCommand).execute()) {
                    // Prova ad effettuare il rollback della transazione quando questa non è riuscita a terminare con successo
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

    public void restore() throws Exception {
        for (; currentCommand >= 0; currentCommand--)
            if (!commands.get(currentCommand).undo())
                throw new Exception("File restoring failed!");
    }
}
