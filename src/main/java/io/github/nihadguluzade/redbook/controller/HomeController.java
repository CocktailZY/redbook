package io.github.nihadguluzade.redbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nihadguluzade.redbook.dao.PageRepository;
import io.github.nihadguluzade.redbook.dao.SubmissionRepository;
import io.github.nihadguluzade.redbook.entity.PageEntity;
import io.github.nihadguluzade.redbook.entity.SubmissionEntity;
import io.github.nihadguluzade.redbook.entity.UsersEntity;
import io.github.nihadguluzade.redbook.model.Post;
import io.github.nihadguluzade.redbook.model.Subreddit;
import io.github.nihadguluzade.redbook.rest.HomeRestController;
import io.github.nihadguluzade.redbook.rest.SubredditRestController;
import io.github.nihadguluzade.redbook.security.AccessTokenProvider;
import io.github.nihadguluzade.redbook.service.UsersService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeRestController homeRestController;
    @Autowired
    private SubredditRestController subredditRestController;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @GetMapping("/")
    public String home(Model model) {
        buildHome("", model);
        return "index";
    }

    @GetMapping("/sub/{subreddit}")
    public String subreddit(@PathVariable String subreddit, Model model) {
        buildHome(subreddit, model);
        return "index";
    }

    @PostMapping("/vote")
    public ResponseEntity<Object> vote(@RequestParam(value = "postID") String postID,
                                       @RequestParam(value = "action") String action) {
        return homeRestController.vote(postID, action);
    }

    public void buildHome(String subreddit, Model model) {
        long startTime = System.currentTimeMillis();
        UsersEntity user = usersService.getUser();
        model.addAttribute("user", user);
        if (user != null && user.getRedditUserId() == null) {
            model.addAttribute("submissions", listSubmissions());
            model.addAttribute("redditPosts", false);
            model.addAttribute("pages", listPages());
        }
        else {
            try {
                obtainToken(); // don't get this async
                CompletableFuture<ResponseEntity<String>> posts = homeRestController.getPosts(subreddit);
                CompletableFuture<ResponseEntity<String>> reddits = subredditRestController.reddits();
                CompletableFuture.allOf(posts, reddits).join();
                model.addAttribute("posts", parseT3Collection(posts.get()));
                model.addAttribute("redditPosts", true);
                model.addAttribute("reddits", parseT5Collection(reddits.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("metaTitle", "Redbook");
        logger.info("buildHome() Elapsed time: " + (System.currentTimeMillis() - startTime));
    }

    public void obtainToken() {
        accessTokenProvider.obtainAccessToken();
    }

    private List<SubmissionEntity> listSubmissions() {
        return submissionRepository.findAllByOrderByPostedDesc();
    }

    private List<PageEntity> listPages() {
        return pageRepository.findAll();
    }

    /**
     * Reads subreddits Json from response through JsonNode.
     * @param response Response from reddit in Json format
     * @return List of all subreddits in List object
     */
    private List<Subreddit> parseT5Collection(ResponseEntity<String> response) {
        JsonNode rootNode = getRootNode(response);
        if (rootNode == null) return null;

        List<Subreddit> subreddits = new ArrayList<>();

        for (int i = 0; i < rootNode.path("data").path("dist").asInt(); i++) {
            JsonNode mainPath = rootNode.path("data").path("children").get(i).path("data");
            buildSubredditModel(mainPath);
            subreddits.add(buildSubredditModel(mainPath));
        }

        return subreddits;
    }

    private Subreddit buildSubredditModel(JsonNode mainPath) {
        Subreddit subreddit = new Subreddit();

        subreddit.setDisplayName(mainPath.path("display_name").asText());
        if (mainPath.path("icon_img").asText().equals("") || mainPath.path("icon_img").isNull()) {
            subreddit.setIconImg("/images/pages_icon.png");
        } else {
            subreddit.setIconImg(mainPath.path("icon_img").asText());
        }

        return subreddit;
    }

    /**
     * Reads posts Json from response through JsonNode.
     * @param response Response from reddit in Json format
     * @return List of all posts in List object
     */
    private List<Post> parseT3Collection(ResponseEntity<String> response) {
        JsonNode rootNode = getRootNode(response);
        if (rootNode == null) return null;

        // for transferring rootNode into List
        List<Post> posts = new ArrayList<>();

        IntStream.range(0, rootNode.path("data").path("dist").asInt()).parallel().forEach(i -> {
            try {
                JsonNode mainPath = rootNode.path("data").path("children").get(i).path("data");
                Post post = new Post();

                post.setAuthor(mainPath.path("author").asText());
                post.setSubreddit(parseT5(subredditRestController.getSubreddit(mainPath.path("subreddit").asText()).get()));

                if (mainPath.path("link_flair_richtext").get(0) != null) {
                    post.setFlairRichText(mainPath.path("link_flair_richtext").get(0).path("t").asText());
                }

                post.setTitle(mainPath.path("title").asText().replace("&amp;", "&"));

                String selfText = mainPath.path("selftext").asText();
                selfText = selfText.replace("&amp;nbsp;", "");
                String htmlText = parseMarkdown(selfText); // Convert markdown to html
                post.setSelftext(htmlText);

                if (!mainPath.path("preview").isEmpty()) {
                    post.setPreview(mainPath.path("preview").path("images").get(0).path("source").path("url").asText().replace("amp;s", "s"));
                }

                post.setDestination(mainPath.path("url_overridden_by_dest").asText());
                if (!post.getDestination().isEmpty() && ( getExtension(post.getDestination()).equals(".jpg") || getExtension(post.getDestination()).equals(".png") )) {
                    post.setImage(true);
                }

                post.setScore(mainPath.path("score").asText());
                post.setDistinguished(mainPath.path("distinguished").asText());
                post.setSelf(mainPath.path("is_self").asBoolean());
                post.setUpvoteRatio((int) (mainPath.path("upvote_ratio").asDouble() * 100));
                post.setName(mainPath.path("name").asText());
                post.setPermalink(mainPath.path("permalink").asText());

                String thumbnail = mainPath.path("thumbnail").asText();
                if (thumbnail.equals("default")) {
                    if (mainPath.path("preview").isEmpty() || mainPath.path("preview").isNull()) {
                        post.setThumbnail(null);
                    } else {
                        post.setThumbnail(post.getPreview());
                    }
                }
                else if (thumbnail.equals("nsfw")) {
                    post.setThumbnail("/images/nsfw.jpg");
                }
                else {
                    post.setThumbnail(thumbnail);
                }

                post.setCreatedUtc(mainPath.path("created_utc").asLong());

                posts.add(post);

            } catch (Exception e) {
                logger.error("Unexpected exception: " + e);
                e.printStackTrace();
            }     
        });

        return posts;
    }

    private Subreddit parseT5(ResponseEntity<String> response) {
        JsonNode rootNode = getRootNode(response);
        if (rootNode == null) return null;

        JsonNode mainPath = rootNode.path("data");
        return buildSubredditModel(mainPath);
    }

    private JsonNode getRootNode(ResponseEntity<String> response) {
        if (response == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getExtension(String url) {
        return url.substring(url.length() - 4, url.length());
    }

    /**
     * Parse markdown to html elements.
     * @param text Selftext of Reddit submission
     * @return String
     */
    private String parseMarkdown(String text) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

}
