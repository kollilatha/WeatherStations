package com.defination;

import org.apache.http.HttpStatus;
import org.testng.Assert;

import com.typesafe.config.Config;

import config.ConfigProvider;
import constants.EndPoint;
import constants.Index;
import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class DeleteStation {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	Response response;

	@Given("^User is with endpoint and id$")
	public void user_is_with_endpoint_and_id() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		RestAssured.baseURI = conf.getString(Index.BASE_URI);
		this.requestSpecification = RestAssured.given();
		this.requestSpecification.headers(ConfigProvider.config().getObject(Index.HEADERS).unwrapped());
		this.requestSpecification.log().all();
		responseSpecification = requestSpecification.expect();
		responseSpecification.log().all();
	}

	@Then("^Delete that Weather Station$")
	public void delete_that_Weather_Station() {
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT);
	}

	@When("^User sends the request$")
	public void user_sends_the_request() {
		requestSpecification.pathParam("stationID", "60e32f4809e7430001b9f472");
		requestSpecification.queryParam("APPID", "9753f0d4eddfc2a3349047212fe5f35a");
		response = requestSpecification.delete(EndPoint.RETRIEVE_STATION_DETAILS);
	}

	@Then("^we should get no content found$")
	public void we_should_get_no_content_found() throws Throwable {

		requestSpecification.pathParam("stationID", "60e32f4809e7430001b9f472");
		requestSpecification.queryParam("APPID", "9753f0d4eddfc2a3349047212fe5f35a");
		response = requestSpecification.delete(EndPoint.RETRIEVE_STATION_DETAILS);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
	}
}
