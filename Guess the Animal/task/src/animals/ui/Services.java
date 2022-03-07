package animals.ui;

import animals.domain.LanguageRules;

public class Services {
    private static final UI ui = new UI("services");

    public static boolean askYesNo() {
        while (true) {
            final var respond = ui.readLine();
            if (LanguageRules.IS_POSITIVE_ANSWER.test(respond)) {
                return true;
            }
            if (LanguageRules.IS_NEGATIVE_ANSWER.test(respond)) {
                return false;
            }
            ui.println("ask.again");
        }
    }

    public static String askAnimal() {
        return LanguageRules.ANIMAL.apply(ui.readLine());
    }

    public static String askFavoriteAnimal() {
        ui.println("ask.favorite.animal");
        return LanguageRules.ANIMAL.apply(ui.readLine());
    }
}