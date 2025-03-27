package Base;

import actions.Assert_Actions;
import endpoints.baseapi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import modules.PayloadManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class Baseset {


    public static String TOKEN = "";
    public static String ACCEPT = "application/json";

  public  PayloadManager payloadManager;
  public  Assert_Actions assert_actions;
  public  RequestSpecification requestspecification;
  public Response response;
  public ValidatableResponse validatableResponse;
  public JsonPath jsonPath;

    @BeforeMethod
    public void SetUpConfig() {
        System.out.println(" !!!!! Test Started !!!!! ");


        payloadManager = new PayloadManager();
        assert_actions = new Assert_Actions();

        requestspecification = (RequestSpecification) new RequestSpecBuilder()
                .setBaseUri(baseapi.BASE_URI)
                .addHeader("content-type", "application/json")
                .build().log().all();
    }


    public String gettoken(){


        TOKEN = given().spec(requestspecification)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().post(baseapi.TOKENAPI)
                .then().log().all().extract().path("token");


        return TOKEN;

    }




@AfterMethod
    public void tearDown() {
        System.out.println(" !!!!! Test Completed !!!!! ");
    }



}
