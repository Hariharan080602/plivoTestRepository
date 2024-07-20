package test;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static junit.framework.TestCase.assertEquals;
import org.apache.commons.lang3.RandomStringUtils;
import io.restassured.response.Response;
import java.io.*;

public class SlackAutomationSteps {
    public static final String OAUTH_TOKEN="SAMPLE";
    public static final String INVALID_TOKEN = "INVALID";

    //original token removed to git privacy


    Response response1;
    @When("^user create a channel$")
    public void userCreateAChannel() {
        response1=masterSkuReviewController.createChannelName("PlivoInterviewChannel_"+RandomStringUtils.random(5,"1234567890"),OAUTH_TOKEN);
    }

    @Then("^channel should be created successfully$")
    public void channelShouldBeCreatedSuccessfully() {
        assertEquals("Incorrect status", response1.getStatusCode(), 200);
        response1.then()
                .body("ok", equalTo(true));
    }

    Response response2;

    @And("^user joins the created channel$")
    public void userJoinsTheCreatedChannel() {
        String channelId=response1.jsonPath().getString("channel_id");
        response2=masterSkuReviewController.joinChannel(channelId,OAUTH_TOKEN);
        response2.then()
                .body("ok", equalTo(true));
    }

    @Then("^user verifies they joined the channel correctly$")
    public void userVerifiesTheyJoinedTheChannelCorrectly() {
        assertEquals("User joined a wrong channel",response1.jsonPath().getBoolean("channel_id"),equalTo(response2.jsonPath().getString("channel.id")));
    }

    Response response3;
    @And("^user tries to rename the created channel$")
    public void userTriesToRenameTheCreatedChannel() {
        String newName="PlivoInterviewUpdatedChannel_"+RandomStringUtils.random(5,"1234567890");
        response3= masterSkuReviewController.renameChannel
                (response1.jsonPath().getString("channel_id"),newName,OAUTH_TOKEN);
        response3.then()
                .body("ok", equalTo(true));
    }

    @When("^user create a channel with invalid auth token$")
    public void userCreateAChannelWithInvalidAuthToken() {
        response1=masterSkuReviewController.createChannelName("PlivoInterviewChannel_"+RandomStringUtils.random(5,"1234567890"),INVALID_TOKEN);
        response1.then()
                .body("ok", equalTo(false));
    }

    @Then("^channel should not be created successfully$")
    public void channelShouldNotBeCreatedSuccessfully() {
        response1.then()
                .body("error", equalTo("not_authed"));
    }
}
