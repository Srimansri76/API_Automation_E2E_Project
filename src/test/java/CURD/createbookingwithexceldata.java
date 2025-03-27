package CURD;

import Base.Baseset;
import com.fasterxml.jackson.core.JsonProcessingException;
import endpoints.baseapi;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.Bookingdata;
import utility.DataProviderUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class createbookingwithexceldata extends Baseset {

    private static final Logger logger = LoggerFactory.getLogger(createbookingwithexceldata.class);

    @Owner("Sriman")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify weather Create a booking working or not with excel data")
    @Feature("Create a booking with excel data")
    @Test(dataProvider = "BookingDataProvider", dataProviderClass = DataProviderUtil.class)
    public void createBookingTest(Bookingdata bookingdata) {
        String bookingId;

        logger.info("API Started");

        requestspecification.basePath(baseapi.CREATE_GET_API);
        response = RestAssured.given()
                .spec(requestspecification)
                .accept("application/json")
                .body(bookingdata)
                .when().post();

        logger.info("API validation started");

        //for validation storing responese
        validatableResponse = response.then().log().all();

        // its one type of validation
        validatableResponse.statusCode(200);
        validatableResponse.body("booking.firstname", equalTo("Narendra"));
        validatableResponse.body("booking.totalprice", equalTo(76));
        validatableResponse.body("booking.depositpaid", equalTo(true));
        validatableResponse.body("booking.bookingdates.checkin", equalTo("2020-01-01"));
        validatableResponse.body("booking.bookingdates.checkout", equalTo("2020-01-02"));
        validatableResponse.body("booking.additionalneeds", equalTo("Breakfast"));
        validatableResponse.header("Content-Type", "application/json; charset=utf-8");

        logger.info("API Field  validation success");

        jsonPath = JsonPath.from(response.asString());
        bookingId = jsonPath.getString("bookingid");

        assertThat(response.body().asString()).isNotNull().contains("firstname").isNotBlank().isEqualTo(response.body().asString());

        // ✅ **TestNG Assertion**
        Assert.assertEquals(jsonPath.getString("booking.firstname"), "Narendra", "Firstname does not match in POST response!");

        // ✅ **AssertJ Assertion**
        assertThat(jsonPath.getString("booking.firstname")).isEqualTo("Narendra");

        // ✅ **JSON Schema Validation**

        logger.info("API Schama validation started");

validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createbookingschema.json"));

        logger.info("API Schama validation Suceess");

        System.out.println("----------------------------------------------------------------------------  ");
        System.out.println(jsonPath.getString("bookingid"));
        System.out.println("---------------------------------------------------------------------------- ");


        logger.info("API Ended");
    }
}
