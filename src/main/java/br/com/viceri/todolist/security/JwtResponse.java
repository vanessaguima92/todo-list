package br.com.viceri.todolist.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JwtResponse {
    @JsonProperty("access_token")
    private final String accessToken;
}
