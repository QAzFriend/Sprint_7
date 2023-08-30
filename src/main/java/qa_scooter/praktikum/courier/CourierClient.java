package qa_scooter.praktikum.courier;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String CREATE_URL = "api/v1/courier";
    private static final String LOGIN_URL = "/api/v1/courier/login";
    private static final String DELETE_URL = "/api/v1/courier/";

    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_URL);
    }

    public Response login(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(LOGIN_URL);
    }

    public Response delete(String id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(DELETE_URL+id);
    }

}
