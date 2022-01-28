package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigReader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

/**
 * @author Sreej
 */
public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;


     /*
     synchronized keyword will make method to wait, till current thread releases the method
     inorder to avoid calling this method multiple times.
     */
    public synchronized static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing token...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is good to use");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get token");
        }
        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("client_id", ConfigReader.getInstance().getClientId());
        formParams.put("client_secret", ConfigReader.getInstance().getClientSecret());
        formParams.put("refresh_token", ConfigReader.getInstance().getRefreshToken());
        formParams.put("grant_type", ConfigReader.getInstance().getGrantType());

        Response response = RestResource.postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Renew Token failed");
        }

        return response;
    }

}
