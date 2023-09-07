package qa_scooter.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import qa_scooter.praktikum.constants.Url;
import qa_scooter.praktikum.order.Order;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static qa_scooter.praktikum.utils.Utils.randomInt;
import static qa_scooter.praktikum.utils.Utils.randomString;
@RunWith(Parameterized.class)
public class OrderCreateTest {

        private final String name;
        private final String surname;
        private final String address;
        private final String metroStation;
        private final String phone;
        private final int rentTime;
        private final String deliveryDate;
        private final String comments;
        private List<String> color;


        public OrderCreateTest(String name, String surname, String address, String metroStation,String phone,int rentTime, String deliveryDate, String comments, List<String> color) {
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.comments = comments;
            this.color = color;
        }
//Решил оставить с параметром только цвет
        @Parameterized.Parameters
        public static Object[][] inputUser() {
            return new Object[][]{
                    {randomString(8), randomString(8), randomString(20), randomString(20), "79999999999", randomInt(), "2023-06-01", randomString(25),List.of("BLACK", "GRAY")},
            };
        }
    @Before
    public void setUp() {
        RestAssured.baseURI = Url.BASE_URI;
    }

    @Test
    public void createOrder() {
        Order order = new Order(name, surname, address,metroStation, phone, rentTime, deliveryDate, comments, color);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue());
    }

}
