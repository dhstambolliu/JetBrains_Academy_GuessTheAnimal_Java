package animals.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.util.Objects.nonNull;

public class Menu implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private final List<Entry> menu = new ArrayList<>();
    private final String title;
    private final ResourceBundle bundle;
    private final String exitMessage;
    private boolean once;

    public Menu(final String bundleName) {
        this.bundle = ResourceBundle.getBundle(bundleName);
        this.title = String.format("%n%s%n", bundle.getString("title"));
        exitMessage = " 0. " + bundle.getString("exit");
    }

    public Menu once() {
        once = true;
        return this;
    }

    public Menu add(final String key, final Runnable action) {
        final var menuNumber = menu.size() + 1;
        final String entry = nonNull(bundle) ? bundle.getString(key) : key;
        menu.add(new Entry(String.format("%2d. %s", menuNumber, entry), action));
        return this;
    }

    @Override
    public void run() {
        do {
            System.out.println(title);
            menu.forEach(System.out::println);
            System.out.println(exitMessage);
            System.out.print(" ");
            try {
                final int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice == -1) {
                    return;
                }
                menu.get(choice).run();
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                final var msg = nonNull(bundle)
                        ? bundle.getString("error")
                        : "Please enter the number from 0 up to {0}";
                System.out.println(MessageFormat.format(msg, menu.size()));
            }
        } while (!once);
    }

    private static final class Entry implements Runnable {
        private final String name;
        private final Runnable action;

        Entry(final String name, final Runnable action) {
            this.name = name;
            this.action = action;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public void run() {
            action.run();
        }
    }

}