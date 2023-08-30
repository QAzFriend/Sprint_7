package qa_scooter.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static qa_scooter.praktikum.courier.CourierGenerator.*;
import qa_scooter.praktikum.constants.Url;
import qa_scooter.praktikum.courier.Courier;
import qa_scooter.praktikum.courier.CourierClient;


public class CourierCreateTest {

    private CourierClient courierClient = new CourierClient();
    private String id;

    @Before
    public void setUp() {
        RestAssured.baseURI = Url.BASE_URI;
    }

    @Test
    public void createCourierStatus201() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        assertEquals("Неверный статус код", SC_CREATED, response.statusCode());
    }

    @Test
    public void createCourierDuplicate() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        Response response = courierClient.create(courier);
        id = response.path("id");
        response.then().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        assertEquals("Неверный статус код", HttpStatus.SC_CONFLICT, response.statusCode());
    }


    @Test
    public void createCourierTrue() {
        Courier courier = randomCourier();
        Response response = courierClient.create(courier);
        id = response.path("id");
        response.then().body("ok",equalTo(true));
    }

    @Test
    public void createCourierWithoutLogin() {
        Courier courier = randomCourierWithoutLogin();
        Response response = courierClient.create(courier);
        id = response.path("id");
        assertEquals("Неверный статус код", SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void createCourierWithoutPassword() {
        Courier courier = randomCourierWithoutPassword();
        Response response = courierClient.create(courier);
        id = response.path("id");
        assertEquals("Неверный статус код", SC_BAD_REQUEST, response.statusCode());
    }

    @Test
    public void createCourierWithoutName() {
        Courier courier = randomCourierWithoutFirstName();
        Response response = courierClient.create(courier);
        id = response.path("id");
        assertEquals("Неверный статус код", SC_CREATED, response.statusCode());
    }

    @Before
    public void tearDown() {
        courierClient.delete(id);
    }
}
