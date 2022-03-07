package animals;

import animals.repository.KnowledgeBase;
import animals.ui.Application;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.log(Level.CONFIG, "Program started with arguments: {0}.", Arrays.toString(args));

        final KnowledgeBase repository;

        if (args.length > 1 && args[0].equals("-type")) {
            repository = KnowledgeBase.Type.valueOf(args[1].toUpperCase()).getInstance();
        } else {
            repository = KnowledgeBase.Type.IN_MEMORY.getInstance();
        }

        new Application(repository).run();

        LOGGER.log(Level.FINE, "Program finished.");
    }

}