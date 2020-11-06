package io.github.nihadguluzade.redbook.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.*;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class AccessTokenProvider extends AuthorizationCodeAccessTokenProvider implements Serializable {

    private String accessUri = "https://www.reddit.com/api/v1/access_token";
    private final String client_id = "your_client_id";
    private final String client_secret = "your_client_secret";
    private final String userAgent = "your_user_agent";
    private String clientToken;
    private static String oauth2Token;
    private boolean OAuth2TokenActive;

    private Logger logger = Logger.getLogger(getClass().getName());

    public String obtainAccessToken() {
        BaseOAuth2ProtectedResourceDetails details = new BaseOAuth2ProtectedResourceDetails();
        AccessTokenRequest request =  new DefaultAccessTokenRequest();

        details.setAccessTokenUri(accessUri);
        details.setClientId(client_id);
        details.setClientSecret(client_secret);
        details.setGrantType("client_credentials");

        return obtainAccessToken(details, request).getValue();
    }

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException, OAuth2AccessDeniedException {
        // Check if user is logged in
        if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof DefaultOAuth2User)) {
            if (OAuth2TokenActive && oauth2Token != null) {
                return new DefaultOAuth2AccessToken(oauth2Token);
            }
            else if (!OAuth2TokenActive && oauth2Token != null) { // if first time
                dropClientToken(clientToken);
                OAuth2TokenActive = true;
                return new DefaultOAuth2AccessToken(oauth2Token);
            }
        }
        else {
            // reset oauth2 token
            if (OAuth2TokenActive) {
                OAuth2TokenActive = false;
                dropOAuth2Token(oauth2Token);
                oauth2Token = null;
            }
        }

        if (clientToken != null) {
            return new DefaultOAuth2AccessToken(clientToken);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth(details.getClientId(), details.getClientSecret());
        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> httpEntity = new HttpEntity<>("grant_type=" + details.getGrantType(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(details.getAccessTokenUri(), httpEntity, String.class);

        logger.info("[Token] " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        try {
            map.putAll(objectMapper.readValue(response.getBody(), new TypeReference<Map<String,Object>>(){}));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        clientToken = String.valueOf(map.get("access_token"));
        return new DefaultOAuth2AccessToken(clientToken);
    }

    public void dropClientToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "https://www.reddit.com/api/v1/revoke_token";

        headers.setBasicAuth(client_id, client_secret);
        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("token=" + token, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        clientToken = null;
    }

    public void dropOAuth2Token(String token) {
        dropClientToken(token);
        OAuth2TokenActive = false;
        oauth2Token = null;
    }

    public static void setOauth2Token(String oauth2Token) {
        AccessTokenProvider.oauth2Token = oauth2Token;
    }
}
