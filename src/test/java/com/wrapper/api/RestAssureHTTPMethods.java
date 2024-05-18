package com.wrapper.api;

import static com.spotify.base.SpecBuilder.getReqSpecification;
import static com.spotify.base.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class RestAssureHTTPMethods {
	
	public static Response post(Object requestObj,String resource) {
		return given(getReqSpecification())
				.body(requestObj)
				.when()
				.post(resource)
				.then()
				.spec(getResponseSpecification())
				.extract()
				.response();
	}
	
	public static Response get(String resource) {
		return given(getReqSpecification())
				.when()
				.get(resource)
				.then()
				.spec(getResponseSpecification())
				.extract().response();
	}
	
	public static Response update(Object requestObj,String resource) {
		return given(getReqSpecification())
				.body(requestObj)
				.when()
				.put(resource)
				.then() 
				.spec(getResponseSpecification())
				.extract().response();
	}

}
