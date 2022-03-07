package animals.localization;

import java.util.Map;
import java.util.NoSuchElementException;

final class SentenceGenerator {

    static String generateSentence(String data, Map<String, String> rules) {
        final var rule = rules.entrySet()
                .stream()
                .filter(e -> data.matches(e.getKey()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't generate question from statement: " + data));
        return capitalize(data.replaceFirst(rule.getKey(), rule.getValue()));
    }

    static String capitalize(final String data) {
        return data.substring(0, 1).toUpperCase() + data.substring(1).toLowerCase();
    }
}