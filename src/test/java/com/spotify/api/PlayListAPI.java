package com.spotify.api;

import static com.spotify.base.SpecBuilder.getReqSpecification;
import static com.spotify.base.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static com.wrapper.api.RestAssureHTTPMethods.*;

import com.spotify.pojo.PlayList;
import com.util.PropertiesReader;

import io.restassured.response.Response;

public class PlayListAPI {
	
	public static String fileName = "environment";
	public static String userID = PropertiesReader.getPropertyValue(fileName, "user_id");
	
	public static Response postCreatePlayList(PlayList reqPlayList) {
		return post(reqPlayList,"/users/"+userID+"/playlists");
	}
	
	public static Response editPlayList(PlayList reqPlayList,String playListID) {
		return update(reqPlayList, "/playlists/"+playListID);
		}
	
	public static Response getPlayList(String playListID) {
		return get("/playlists/"+playListID);
		}

}
