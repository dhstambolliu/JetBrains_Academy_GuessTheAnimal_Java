package animals.localization;

import java.util.ListResourceBundle;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static animals.localization.SentenceGenerator.capitalize;
import static animals.localization.SentenceGenerator.generateSentence;

public class LanguageRules extends ListResourceBundle {
    private static final String VOWEL_ARTICLE = "an ";
    private static final String CONSONANT_ARTICLE = "a ";
    private static final String ENGLISH_VOWELS = "aeiou";
    private static final Pattern IS_CORRECT_STATEMENT;
    private static final Pattern POSITIVE_ANSWER;
    private static final Pattern NEGATIVE_ANSWER;

    private static final UnaryOperator<String> ANIMAL_NAME;
    private static final UnaryOperator<String> NEGATIVE_FACT =
            data -> generateSentence(data, Map.of(
                    "it can (.*)", "It can't $1.",
                    "it has (.*)", "It doesn't have $1.",
                    "it is (.*)", "It isn't $1."
            ));

    static {
        IS_CORRECT_STATEMENT = Pattern.compile("it (can|has|is) .+");

        POSITIVE_ANSWER = Pattern.compile(
                "(y|yes|yeah|yep|sure|right|affirmative|correct|indeed|you bet|exactly|you said it)[.!]?");
        NEGATIVE_ANSWER = Pattern.compile(
                "(n|no( way)?|nah|nope|negative|i don't think so|yeah no)[.!]?");

        ANIMAL_NAME = data -> data.substring(data.startsWith(CONSONANT_ARTICLE) ? 2 : 3);
    }

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"animal", (UnaryOperator<String>) input -> {
                    final var name = input.toLowerCase().strip();
                    if (name.startsWith(VOWEL_ARTICLE) || name.startsWith(CONSONANT_ARTICLE)) {
                        return name;
                    } else {
                        final var isStartVowel = ENGLISH_VOWELS.indexOf(name.charAt(0)) > -1;
                        final var article = isStartVowel ? VOWEL_ARTICLE : CONSONANT_ARTICLE;
                        return article + name;
                    }
                }},
                {"statement", (UnaryOperator<String>) data ->
                        data.toLowerCase().trim().replaceFirst("(.+)\\.?", "$1")},

                {"isCorrectStatement", (Predicate<String>) data -> IS_CORRECT_STATEMENT.matcher(data).matches()},

                {"animalName", ANIMAL_NAME},

                {"animalQuestion", (UnaryOperator<String>) data -> "Is it " + data + "?"},

                {"statementQuestion", (UnaryOperator<String>) data -> generateSentence(data, Map.of(
                        "it can (.*)", "Can it $1?",
                        "it has (.*)", "Does it have $1?",
                        "it is (.*)", "Is it $1?"
                ))},
                {"negativeFact", NEGATIVE_FACT},
                {"positiveFact", (UnaryOperator<String>) data -> capitalize(data + ".")},

                {"generatePositiveFact", (BiFunction<String, String, String>) (statement, animal) ->
                        statement.replaceFirst("it", "The " + ANIMAL_NAME.apply(animal)) + "."
                },
                {"generateNegativeFact", (BiFunction<String, String, String>) (statement, animal) ->
                        NEGATIVE_FACT.apply(statement)
                                .replaceFirst("It", "The " + ANIMAL_NAME.apply(animal))
                },
                {"isPositiveAnswer", (Predicate<String>) data -> POSITIVE_ANSWER.matcher(data).matches()},
                {"isNegativeAnswer", (Predicate<String>) data -> NEGATIVE_ANSWER.matcher(data).matches()}
        };
    }

}