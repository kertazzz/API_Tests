package creators;

import io.qameta.allure.Step;
import models.Pet;

import static io.restassured.RestAssured.given;
import static spec.ProjectSpec.requestSpec;
import static spec.ProjectSpec.responseSpec;

public class PetCreate {
    @Step("Создаем питомца")
    public static Pet createNewPet(Pet pet) {
        return given(requestSpec)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .as(Pet.class);

    }
}
