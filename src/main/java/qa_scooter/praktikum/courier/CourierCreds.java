package qa_scooter.praktikum.courier;

import static qa_scooter.praktikum.utils.Utils.randomString;

public class CourierCreds {

    private String login;
    private String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFrom(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    public static CourierCreds credsFromNullLogin(Courier courier) {
        return new CourierCreds(null, courier.getPassword());
    }

    public static CourierCreds credsFromNullPassword(Courier courier) {
        return new CourierCreds(courier.getLogin(),null);
    }

    public static CourierCreds credsFromRandom(Courier courier) {
        return new CourierCreds(randomString(8),randomString(12));
    }
}
