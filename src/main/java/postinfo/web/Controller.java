package postinfo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import postinfo.data.Post;
import postinfo.impl.JdbcRepository;

import java.util.List;

@RestController
public class Controller {

    private final JdbcRepository jdbcRepository;

    @Autowired
    public Controller(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return jdbcRepository.getPosts();
    }

    @PostMapping(value = "/post", consumes = "application/json;charset=UTF-8")
    public void addPosts(@RequestBody List<Post> posts) {
        if (posts.get(0).equals(Post.createAuthPost())) {
            posts.remove(0);
            jdbcRepository.addPosts(posts);
        }
        deleteOldPosts();
    }

    @PostMapping(value = "/delete", consumes = "application/json;charset=UTF-8")
    public void deletePosts(@RequestBody List<Post> posts) {
        if (posts.get(0).equals(Post.createAuthPost())) {
            posts.remove(0);
            jdbcRepository.deletePosts(posts);
        }
    }

    @GetMapping("deleteOldPosts")
    public void deleteOldPosts() {
        jdbcRepository.deleteOldPosts();
    }

    @GetMapping(value = "postsUrls", produces = "application/json")
    public List<String> getPostsUrls() {
        return jdbcRepository.getPostsURL();
    }
}
