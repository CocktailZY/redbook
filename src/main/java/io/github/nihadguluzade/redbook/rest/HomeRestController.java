package io.github.nihadguluzade.redbook.rest;

import io.github.nihadguluzade.redbook.security.AccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/api/home")
@PropertySource("classpath:reddit.properties")
public class HomeRestController {

    private AccessTokenProvider accessTokenProvider;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    @Value("${userAgent}") private String userAgent;

    @Autowired
    public HomeRestController(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @GetMapping("/sub/{subreddit}")
    public ResponseEntity<String> getPosts(@PathVariable String subreddit) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        String token = accessTokenProvider.obtainAccessToken();

        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setBearerAuth(token); // Reddit uses Bearer auth

        HttpEntity<String> request = new HttpEntity<String>("parameters", headers);

        String url = "https://oauth.reddit.com/";

        // Not home page
        if (subreddit != null && !subreddit.isEmpty()) {
            url = url + "/r/" + subreddit + "/";
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/vote/{postID}/{action}")
    public ResponseEntity<Object> vote(@PathVariable String postID, @PathVariable String action) {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        String token = accessTokenProvider.obtainAccessToken();
        String url = "https://oauth.reddit.com/api/vote/";

        headers.put("User-Agent", Collections.singletonList(userAgent));
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", postID);
        map.add("dir", action);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(map, headers), String.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
