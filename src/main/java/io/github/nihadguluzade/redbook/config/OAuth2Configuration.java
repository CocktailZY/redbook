package io.github.nihadguluzade.redbook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
@PropertySource("classpath:reddit.properties")
public class OAuth2Configuration {

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("${userAuthorizationUri}")
    private String userAuthorizationUri;

    @Value("${redirectUri}")
    private String redirectUri;

    private static String userAgent;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.redditClientRegistration());
    }

    private ClientRegistration redditClientRegistration() {
        return ClientRegistration
                .withRegistrationId("reddit")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope(Arrays.asList("identity", "read", "vote", "save"))
                .authorizationUri(userAuthorizationUri)
                .tokenUri(accessTokenUri)
                .userInfoUri("https://oauth.reddit.com/api/v1/me")
                .userNameAttributeName("name")
                .clientName("Reddit")
                .build();
    }

    @Value("${userAgent}")
    public void setUserAgent(String userAgent) {
        OAuth2Configuration.userAgent = userAgent;
    }

    public static String getUserAgent() {
        return userAgent;
    }
}
