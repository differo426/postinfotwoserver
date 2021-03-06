package postinfo.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import postinfo.IRepository;
import postinfo.data.Post;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class JdbcRepository implements IRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Post> getPosts() {

        String sql = "SELECT * FROM posts WHERE show_after < ? ORDER BY time_uploaded DESC";
        return jdbcTemplate.query(sql, this::mapPostRow, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void addPosts(List<Post> posts) {
        String sql = "INSERT INTO posts (page, type, text, url, likes, comments, shares, date, time_uploaded, time_updated, show_after) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "page = VALUES(page), " +
                     "type = VALUES(type), " +
                     "text = VALUES(text), " +
                     "url = VALUES(url), " +
                     "likes = VALUES(likes), " +
                     "comments = VALUES(comments), " +
                     "shares = VALUES(shares), " +
                     "date = VALUES(date), " +
                     "time_updated = VALUES(time_updated), " +
                     "show_after = VALUES(show_after) ";

        for (Post post : posts) {
            jdbcTemplate.update(sql, post.getPage(), post.getType(), new String(post.getText().getBytes(), StandardCharsets.UTF_8), post.getUrl(), post.getLikes(), post.getComments(), post.getShares(), post.getDate(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), post.getShowAfter());
        }
    }

    @Override
    public void deletePosts(List<Post> posts) {
        String sql = "DELETE FROM posts WHERE url = ?";

        for (Post post : posts) {
            jdbcTemplate.update(sql, post.getUrl());
        }
    }

    @Override
    public void deleteOldPosts() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String lastSupportedDate = (year - 1) + "-01-01";

        String sql = "DELETE FROM posts where date < ?";

        jdbcTemplate.update(sql, lastSupportedDate);
    }

    @Override
    public List<String> getPostsURL() {
        String sql = "SELECT url FROM posts";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    private Post mapPostRow(ResultSet rs, int rowNum) throws SQLException {
        return new Post().setType(rs.getInt("type"))
                .setPage(rs.getString("page"))
                .setText(rs.getString("text"))
                .setUrl(rs.getString("URL"))
                .setLikes(convert(rs.getString("likes")))
                .setComments(convert(rs.getString("comments")))
                .setShares(convert(rs.getString("shares")))
                .setDate(rs.getString("date"));

    }

    private int convert(String number) {
        Pattern pattern = Pattern.compile("[0-9]+(.[0-9])?");
        if (number.contains("K")) {
            Matcher matcher = pattern.matcher(number);
            if (matcher.find()) {
                return (int) (Double.parseDouble(matcher.group(0)) * 1000);
            } else {
                return 0;
            }
        } else if (number.contains("M")) {
            Matcher matcher = pattern.matcher(number);
            if (matcher.find()) {
                return (int) (Double.parseDouble(matcher.group(0)) * 1000000);
            } else {
                return 0;
            }
        } else {
            return Integer.parseInt(number);
        }

    }
}
