package animals.localization;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ListResourceBundle;

public class Greetings extends ListResourceBundle {
    private final String[] greetings;

    {
        final var messages = new ArrayList<String>();
        final var time = LocalTime.now();
        if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(18, 0))) {
            messages.add("Good afternoon!");
        } else if (time.isAfter(LocalTime.of(5, 0)) && time.isBefore(LocalTime.NOON)) {
            messages.add("Good morning!");
        } else if (time.isAfter(LocalTime.of(18, 0))) {
            messages.add("Good evening!");
        } else if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(4, 0))) {
            messages.add("Hi Night Owl!");
        } else if (time.isAfter(LocalTime.of(4, 0))) {
            messages.add("Hi Early Bird!");
        }
        messages.add("Hello!");
        greetings = messages.toArray(String[]::new);
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hi", greetings},
                {"welcome", "Welcome to the animal's expert system!"},
                {"bye", new String[]{
                        "Bye!",
                        "Bye, bye!",
                        "See you later!",
                        "See you soon!",
                        "Talk to you later!",
                        "I’m off!",
                        "It was nice seeing you!",
                        "See ya!",
                        "See you later, alligator!",
                        "In a while, crocodile!",
                        "Hasta la vista, baby!",
                        "Adios, amigos!",
                        "Au revoir!",
                        "Adieu!",
                        "Have a nice one!"
                }}};
    }
}