package qa_scooter.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import qa_scooter.praktikum.constants.Url;
import qa_scooter.praktikum.courier.Courier;
import qa_scooter.praktikum.courier.CourierClient;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static qa_scooter.praktikum.courier.CourierCreds.*;
import static qa_scooter.praktikum.courier.CourierGenerator.randomCourier;

public class CourierLoginTest {

    private CourierClient courierClient = new CourierClient();
    private String id;

    @Before
    public void setUp() {
        RestAssured.baseURI = Url.BASE_URI;
    }

    @Test
    public void courierLoginStatus200() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        Response loginResponse = courierClient.login(credsFrom(courier));
        assertEquals("Курьер не залогинен", HttpStatus.SC_OK, loginResponse.statusCode());
        loginResponse.then().assertThat().body("id", notNullValue());
    }

    @Test
    public void courierLoginWithoutLogin() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        Response loginResponse = courierClient.login(credsFromNullLogin(courier));
        assertEquals("Неверный статус код", HttpStatus.SC_BAD_REQUEST, loginResponse.statusCode());
    }

    @Test
    public void courierLoginWithoutPassword() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        Response loginResponse = courierClient.login(credsFromNullPassword(courier));
        assertEquals("Неверный статус код", HttpStatus.SC_GATEWAY_TIMEOUT, loginResponse.statusCode());
    }

    @Test
    public void courierLoginRandom() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        Response loginResponse = courierClient.login(credsFromRandom(courier));
        assertEquals("Неверный статус код", HttpStatus.SC_NOT_FOUND, loginResponse.statusCode());
    }

    @Before
    public void tearDown() {
        courierClient.delete(id);
    }
}
