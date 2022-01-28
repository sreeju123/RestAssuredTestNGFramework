package com.spotify.oauth2.utils;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Sreej
 */
public class ConfigReader {


    private final Properties properties;
    private static ConfigReader configReader;

    private ConfigReader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigReader getInstance() {
        if (Objects.isNull(configReader)) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getClientId() {
        String prop = properties.getProperty("client_id");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property client_id is not specified in the config.properties file");
    }

    public String getClientSecret() {
        String prop = properties.getProperty("client_secret");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property client_secret is not specified in the config.properties file");
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property refresh_token is not specified in the config.properties file");
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property grant_type is not specified in the config.properties file");
    }

    public String getUserId() {
        String prop = properties.getProperty("user_id");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property user_id is not specified in the config.properties file");
    }

}
