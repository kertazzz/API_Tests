package oauth2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;


import static io.restassured.RestAssured.given;


public class OAuth2 {


    public static String clientId = "test";
    public static String redirectUri = "https://petstore.swagger.io/oauth2-redirect.html";
    public static String username = "test";
    public static String password = "abc123";
    public static String responseType = "token";
    public static Cookies cookies;



    public static Response authorize() {
        cookies = setup();
        given().cookies(cookies)
                .log()
                .uri()
                .redirects()
                .follow(true)
                .when()
                .queryParam("response_type", responseType)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("username", username)
                .queryParam("password", password)
                .get("/oauth/authorize")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return given()
                .cookies(cookies)
                .log()
                .uri()
                .contentType(ContentType.URLENC)
                .when()
                .formParam("user_oauth_approval", true)
                .formParam("scope.read", true)
                .formParam("scope.write", true)
                .formParam("scope.read:pets", true)
                .formParam("scope.write:pets", true)
                .formParam("authorize", "Authorize")
                .post("/oauth/authorize")
                .then()
                .statusCode(302)
                .log()
                .all()
                .extract().response();
    }

    public static Cookies setup() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        Cookies initialCookies = given()
                .log()
                .uri()
                .queryParam("response_type", responseType)
                .queryParam("client_id", username)
                .queryParam("redirect_uri", redirectUri)
                .get("/oauth/authorize")
                .then()
                .statusCode(Matchers.oneOf(200,302))
                .extract()
                .detailedCookies();

        given().cookies(initialCookies)
                .log()
                .uri()
                .get("/oauth/login.jsp")
                .then()
                .statusCode(200)
                .log()
                .headers();

        return given().cookies(initialCookies)
                .log()
                .uri()
                .with()
                .header("Origin", "https://petstore.swagger.io")
                .header("Referer", "/oauth/login.jsp")
                .contentType(ContentType.URLENC)
                .formParam("username", username)
                .formParam("password", password)
                .post("/oauth/login")
                .then()
                .statusCode(302)
                .extract().detailedCookies();
    }

    public static String getAccessToken()  {
        Response response = authorize();
        String parsedToken = response.getHeader("Location").replaceFirst(".*#", "").replaceFirst("&.*", "");
        String accessToken = parsedToken.replaceFirst(".*=", "");
        Assertions.assertNotNull(accessToken);
        return accessToken;
    }
}