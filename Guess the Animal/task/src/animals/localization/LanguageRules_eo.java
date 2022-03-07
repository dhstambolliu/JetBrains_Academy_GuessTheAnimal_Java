package animals.localization;

import java.util.ListResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static animals.localization.SentenceGenerator.capitalize;

public class LanguageRules_eo extends ListResourceBundle {
    private static final Pattern IS_CORRECT_STATEMENT = Pattern.compile("ĝi .+");
    private static final Pattern POSITIVE_ANSWER = Pattern.compile("(j|jes|certe)!?");
    private static final Pattern NEGATIVE_ANSWER = Pattern.compile("(n|ne)!?");

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"animal", (UnaryOperator<String>) input -> input},
                {"statement", (UnaryOperator<String>) data ->
                        data.toLowerCase().trim().replaceFirst("(.+)\\.?", "$1")},

                {"isCorrectStatement", (Predicate<String>) data -> IS_CORRECT_STATEMENT.matcher(data).matches()},

                {"animalName", (UnaryOperator<String>) input -> input},

                {"animalQuestion", (UnaryOperator<String>) data -> "Ĉu ĝi estas " + data + "?"},

                {"statementQuestion", (UnaryOperator<String>) data -> "Ĉu " + data + "?"},

                {"negativeFact", (UnaryOperator<String>) data ->
                        data.replaceFirst("ĝi", "Ĝi ne") + "."},
                {"positiveFact", (UnaryOperator<String>) data -> capitalize(data + ".")},

                {"generatePositiveFact", (BiFunction<String, String, String>) (statement, animal) ->
                        statement.replaceFirst("ĝi", "La " + animal) + "."
                },
                {"generateNegativeFact", (BiFunction<String, String, String>) (statement, animal) ->
                        statement.replaceFirst("ĝi", "La " + animal + " ne") + "."
                },
                {"isPositiveAnswer", (Predicate<String>) data -> POSITIVE_ANSWER.matcher(data).matches()},
                {"isNegativeAnswer", (Predicate<String>) data -> NEGATIVE_ANSWER.matcher(data).matches()}
        };
    }

}