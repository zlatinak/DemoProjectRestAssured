import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    public static void main(String[] args) {
//validate if Add Place API is working as expected
        //given - all input details
        //when - submit the API, resource and http method
        //then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response); //it take string as input and printed it as JSON
        String placeId = js.getString("place_id");

        System.out.println(placeId);


        //update place

        String newAddress = "Mladost bulevards, Sofia OOO";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //getPlace

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .body("address", equalTo(newAddress)).extract().response().asString();

        JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);

        String actualAddress = js1.getString("address");

        Assert.assertEquals(newAddress,actualAddress,"Addresses are not equals");
        

    }
}
