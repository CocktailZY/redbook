package io.github.nihadguluzade.redbook.rest;

import io.github.nihadguluzade.redbook.security.AccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditRestController {

    private AccessTokenProvider accessTokenProvider;
    private String userAgent = "your_user_agent";

    @Autowired
    public SubredditRestController(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @GetMapping("/reddits")
    public ResponseEntity<String> reddits() {
        String url = "https://oauth.reddit.com/reddits/";
        return getStringResponseEntity(url);
    }

    @GetMapping("/{subreddit}")
    public ResponseEntity<String> getSubreddit(@PathVariable String subreddit) {
        String url = "https://oauth.reddit.com/r/" + subreddit + "/about.json";
        return getStringResponseEntity(url);
    }

    public ResponseEntity<String> getStringResponseEntity(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String token = accessTokenProvider.obtainAccessToken();

        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<String>("parameters", headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
