package animals.domain;

import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public enum LanguageRules {
    ANIMAL("animal"),
    ANIMAL_NAME("animalName"),
    ANIMAL_QUESTION("animalQuestion"),
    STATEMENT("statement"),
    POSITIVE_FACT("positiveFact"),
    NEGATIVE_FACT("negativeFact"),
    STATEMENT_QUESTION("statementQuestion");

    public static final Predicate<String> IS_CORRECT_STATEMENT = (Predicate<String>) ResourceBundle
            .getBundle("animals.localization.LanguageRules")
            .getObject("isCorrectStatement");
    public static final Predicate<String> IS_POSITIVE_ANSWER = (Predicate<String>) ResourceBundle
            .getBundle("animals.localization.LanguageRules")
            .getObject("isPositiveAnswer");
    public static final Predicate<String> IS_NEGATIVE_ANSWER = (Predicate<String>) ResourceBundle
            .getBundle("animals.localization.LanguageRules")
            .getObject("isNegativeAnswer");
    private static final BiFunction<String, String, String> GENERATE_POSITIVE_FACT =
            (BiFunction<String, String, String>) ResourceBundle
                    .getBundle("animals.localization.LanguageRules")
                    .getObject("generatePositiveFact");
    private static final BiFunction<String, String, String> GENERATE_NEGATIVE_FACT =
            (BiFunction<String, String, String>) ResourceBundle
                    .getBundle("animals.localization.LanguageRules")
                    .getObject("generateNegativeFact");
    public static final Function<Boolean, BiFunction<String, String, String>> FACT_GENERATOR = isPositive ->
            isPositive ? GENERATE_POSITIVE_FACT : GENERATE_NEGATIVE_FACT;
    private final UnaryOperator<String> rule;

    LanguageRules(String ruleName) {
        this.rule = (UnaryOperator<String>) ResourceBundle
                .getBundle("animals.localization.LanguageRules")
                .getObject(ruleName);
    }

    public String apply(String data) {
        return rule.apply(data);
    }
}