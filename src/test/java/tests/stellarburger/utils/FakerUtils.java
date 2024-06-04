package tests.stellarburger.utils;

import stellarburgers.pojo.User;

public class FakerUtils {
    private FakerUtils() {}

    public static String getFakeEmail() {
        return Constants.FAKER.letterify("?????@????.ru");
    }

    public static String getFakeName() {
        return Constants.FAKER.name().username();
    }

    public static String getFakePassword() {
        return Constants.FAKER.letterify("??????");
    }

    public static String getFakeHash() {
        return Constants.FAKER.bothify("????????????????????????");
    }

    public static User getFakeUser() {
        return User.with()
                .password(getFakePassword())
                .name(getFakeName())
                .email(getFakeEmail());
    }
}
