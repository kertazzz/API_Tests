package spec;

import helpers.CustomApiTemplate;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import oauth2.OAuth2;

import static io.restassured.RestAssured.with;

public class ProjectSpec {
    public static RequestSpecification requestSpec = with()
            .auth()
            .oauth2(OAuth2.getAccessToken())
            .cookies(OAuth2.cookies)
            .filter(CustomApiTemplate.withCustomTemplates())
            .log()
            .all()
            .baseUri("https://petstore.swagger.io")
            .basePath("/v2")
            .contentType(ContentType.JSON);
    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
}
