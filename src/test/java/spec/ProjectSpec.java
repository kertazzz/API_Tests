package spec;

import helpers.CustomApiTemplate;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class ProjectSpec {
    public static RequestSpecification requestSpec = with()
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
