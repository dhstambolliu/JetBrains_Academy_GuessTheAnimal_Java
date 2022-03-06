package animals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Main {
    private static final List<String> stringPatterns = List.of("the .*", "a .*", "an .*");
    private static List<Pattern> patterns;

    public static void initPatterns() {
        List<Pattern> patternsCurrent = new ArrayList<>();
        for (String stringPattern : stringPatterns) {
            patternsCurrent.add(Pattern.compile(stringPattern));
        }
        patterns = patternsCurrent;
    }

    public static String getName(String rawName) {
        String rawNamelower = rawName.toLowerCase(Locale.ROOT);

        return patterns.stream().anyMatch(c -> c.matcher(rawNamelower).matches())
                ? Arrays.stream(rawNamelower.split(" ")).skip(1).reduce((result, s) -> result + " " + s).get()
                : rawNamelower;
    }

    public static String getAnimalWithArticle(String animal) {
        String name = getName(animal);
        Pattern pattern = Pattern.compile("([eioa]|xe).*");
        return pattern.matcher(name).matches() ?
                "an " + name :
                "a " + name;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            initPatterns();
            System.out.println(FarewellsAndGreetings.generateGreeting());
            String animal = reader.readLine();
            System.out.println("Is it " + getAnimalWithArticle(animal) + "?");
            String userAnswer = reader.readLine();
            AnswerConstants answer = AnswerConstants.getUserAnswer(userAnswer);

            while (answer == AnswerConstants.OTHER) {
                System.out.println(answer.getMachineAnswer());
                answer = AnswerConstants.getUserAnswer(reader.readLine());
            }

            System.out.println("You answered: " + answer.getMachineAnswer());
            System.out.println();
            System.out.println(FarewellsAndGreetings.generateFarewell());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}