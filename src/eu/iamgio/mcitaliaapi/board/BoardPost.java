package eu.iamgio.mcitaliaapi.board;

import eu.iamgio.mcitaliaapi.user.UnparsedUser;

import java.util.Date;
import java.util.List;

/**
 * Represents a post on the board
 * @author Gio
 */
public class BoardPost {

    private long id;
    private Long sharedId;
    private UnparsedUser user, target;
    private String content, mediaUrl;
    private Date date;
    private long[] likeGivers, sharers;
    private List<BoardPostComment> comments;

    public BoardPost(long id, Long sharedId, UnparsedUser user, UnparsedUser target, String content, String mediaUrl, Date date, long[] likeGivers, long[] sharers, List<BoardPostComment> comments) {
        this.id = id;
        this.sharedId = sharedId;
        this.user = user;
        this.target = target;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.date = date;
        this.likeGivers = likeGivers;
        this.sharers = sharers;
        this.comments = comments;
    }

    /**
     * @return Post ID
     */
    public long getId() {
        return id;
    }

    /**
     * @return ID of the shared post (may be null)
     */
    public Long getSharedId() {
        return sharedId;
    }

    /**
     * @return Author of the post
     */
    public UnparsedUser getUser() {
        return user;
    }

    /**
     * @return Target of the post (may be null)
     */
    public UnparsedUser getTarget() {
        return target;
    }

    /**
     * @return Text of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * @return URL of the media of the post (may be null)
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * @return Date of the post
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return UIDs of people who liked the post
     */
    public long[] getLikeGivers() {
        return likeGivers;
    }

    /**
     * @return UIDs of people who shared the post
     */
    public long[] getSharers() {
        return sharers;
    }

    /**
     * @return Post comments
     */
    public List<BoardPostComment> getComments() {
        return comments;
    }
}
