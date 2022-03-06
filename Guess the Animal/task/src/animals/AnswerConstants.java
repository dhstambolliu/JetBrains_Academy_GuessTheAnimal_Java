package animals;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public enum AnswerConstants {
    YES(List.of("Yes"), List.of("y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct", "indeed", "you bet", "exactly", "you said it")),
    NO(List.of("No"), List.of("n", "no", "no way", "nah", "nope", "negative", "I don't think so", "yeah no")),
    OTHER(List.of("Come on, yes or no?",
            "I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no."),
            List.of());

    private final List<String> machineAnswers;
    private final List<String> synonims;

    AnswerConstants(List<String> machineAnswers, List<String> synonims) {
        this.machineAnswers = machineAnswers;
        this.synonims = synonims;
    }

    public String getMachineAnswer() {
        if (this == AnswerConstants.OTHER) {
            return OTHER.machineAnswers.get(Logic.randomIntBetween(0, this.machineAnswers.size()));
        }
        return this.machineAnswers.get(0);
    }

    public static AnswerConstants getUserAnswer(String userAnswer) {
        if (NO.synonims.stream().map(c -> Pattern.compile(c + "[!.]?")).anyMatch(c -> c.matcher(userAnswer.toLowerCase(Locale.ROOT).trim()).matches())) {
            return NO;
        }

        if (YES.synonims.stream().map(c -> Pattern.compile(c + "[!.]?")).anyMatch(c -> c.matcher(userAnswer.toLowerCase(Locale.ROOT).trim()).matches())) {
            return YES;
        }

        return OTHER;
    }
}