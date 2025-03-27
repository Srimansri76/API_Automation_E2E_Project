package actions;

import io.restassured.response.Response;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;

public class Assert_Actions {

        /**
         * Validate response status code
         */
        public static void validateStatusCode(Response response, int expectedStatusCode) {
            Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                    "Status code does not match!");
        }

        /**
         * Validate response contains a key
         */
        public static void validateJsonKeyExists(Response response, String key) {
            JsonPath jsonPath = response.jsonPath();
            Assert.assertTrue(jsonPath.get(key) != null,
                    "Key '" + key + "' not found in response!");
        }

        /**
         * Validate JSON key has a specific value
         */
        public static void validateJsonValue(Response response, String key, String expectedValue) {
            JsonPath jsonPath = response.jsonPath();
            String actualValue = jsonPath.getString(key);
            Assert.assertEquals(actualValue, expectedValue,
                    "Value for key '" + key + "' does not match!");
        }

        /**
         * Validate response time is within the expected range
         */
        public static void validateResponseTime(Response response, long maxTimeInMs) {
            Assert.assertTrue(response.getTime() <= maxTimeInMs,
                    "Response time exceeded " + maxTimeInMs + "ms!");
        }

        /**
         * Validate response header contains a specific value
         */
        public static void validateHeader(Response response, String headerName, String expectedValue) {
            String actualValue = response.getHeader(headerName);
            Assert.assertEquals(actualValue, expectedValue,
                    "Header value does not match for: " + headerName);
        }
    }


