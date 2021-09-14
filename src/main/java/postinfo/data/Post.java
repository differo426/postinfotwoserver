package postinfo.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class Post {
    int type;
    @JsonProperty("URL")
    String url;
    String text;
    int likes;
    int comments;
    int shares;
    String page;
    String date;
    String profileImageURL;
    String thumbnailImageURL;


    public int getType() {
        return type;
    }

    public Post setType(int type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Post setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getText() {
        return text;
    }

    public Post setText(String text) {
        this.text = text;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public Post setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public int getComments() {
        return comments;
    }

    public Post setComments(int comments) {
        this.comments = comments;
        return this;
    }

    public int getShares() {
        return shares;
    }

    public Post setShares(int shares) {
        this.shares = shares;
        return this;
    }


    public String getPage() {
        return page;
    }

    public Post setPage(String page) {
        this.page = page;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Post setDate(String date) {
        this.date = date;
        return this;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    @JsonProperty("profile_img_url")
    public Post setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
        return this;
    }

    public String getThumbnailImageURL() {
        return thumbnailImageURL;
    }

    @JsonProperty("thumbnail_img_url")
    public Post setThumbnailImageURL(String thumbnailImageURL) {
        this.thumbnailImageURL = thumbnailImageURL;
        return this;
    }

    public static Post createAuthPost(){
        return new Post()
                .setType(0)
                .setDate("1999")
                .setUrl("postinfo")
                .setLikes(3)
                .setComments(2)
                .setShares(3)
                .setPage("page")
                .setProfileImageURL("img")
                .setThumbnailImageURL("thumb")
                .setText("empty");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return type == post.type &&
               likes == post.likes &&
               comments == post.comments &&
               shares == post.shares &&
               Objects.equals(url, post.url) &&
               Objects.equals(text, post.text) &&
               Objects.equals(page, post.page) &&
               Objects.equals(date, post.date) &&
               Objects.equals(profileImageURL, post.profileImageURL) &&
               Objects.equals(thumbnailImageURL, post.thumbnailImageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, url, text, likes, comments, shares, page, date, profileImageURL, thumbnailImageURL);
    }
}
