package qa_scooter.praktikum;

import io.qameta.allure.Step;
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

        private  String name;
        private  String surname;
        private  String address;
        private  String metroStation;
        private  String phone;
        private  int rentTime;
        private  String deliveryDate;
        private  String comments;
        private List<String> color;


      public OrderCreateTest(List<String> color) {this.color = color;}

//да понял, немного затупил при прочтении ревью))
        @Parameterized.Parameters
        public static Object[][] inputUser() {
            return new Object[][]{
                    {List.of()},
                    {List.of("BLACK")},
                    {List.of("BLACK", "GREY")},
                    {List.of("GREY")},
            };
        }
    @Before
    public void setUp() {
        RestAssured.baseURI = Url.BASE_URI;
    }
    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTest(){
        this.name = randomString(8);
        this.surname = randomString(8);
        this.address = randomString(20);
        this.metroStation = randomString(20);
        this.phone = "79508111579";
        this.rentTime = randomInt();
        this.deliveryDate = "2023-06-01";
        this.comments = randomString(25);
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
