package eu.iamgio.mcitaliaapi.forum;

import eu.iamgio.mcitaliaapi.user.UnparsedUser;

import java.util.Date;
import java.util.List;

/**
 * Represents a post inside of a topic
 * @author Gio
 */
public class TopicPost {

    private long id;

    private String plainText, html;

    private UnparsedUser user;

    private String rawDate;

    // These values can be gained through User but they are loaded here too to prevent other connections
    private int userMessagesCount, userTopicsCount, userLikesReceivedCount, userLikedPostsCount, userLikesGivenCount;
    private List<String> userBadges;
    private String userRawRegistrationDate;
    private Date userRegistrationDate;
    private String userAvatarUrl;
    private boolean userOnline;

    private List<UnparsedUser> likeGivers;

    private String userSignatureHtml;

    TopicPost(long id, String plainText, String html, UnparsedUser user, String rawDate, int userMessagesCount, int userTopicsCount, int userLikesReceivedCount, int userLikedPostsCount, int userLikesGivenCount, List<String> userBadges, String userRawRegistrationDate, Date userRegistrationDate, String userAvatarUrl, boolean userOnline, List<UnparsedUser> likeGivers, String userSignatureHtml) {
        this.id = id;
        this.plainText = plainText;
        this.html = html;
        this.user = user;
        this.rawDate = rawDate;
        this.userMessagesCount = userMessagesCount;
        this.userTopicsCount = userTopicsCount;
        this.userLikesReceivedCount = userLikesReceivedCount;
        this.userLikedPostsCount = userLikedPostsCount;
        this.userLikesGivenCount = userLikesGivenCount;
        this.userBadges = userBadges;
        this.userRawRegistrationDate = userRawRegistrationDate;
        this.userRegistrationDate = userRegistrationDate;
        this.userAvatarUrl = userAvatarUrl;
        this.userOnline = userOnline;
        this.likeGivers = likeGivers;
        this.userSignatureHtml = userSignatureHtml;
    }

    /**
     * @return Post ID. <tt>-1</tt> if cannot be fetched
     */
    public long getId() {
        return id;
    }

    /**
     * @return Post content as plain text
     */
    public String getPlainText() {
        return plainText;
    }

    /**
     * @return Post content as HTML
     */
    public String getHtml() {
        return html;
    }

    /**
     * @return Author of the post
     */
    public UnparsedUser getUser() {
        return user;
    }

    /**
     * @return Raw date
     */
    public String getRawDate() {
        return rawDate;
    }

    /**
     * @return User's amount of messages sent
     */
    public int getuserMessagesCount() {
        return userMessagesCount;
    }

    /**
     * @return User's amount of topics created
     */
    public int getuserTopicsCount() {
        return userTopicsCount;
    }

    /**
     * @return User's amount of likes received
     */
    public int getuserLikesReceivedCount() {
        return userLikesReceivedCount;
    }

    /**
     * @return User's amount of posts that received likes
     */
    public int getuserLikedPostsCount() {
        return userLikedPostsCount;
    }

    /**
     * @return User's likes given
     */
    public int getuserLikesGivenCount() {
        return userLikesGivenCount;
    }

    /**
     * @return User's badges
     */
    public List<String> getuserBadges() {
        return userBadges;
    }

    /**
     * @return Raw user's registration date
     */
    public String getUserRawRegistrationDate() {
        return userRawRegistrationDate;
    }

    /**
     * @return User's registration date
     */
    public Date getUserRegistrationDate() {
        return userRegistrationDate;
    }

    /**
     * @return User's avatar URL
     */
    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    /**
     * @return <tt>true</tt> if user is online
     */
    public boolean isUserOnline() {
        return userOnline;
    }

    /**
     * @return People who liked the post
     */
    public List<UnparsedUser> getLikeGivers() {
        return likeGivers;
    }

    /**
     * @return User's signature content as HTML
     */
    public String getuserSignatureHtml() {
        return userSignatureHtml;
    }
}
