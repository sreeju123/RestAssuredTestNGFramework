package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

/**
 * @author Sreej
 */
public class RestResource {

    public static Response post(String path, String token, Object requestPlaylist) {
        return given().
                spec(getRequestSpec()).
                body(requestPlaylist).
                auth().oauth2(token).
                when().
                post(path).
                then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {
        return given(getAccountRequestSpec()).
                formParams(formParams).
                when().
                post(API + TOKEN).
                then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String path, String token) {
        return given().
                spec(getRequestSpec()).
                header("Authorization", "Bearer " + token).
                when().
                get(path).
                then().
                spec(getResponseSpec()).
                assertThat().
                extract().
                response();

    }

    public static Response update(String path, String token, Object requestPlaylist) {
        return given().
                spec(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + token).
                when().
                put(path).
                then().
                spec(getResponseSpec()).
                extract().
                response();
    }

}
