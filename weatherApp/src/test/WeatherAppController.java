package test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class WeatherAppController {
    public Response getLatLogOfCity(String city){
        // Set the base URI
        RestAssured.baseURI = "http://api.openweathermap.org/";
        Response response = given()
                .queryParam("q",city)
                .queryParam("limit",5)
                .queryParam("appid","ec740658adb66f45bb49f9f45774614b")
                .get("geo/1.0/direct");
        return response;
    }

    public Response getWeatherOfCity(String latitude,String longitude){
        RestAssured.baseURI = "http://api.openweathermap.org/";
        Response response = given()
                .queryParam("lat",latitude)
                .queryParam("lon",longitude)
                .queryParam("appid","ec740658adb66f45bb49f9f45774614b")
                .get("data/3.0/onecall");
        return response;
    }
}
