package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataReader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Sreej
 */

@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest {


    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).
                build();
    }


    @Story("Create a Playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("12345")
    @TmsLink("1234567")
    @Description("Description To Create A Playlist")
    @Test(description = "Should Be Able To Create A Playlist")
    public void ShouldBeAbleToCreateAPlaylist() {
        /*
        -->  Normal Pojo
        */
//        Playlist requestPlaylist = new Playlist();
//        requestPlaylist.setName("New Playlist");
//        requestPlaylist.setDescription("New Playlist description");
//        requestPlaylist.set_public(false);

//        --> Any of these can be used <--
        /*
        -->  Builder Pattern
        */
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);


        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.getCode()));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }

    @Description("Description To Get A Playlist")
    @Test(description = "Should Be Able To Get A Playlist")
    public void ShouldBeAbleToGetAPlaylist() {

        Playlist requestPlaylist = playlistBuilder("Updated Playlist", "Updated Playlist description", false);

        Response response = PlaylistApi.get(DataReader.getInstance().getGetPlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }

    @Description("Description To Update A Playlist")
    @Test(description = "Should Be Able To Update A Playlist")
    public void ShouldBeAbleToUpdateAPlaylist() {

        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);

        Response response = PlaylistApi.update(requestPlaylist, DataReader.getInstance().getUpdatePlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.getCode()));

    }

    @Story("Create a Playlist story")
    @Test(description = "Should Not Be Able To Create A Playlist With Name")
    public void ShouldNotBeAbleToCreateAPlaylistWithName() {

        Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_400.getCode()));

        Error responseError = response.as(Error.class);

        assertThat(responseError.getError().getStatus(), equalTo(StatusCode.CODE_400.getCode()));
        assertThat(responseError.getError().getMessage(), equalTo(StatusCode.CODE_400.getMessage()));

    }

    @Story("Create a Playlist story")
    @Test(description = "Should Not Be Able To Create A Playlist With Expired Token")
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {

        String invalid_token = "InvalidToken";

        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);


        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.getCode()));

        Error responseError = response.as(Error.class);

        assertThat(responseError.getError().getStatus(), equalTo(StatusCode.CODE_401.getCode()));
        assertThat(responseError.getError().getMessage(), equalTo(StatusCode.CODE_401.getMessage()));

    }

}
