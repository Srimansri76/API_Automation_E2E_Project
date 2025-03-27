package CURD;

import Base.Baseset;
import endpoints.baseapi;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payload.Bookingdata;
import utility.DataProviderUtil;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class createbookingwithmultipleusersdata extends Baseset {

    String bookingId;

    private static final Logger logger = LoggerFactory.getLogger(createbookingwithmultipleusersdata.class);


    private static String bookingIds = ""; // its is for { } this type response


    // ✅ Static list to store all booking IDs across multiple test runs
    private static final List<String> bookingIdList = new ArrayList<>();

    @Test(dataProvider = "BookingDataProvider", dataProviderClass = DataProviderUtil.class)
    @Description("Verify weather Create a booking working or not with excel multiple users data")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Sriman")
    @Epic("Create Booking")
    @Feature("Create a booking with multiple users data")
    public void createbookingwithmultipleusers(Bookingdata bookingdata) {

        String bookingId;

        logger.info(" +++++ API Started +++++");

        requestspecification.basePath(baseapi.CREATE_GET_API);
        response = RestAssured.given()
                .spec(requestspecification)
                .accept("application/json")
                .body(bookingdata)
                .when().post();

        logger.info("API Executed");
        logger.info("API validation started");

         JsonPath jsonPath = JsonPath.from(response.asString());
         bookingId = jsonPath.getString("bookingid");

        // ✅ Add the new booking ID to the string variable & this is for { } this type response
        if (bookingIds.isEmpty()) {
            bookingIds = bookingId;
        } else {
            bookingIds += ", " + bookingId;
        }

        logger.info("Booking ID stored: " + bookingId); // for debugging

        //for validation storing responese
        validatableResponse = response.then().log().all();


        // ✅ Fetch the booking details from response
        String responseFirstname = jsonPath.getString("booking.firstname");
        String responseLastname = jsonPath.getString("booking.lastname");
        //float responseTotalPrice = jsonPath.getFloat("booking.totalprice");
        boolean responseDepositPaid = jsonPath.getBoolean("booking.depositpaid");
//        String responseCheckin = jsonPath.getString("booking.bookingdates.checkin");
//        String responseCheckout = jsonPath.getString("booking.bookingdates.checkout");
        String responseAdditionalNeeds = jsonPath.getString("booking.additionalneeds");

        // ✅ Validate Response Data Against Input Data
        assertEquals(responseFirstname, bookingdata.getFirstname(), "Firstname mismatch!");
        assertEquals(responseLastname, bookingdata.getLastname(), "Lastname mismatch!");
        //assertEquals(responseTotalPrice, bookingdata.getTotalprice(), "Total price mismatch!");
        assertEquals(responseDepositPaid, bookingdata.getDepositpaid(), "DepositPaid mismatch!");
        //assertEquals(responseCheckin, bookingdata.getBookingdates().getCheckin(), "Check-in mismatch!");
       // assertEquals(responseCheckout, bookingdata.getBookingdates().getCheckout(), "Check-out mismatch!");
        assertEquals(responseAdditionalNeeds, bookingdata.getAdditionalneeds(), "Additional needs mismatch!");

        bookingIdList.add(bookingId);

        //for validation storing responese
        validatableResponse = response.then().log().all();



    }

    // ✅ Print all booking IDs at the end of each test run and this is for { } this type response
    @AfterMethod
    public void printBookingIds() {
        System.out.println("Booking IDs so far: {" + bookingIds + "}");
    }

    // ✅ Print all booking IDs at the end of test execution and this is for [ ] this type response one by one
    @AfterClass
    public void printAllBookingIds() {
        System.out.println("===== All Booking IDs Generated =====");
        for (String id : bookingIdList) {
            System.out.println("Booking ID: " + id);
        }
        System.out.println("=====================================");
    }


}
