package test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class SlackChannelController {

    public Response createChannelName(String channelName,String authToken){
        RestAssured.baseURI="https://slack.com/api/";
        Response response = given()
                .header("token", authToken)
                .header("is_private",false)
                .header("name",channelName)
                .header("Content-Type", "application/json")
                .post("admin.conversations.create");
        response.prettyPrint();
        return response;
    }

    public Response joinChannel(String channelId,String authToken){
        RestAssured.baseURI="https://slack.com/api/";
        Response response=given()
                .header("token", authToken)
                .header("Content-Type", "application/json")
                .queryParam("channel",channelId)
                .post("conversations.join");
        response.prettyPrint();
        return response;
    }

    public Response renameChannel(String channelId,String newName,String authToken){
        RestAssured.baseURI="https://slack.com/api/";
        Response response=given()
                .header("token", authToken)
                .header("Content-Type", "application/json")
                .header("channel_id",channelId)
                .queryParam("name",newName)
                .post("admin.conversations.rename");
        response.prettyPrint();
        return response;
    }
}
