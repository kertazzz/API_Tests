package tests;

import creators.PetCreate;
import models.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spec.ProjectSpec.requestSpec;
import static spec.ProjectSpec.responseSpec;

public class PetsTests {
    final private String petPath = "/pet";


    @DisplayName("Добавление нового питомца")
    @Test
    public void addNewPet() {
        Pet pet = Pet.builder()
                .name("Doggo")
                .photoUrls(new String[]{"https://petstore.swagger.io/oauth/imageso/dog.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Dogo_argentino_sylwetka.jpg/320px-Dogo_argentino_sylwetka.jpg"})
                .status("Waiting")
                .build();

        Pet responsePet = given(requestSpec)
                .body(pet)
                .when()
                .post(petPath)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(Pet.class);

        step("Проверяем соотвествие созданных и переданных данных", () -> {
            assertEquals(responsePet.getName(), pet.getName());
            assertArrayEquals(responsePet.getPhotoUrls(), pet.getPhotoUrls());
            assertEquals(responsePet.getStatus(), pet.getStatus());
        });
    }

    @DisplayName("Поиск питомца по ID")
    @Test
    public void getPetById() {
        Pet pet = Pet.builder().id(1337L)
                .name("Rex")
                .photoUrls(new String[]{"https://cdn.royalcanin-weshare-online.io/APoMHmsBIYfdNSoCiRsR/v4/bd179h-hub-dogo-argentino-adult-black-and-white"})
                .build();
        Pet createdPet = PetCreate.createNewPet(pet);
        Pet responsePet = given(requestSpec)
                .when()
                .get(petPath + "/" + String.valueOf(createdPet.getId()))
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(Pet.class);
        step("Проверяем запрос по ID к созданному питомцу", () -> {
            assertEquals(responsePet.getId(), pet.getId());
            assertEquals(responsePet.getName(), pet.getName());
            assertArrayEquals(responsePet.getPhotoUrls(), pet.getPhotoUrls());
        });
    }

    @DisplayName("Добавление питомца без body параметров")
    @Test
    public void addEmptyBodyPet() {
        given(requestSpec).when()
                .post(petPath)
                .then()
                .spec(responseSpec)
                .statusCode(405);
    }

    @DisplayName("Добавление питомца c null полями")
    @Test
    public void addNullValuesPet() {
        Pet pet = Pet.builder().build();
        given(requestSpec)
                .body(pet)
                .when()
                .post(petPath)
                .then()
                .spec(responseSpec)
                .statusCode(405);
    }

    @DisplayName("Удаление питомца")
    @Test
    public void deletePet() {
        Pet pet = Pet.builder().id(1338L)
                .name("RexDelete")
                .photoUrls(new String[]{"https://cdn.royalcanin-weshare-online.io/APoMHmsBIYfdNSoCiRsR/v4/bd179h-hub-dogo-argentino-adult-black-and-white"})
                .build();
        step("Создаем питомца для удаления", () -> {
            Pet createdPet = PetCreate.createNewPet(pet);
        });
        step("Проверяем статус-код удаления", () -> {
            given(requestSpec)
                    .when()
                    .delete(petPath + "/" + 1338)
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .extract()
                    .response()
                    .body();
        });

        step("Проверяем отсутствие удаленного питомца ", () -> {
            given(requestSpec)
                    .when()
                    .get(petPath + "/" + String.valueOf(pet.getId()))
                    .then()
                    .spec(responseSpec)
                    .statusCode(404)
                    .body("type", is("error"))
                    .body("message", is("Pet not found"));

        });
    }

    @DisplayName("Удаление несуществующего питомца")
    @Test
    public void deleteNonExistingPet() {
        given(requestSpec)
                .when()
                .delete(petPath + "/" + -137)
                .then()
                .spec(responseSpec)
                .statusCode(404);
    }

}
