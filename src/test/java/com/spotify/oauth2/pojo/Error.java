package com.spotify.oauth2.pojo;

/**
 * @author Sreej
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Getter @Setter
@Jacksonized
@Builder // when we use builder, we also need to mention Jacksonized to identify json
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @JsonProperty("error")
    private InnerError error;

}
