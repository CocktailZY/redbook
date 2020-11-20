package io.github.nihadguluzade.redbook.rest;

import io.github.nihadguluzade.redbook.security.AccessTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditRestController {

    private static final Logger logger = LoggerFactory.getLogger(SubredditRestController.class);

    private AccessTokenProvider accessTokenProvider;
    @Value("${userAgent}") private String userAgent;

    @Autowired
    public SubredditRestController(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @GetMapping("/reddits")
    @Async
    public CompletableFuture<ResponseEntity<String>> reddits() {
        logger.info("reddits()");
        String url = "https://oauth.reddit.com/reddits/";
        return getStringResponseEntity(url);
    }

    @GetMapping("/{subreddit}")
    @Async
    public CompletableFuture<ResponseEntity<String>> getSubreddit(@PathVariable String subreddit) {
        String url = "https://oauth.reddit.com/r/" + subreddit + "/about.json";
        return getStringResponseEntity(url);
    }

    public CompletableFuture<ResponseEntity<String>> getStringResponseEntity(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String token = accessTokenProvider.obtainAccessToken();

        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<String>("parameters", headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
