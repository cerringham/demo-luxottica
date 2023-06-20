package it.bitrock.demoluxottica.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
    @Value("${keycloak.client.secret}")
    private String secret;
    @Value("${keycloak.client.id}")
    private String clientId;
    @Value("${keycloak.server}")
    private String server;
    @Value("${keycloak.realm}")
    private String realm;


    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(server)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(secret)
                .build();
    }

}
