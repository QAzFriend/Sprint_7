package qa_scooter.praktikum.courier;

import static qa_scooter.praktikum.utils.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .withLogin(randomString(8))
                .withPassword(randomString(12))
                .withFirstName(randomString(10));
    }

    public static Courier randomCourierWithoutLogin() {
        return new Courier()
                .withPassword(randomString(12))
                .withFirstName(randomString(10));
    }

    public static Courier randomCourierWithoutPassword() {
        return new Courier()
                .withLogin(randomString(8))
                .withFirstName(randomString(10));
    }

    public static Courier randomCourierWithoutFirstName() {
        return new Courier()
                .withLogin(randomString(8))
                .withPassword(randomString(12));
    }
}
