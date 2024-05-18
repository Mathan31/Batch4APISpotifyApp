package com.spotify.base;

import static io.restassured.RestAssured.baseURI;

import org.testng.annotations.BeforeClass;

import com.util.PropertiesReader;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
	public static String fileName = "environment";
	public static String baseURI = PropertiesReader.getPropertyValue(fileName, "uri");
	public static String accessToken = PropertiesReader.getPropertyValue(fileName, "access_token");

	
	public static RequestSpecification getReqSpecification() {
		return new RequestSpecBuilder()
		.addHeader("Authorization","Bearer "+accessToken)
		.setBaseUri(baseURI)
		.setContentType(ContentType.JSON)
		.log(LogDetail.ALL).build();
	}
	
	public static ResponseSpecification getResponseSpecification() {
		return new ResponseSpecBuilder()
				.log(LogDetail.ALL).build();
	}


}
