package CURD;

import Base.Baseset;
import com.fasterxml.jackson.core.JsonProcessingException;
import endpoints.baseapi;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class restfulcurd extends Baseset {

    private static String bookingId;

@Owner("Sriman")
@Severity(SeverityLevel.CRITICAL)
@Description("Verify weather Create a booking working or not")
@Feature("Create a booking")
@Test  ( priority = 1, groups = {"Sanity","P0"})
public void createbooking() throws JsonProcessingException {


               requestspecification.basePath(baseapi.CREATE_GET_API);
               response = RestAssured.given()
                       .spec(requestspecification)
                       .accept("application/json")
                       .body(payloadManager.createpayload())
                       .when().post();



    //for validation storing responese
    validatableResponse = response.then().log().all();

    // its one type of validation
    validatableResponse.statusCode(200);
    validatableResponse.body("booking.firstname", equalTo("Sriman"));
    validatableResponse.body("booking.totalprice", equalTo(76));
    validatableResponse.body("booking.depositpaid", equalTo(true));
    validatableResponse.body("booking.bookingdates.checkin", equalTo("2020-01-01"));
    validatableResponse.body("booking.bookingdates.checkout", equalTo("2020-01-02"));
    validatableResponse.body("booking.additionalneeds", equalTo("Breakfast"));
    validatableResponse.header("Content-Type", "application/json; charset=utf-8");


    jsonPath = JsonPath.from(response.asString());
    bookingId = jsonPath.getString("bookingid");

   assertThat(response.body().asString()).isNotNull().contains("firstname").isNotBlank().isEqualTo(response.body().asString());

    // ✅ **TestNG Assertion**
    Assert.assertEquals(jsonPath.getString("booking.firstname"), "Sriman", "Firstname does not match in POST response!");

    // ✅ **AssertJ Assertion**
    assertThat(jsonPath.getString("booking.firstname")).isEqualTo("Sriman");

    // ✅ **JSON Schema Validation**



    validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createbookingschema.json"));
    System.out.println("----------------------------------------------------------------------------  ");
    System.out.println(jsonPath.getString("bookingid"));
    System.out.println("---------------------------------------------------------------------------- ");
}



@Owner("Sriman")
@Severity(SeverityLevel.CRITICAL)
@Description("Verify weather booking details fetching or not by bookingid")
@Feature("Get booking details by bookingid")
@Test (priority = 2 ,groups = {"Sanity","P0"})
    public void getbookingbyid() {

    requestspecification.basePath(baseapi.UPDATE_GET_DELETE_BY_BOOKINGID);

    response = RestAssured.given()
            .spec(requestspecification)
            .accept("application/json")
            .pathParam("bookingId", bookingId)
            .when().get();


    //for validation storing responese
    validatableResponse = response.then().log().all();


    // its one type of validation
    validatableResponse.statusCode(200);
    validatableResponse.body("firstname", equalTo("Sriman"));
    validatableResponse.body("totalprice", equalTo(76));
    validatableResponse.body("depositpaid", equalTo(true));
    validatableResponse.body("bookingdates.checkin", equalTo("2020-01-01"));
    validatableResponse.body("bookingdates.checkout", equalTo("2020-01-02"));
    validatableResponse.body("additionalneeds", equalTo("Breakfast"));
    validatableResponse.header("Content-Type", "application/json; charset=utf-8");



    jsonPath = JsonPath.from(response.asString());
    String actualFirstName = jsonPath.getString("firstname");

    // ✅ **TestNG Assertion**
    Assert.assertEquals(actualFirstName, "Sriman", "Firstname does not match in GET response!");
// its one type of validation both are good to use
}

    @Owner("Sriman")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify weather booking details fetching or not by bookingid")
    @Feature("Get booking details by bookingid")
    @Test (priority = 3 ,groups = {"Sanity","P0"})
    public void getbookingbyidwithinvalidid() {

        requestspecification.basePath(baseapi.UPDATE_GET_DELETE_BY_BOOKINGID);

        response = RestAssured.given()
                .spec(requestspecification)
                .accept("application/json")
                .pathParam("bookingId", "invalidBookingId")
                .when().get();

        //for validation storing responese
        validatableResponse = response.then().log().all();



    // its one type of validation
        validatableResponse.statusCode(404);
    }

    @Owner("Sriman")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify weather booking details updating or not by bookingid")
    @Feature("Update booking details by bookingid")
    @Test (priority = 4 ,groups = {"Sanity","P0"})

    public void updatebookingbyid() throws JsonProcessingException {

        // Ensure base path is set correctly
        requestspecification.basePath(baseapi.UPDATE_GET_DELETE_BY_BOOKINGID);

        response = RestAssured.given()
                .spec(requestspecification)
                .pathParam("bookingId", 1)  // ✅ Ensure bookingId is properly passed
                .header("Content-Type", "application/json")  // ✅ Sets request body format
                .header("Accept", "application/json")  // ✅ Sets expected response format
                .header("Cookie", "token= Basic YWRtaW46cGFzc3dvcmQxMjM=")  // ✅ Authorization token
                // .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=") // (Alternative to Cookie, use one)
                .body(payloadManager.updatepayload())
                .when().put();


        //for validation storing responese
        validatableResponse = response.then().log().all();

    }


    @Owner("Sriman")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify weather booking details deleting or not by bookingid")
    @Feature("Delete booking details by bookingid")
    @Test (priority = 5 ,groups = {"Sanity","P0"})
    public void deletebookingbyid() {

        requestspecification.basePath(baseapi.UPDATE_GET_DELETE_BY_BOOKINGID);

        response = RestAssured.given()
                .spec(requestspecification)
                .accept("application/json")
                .pathParam("bookingId", bookingId)
                .when().delete();

        //for validation storing responese
        validatableResponse = response.then().log().all();

    }
    @Owner("Sriman")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify weather booking details deleting or not by bookingid")
    @Feature("Delete booking details by bookingid")
    @Test (priority = 6 ,groups = {"Sanity","P0"})
    public void deletebookingbyidwithinvalidid() {

        requestspecification.basePath(baseapi.UPDATE_GET_DELETE_BY_BOOKINGID);

        response = RestAssured.given()
                .spec(requestspecification)
                .accept("application/json")
                .pathParam("bookingId", "invalidBookingId")
                .when().delete();

        //for validation storing responese
        validatableResponse = response.then().log().all();

        // its one type of validation
        validatableResponse.statusCode(403);

    }

}
