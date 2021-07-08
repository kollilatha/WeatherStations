package com.defination;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpStatus;
import org.testng.Assert;

import com.typesafe.config.Config;

import config.ConfigProvider;
import constants.EndPoint;
import constants.Index;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.CommonUtils;
import utils.FileManager;

public class RegisterWeatherStation {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	Response response;

	@Given("Adding new station")
	public void adding_new_station() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		RestAssured.baseURI = conf.getString(Index.BASE_URI);
		this.requestSpecification = RestAssured.given();
		this.requestSpecification.headers(ConfigProvider.config().getObject(Index.HEADERS).unwrapped());
		this.requestSpecification.body(FileManager.loadJsonFile(Index.ADD_WEATHER_STATION_PAYLOAD));
		this.requestSpecification.log().all();
		responseSpecification = requestSpecification.expect();
		responseSpecification.log().all();
	}

	@When("we give request  with detials")
	public void we_give_request_with_detials() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		requestSpecification.queryParam(conf.getString(Index.KEY), conf.getString(Index.VALUE));
		response = requestSpecification.post(EndPoint.RETRIEVE_STATION_DETAIL);
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_CREATED);

	}

	@When("^we give request  with invalid detials$")
	public void we_give_request_with_invalid_detials() throws Throwable {
		requestSpecification.queryParam("APPID2", "abcdefghiklj");
		response = requestSpecification.post("/stations");
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_UNAUTHORIZED);

	}

	@Then("^the station shoukd be created$")
	public void the_station_shoukd_be_created() {

		response.then().assertThat()
				.body(matchesJsonSchema(FileManager.loadJsonFile(Index.EXPECTED_STATION_RESPONSE_SCHEMA)));
		JsonPath actualResponse = response.jsonPath();
		Assert.assertEquals("DEMO_TEST001", actualResponse.getString("external_id"));
		Assert.assertEquals("Interview Station 007", actualResponse.getString("name"));
		Assert.assertEquals("33.33", actualResponse.getString("latitude"));
		Assert.assertEquals("-111.43", actualResponse.getString("longitude"));
		Assert.assertEquals("444", actualResponse.getString("altitude"));

	}

}
