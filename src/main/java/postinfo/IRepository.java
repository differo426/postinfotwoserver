package postinfo;

import postinfo.data.Post;

import java.util.List;

public interface IRepository {
    List<Post> getPosts();
    void addPosts(List<Post> posts);
    void deletePosts(List<Post> posts);
    void deleteOldPosts();
}
