package animals.localization;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ListResourceBundle;

public class Greetings_eo extends ListResourceBundle {
    private final String[] greetings;

    {
        final var messages = new ArrayList<String>();
        final var time = LocalTime.now();
        if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(18, 0))) {
            messages.add("Bonan tagon!");
        } else if (time.isAfter(LocalTime.of(5, 0)) && time.isBefore(LocalTime.NOON)) {
            messages.add("Bonan matenon!");
        } else if (time.isAfter(LocalTime.of(18, 0))) {
            messages.add("Bonan vesperon!");
        } else if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(4, 0))) {
            messages.add("Saluton Nokta Strigo!");
        } else if (time.isAfter(LocalTime.of(4, 0))) {
            messages.add("Saluton Frua Birdo!");
        }
        messages.add("Saluton!");
        greetings = messages.toArray(String[]::new);
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hi", greetings},
                {"welcome", "Bonvenon al la sperta sistemo de la besto!"},
                {"bye", new String[]{
                        "Ĝis!",
                        "Ĝis revido!",
                        "Estis agrable vidi vin!"
                }}
        };
    }
}