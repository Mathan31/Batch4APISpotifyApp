package com.spotify.testcases;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class SpotifyCRUDOperation {
	
	public String baseURI = "https://api.spotify.com";
	public String accessToken = "BQBSK1pEOfl551ch2smMGt0ydCOnqgE5xQhr8oNp8h4tYXYtLtgo4qEWn2JxWmK3_iTbtJhBk9fAFs2B90kHiZ0stYkl0afZsY_7FeEjPnpWcxrnCsa6zcc2cDKQM8ZRmZ4_iQBrXHY10iAk9mrDlkfDS5lIfbrV5VMoU_4S-Vrt33Nk7mab5km2suVKcp8ZhVi821McvJDILDt6T_kgYZ2aaQLniG6vhkOffo8odfd5JVyvvI7v78ahEo3iU7AxASPtvpxAvVAwd0tR";
	public RequestSpecification reqSpec;
	public ResponseSpecification resSpec;
	public String userID = "31cwetdnd4oeo5wq2c7khzkqt5oe";
	public String playListID = "";
	
	@BeforeClass
	public void ReqAndResSpecification() {
		RequestSpecBuilder reqBuild = new RequestSpecBuilder();
		reqBuild.addHeader("Authorization","Bearer "+accessToken)
		.setBaseUri(baseURI)
		.setContentType(ContentType.JSON)
		.log(LogDetail.ALL);
		reqSpec = reqBuild.build();
		
		ResponseSpecBuilder resBuild = new ResponseSpecBuilder();
		resBuild.log(LogDetail.ALL);
		
		resSpec = resBuild.build();
	}
	
	@Test(priority = 1)
	public void createPlayList() {
		String payLoad = "{\r\n"
				+ "  \"name\": \"Mathan Playlist For Batch 4 created from Rest Assure\",\r\n"
				+ "  \"description\": \"Mathan playlist description for Batch 4 created from Rest Assure\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		JsonPath jsonResponse = given(reqSpec)
		.body(payLoad)
		.when()
		.post("/v1/users/"+userID+"/playlists")
		.then()
		.spec(resSpec)
		.statusCode(201)
		.body("name", equalTo("Mathan Playlist For Batch 4 created from Rest Assure"),
			  "description", equalTo("Mathan playlist description for Batch 4 created from Rest Assure"),
			  "public", equalTo(false))
		.extract()
		.response().jsonPath();
		
		playListID = jsonResponse.getString("id");
		System.out.println("Play List ID is : "+playListID);
	}
	
	@Test(priority = 2)
	public void updatePlayList() {
		String payLoad = "{\r\n"
				+ "  \"name\": \"Mathan Playlist For Batch 4 updated from Rest Assure\",\r\n"
				+ "  \"description\": \"Mathan playlist description for Batch 4 updated from Rest Assure\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		given(reqSpec)
		.body(payLoad)
		.when()
		.put("/v1/playlists/"+playListID)
		.then()
		.spec(resSpec)
		.statusCode(200);
	}
	
	@Test(priority = 3)
	public void getPlayListBasedOnPlayListID() {
		given(reqSpec)
		.when()
		.get("/v1/playlists/"+playListID)
		.then()
		.spec(resSpec)
		.statusCode(200);
	}
	

	@Test(priority = 4)
	public void createPlayListWithInvalidPayload() {
		String payLoad = "{\r\n"
				+ "  \"name\": \"\",\r\n"
				+ "  \"description\": \"Mathan playlist description for Batch 3\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		given(reqSpec)
		.body(payLoad)
		.when()
		.post("/v1/users/"+userID+"/playlists")
		.then()
		.spec(resSpec)
		.statusCode(400)
		.body("error.status", equalTo(400),
			  "error.message", equalTo("Missing required field: name"));
		
	}
	
}
