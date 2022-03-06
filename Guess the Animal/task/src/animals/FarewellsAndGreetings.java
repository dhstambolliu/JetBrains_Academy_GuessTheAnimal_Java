package animals;

import java.util.List;

public enum FarewellsAndGreetings {
    GREETING(List.of("Good morning!", "Good afternoon!", "Good evening!")),
    FAREWELL(List.of("Have a nice day!", "See you soon!", "Bye!"));

    private final List<String> texts;

    FarewellsAndGreetings(List<String> texts) {
        this.texts = texts;
    }

    public static String generateFarewell() {
        return FAREWELL.texts.get(Logic.randomIntBetween(0, FAREWELL.texts.size()));
    }

    public static String generateGreeting() {
        return GREETING.texts.get(Logic.randomIntBetween(0, GREETING.texts.size()));
    }
}