package qa_scooter.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import qa_scooter.praktikum.constants.Url;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrderGetTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Url.BASE_URI;
    }

    @Test
    public void getOrders() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().body("orders.id", notNullValue());
        assertEquals("Неверный статус код", HttpStatus.SC_OK, response.statusCode());
    }

}
