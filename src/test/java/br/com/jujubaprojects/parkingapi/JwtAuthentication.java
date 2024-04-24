package br.com.jujubaprojects.parkingapi;

import br.com.jujubaprojects.parkingapi.Web.dto.UsuarioLoginDto;
import br.com.jujubaprojects.parkingapi.jwt.JwtToken;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration
@SpringBootTest
public class JwtAuthentication {

    @Autowired
    WebTestClient testClient;

    @Test
    public static java.util.function.Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UsuarioLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}