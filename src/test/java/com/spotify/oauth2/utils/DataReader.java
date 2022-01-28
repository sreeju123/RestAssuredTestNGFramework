package com.spotify.oauth2.utils;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Sreej
 */
public class DataReader {


    private final Properties properties;
    private static DataReader dataReader;

    private DataReader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }

    public static DataReader getInstance() {
        if (Objects.isNull(dataReader)) {
            dataReader = new DataReader();
        }
        return dataReader;
    }

    public String getGetPlaylistId() {
        String prop = properties.getProperty("get_playlist_id");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property get_playlist_id is not specified in the data.properties file");
    }

    public String getUpdatePlaylistId() {
        String prop = properties.getProperty("update_playlist_id");
        if (Objects.nonNull(prop)) {
            return prop;
        } else throw new RuntimeException("property update_playlist_id is not specified in the data.properties file");
    }

}
