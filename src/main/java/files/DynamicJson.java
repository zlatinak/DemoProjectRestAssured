package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test (dataProvider ="BooksData")
    public void addBook(){
        RestAssured.baseURI="http://216.10.245.166";
        String response=   given().header("Content-Type","application/json").
                body(Payload.AddBook("53w5","23423")).
                when()
                .post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js  = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");

        System.out.println(id);
    }
    @DataProvider (name="BooksData")
    public Object[][] getData(){
      return  new Object[][]{{"sdfsd","2342"}, {"fsdfsd","3245"}, {"sadasd","asdas"}};
    }
}
