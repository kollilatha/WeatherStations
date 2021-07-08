package com.defination;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import org.apache.http.HttpStatus;
import org.testng.Assert;

import com.typesafe.config.Config;

import config.ConfigProvider;
import constants.EndPoint;
import constants.Index;
import cucumber.api.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.CommonUtils;
import utils.FileManager;

public class RecieveStation {
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	Response response;

	@Given("^Getting single station$")
	public void getting_single_station() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		RestAssured.baseURI = conf.getString(Index.BASE_URI);
		this.requestSpecification = RestAssured.given();
		this.requestSpecification.headers(ConfigProvider.config().getObject(Index.HEADERS).unwrapped());
		this.requestSpecification.log().all();
		
		responseSpecification = requestSpecification.expect();
		responseSpecification.log().all();
	}

	@When("^Send a request with base parameter$")
	public void send_a_request_with_base_parameter() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		requestSpecification.queryParam(conf.getString(Index.KEY), conf.getString(Index.VALUE));
		requestSpecification.pathParam("stationID", conf.getString(Index.ID));
		response = requestSpecification.get(EndPoint.RETRIEVE_STATION_DETAIL);
	}

	@Then("^we will be avaliable with station$")
	public void we_will_be_avaliable_with_station() {
		Config conf = ConfigProvider.config().getConfig(Index.WEATHER_STATION);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		response.then().assertThat().body(matchesJsonSchema(FileManager.loadJsonFile(Index.STATION_RESPONSE_SCHEMA)));
	}

}
