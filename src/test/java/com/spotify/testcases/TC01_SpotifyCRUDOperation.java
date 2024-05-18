package com.spotify.testcases;

import static com.spotify.base.SpecBuilder.getReqSpecification;
import static com.spotify.base.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static com.spotify.api.PlayListAPI.*;

import org.testng.annotations.Test;

import com.spotify.pojo.ErrorRoot;
import com.spotify.pojo.PlayList;
import com.util.PropertiesReader;

import io.restassured.response.Response;

public class TC01_SpotifyCRUDOperation {
	
	
	public String playListID = "";
	public String newResName = "";
	public String newResDesc = "";
	public boolean newResPublic = false;
	
	
	@Test(priority = 1)
	public void createPlayList() {
		PlayList reqPlayList = new PlayList();
		reqPlayList.setName("Batch 4 Playlist created using Pojo Class");
		reqPlayList.setDescription("Incident Created for Batch 4 from POJO Class using searialization.");
		reqPlayList.setPublic(true);
		
		Response response = postCreatePlayList(reqPlayList);
		
		PlayList responsePlayList = response.as(PlayList.class);
		playListID = responsePlayList.getId();
		System.out.println("Playlist ID : "+playListID);
		
		assertThat(response.getStatusCode(), equalTo(201));
		assertThat(responsePlayList.getName(), equalTo(reqPlayList.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(reqPlayList.getDescription()));
		assertThat(responsePlayList.getPublic(), equalTo(reqPlayList.getPublic()));
		
		newResName = responsePlayList.getName();
		newResDesc = responsePlayList.getDescription();
		newResPublic = responsePlayList.getPublic();
	
	}
	
	@Test(priority = 2)
	public void updatePlayList() {
		PlayList reqPlayList = new PlayList();
		reqPlayList.setName("Batch 4 Playlist created using Pojo Class");
		reqPlayList.setDescription("Incident Created for Batch 4 from POJO Class using searialization.");
		reqPlayList.setPublic(true);
		
		Response response = editPlayList(reqPlayList, playListID);
		assertThat(response.getStatusCode(), equalTo(200));
	}
	
	@Test(priority = 3)
	public void getPlayListBasedOnPlayListID() {
		Response response = getPlayList(playListID);
		PlayList responsePlayList = response.as(PlayList.class);
		
		assertThat(responsePlayList.getName(), equalTo(newResName));
		assertThat(responsePlayList.getDescription(), equalTo(newResDesc));
		assertThat(responsePlayList.getPublic(), equalTo(newResPublic));
	}
	

	@Test(priority = 4)
	public void createPlayListWithInvalidPayload() {
		PlayList reqPlayList = new PlayList();
		reqPlayList.setName("");
		reqPlayList.setDescription("Incident Created for Batch 4 from POJO Class using searialization.");
		reqPlayList.setPublic(true);
		
		Response response = postCreatePlayList(reqPlayList);
		
		ErrorRoot responseError = response.as(ErrorRoot.class);
		
		assertThat(responseError.getError().getStatus(), equalTo(400));
		assertThat(responseError.getError().getMessage(), equalTo("Missing required field: name"));
	}
	
}
