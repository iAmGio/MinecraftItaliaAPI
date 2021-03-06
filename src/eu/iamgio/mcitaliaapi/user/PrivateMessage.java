package eu.iamgio.mcitaliaapi.user;

import eu.iamgio.mcitaliaapi.connection.HttpConnection;
import eu.iamgio.mcitaliaapi.exception.MinecraftItaliaException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Represents a private text
 * @author Gio
 */
public class PrivateMessage {

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

    private String userSignatureHtml;

    private PrivateMessage(long id, String plainText, String html, UnparsedUser user, String rawDate, int userMessagesCount, int userTopicsCount, int userLikesReceivedCount, int userLikedPostsCount, int userLikesGivenCount, List<String> userBadges, String userRawRegistrationDate, Date userRegistrationDate, String userAvatarUrl, boolean userOnline, String userSignatureHtml) {
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
        this.userSignatureHtml = userSignatureHtml;
    }

    static PrivateMessage fromId(long id) {
        try {
            Document document = new HttpConnection("https://www.minecraft-italia.it/forum/private.php?action=read&pmid=" + id).connect().get();
            Element authorElement = document.getElementsByClass("post_author").first();
            Element bodyElement = document.getElementsByClass("post_body").first();
            Element signatureElement = document.getElementsByClass("signature").first();
            UnparsedUser user = new UnparsedUser(authorElement.getElementsByClass("username-inner").first().text());
            Element statisticsElement = authorElement.getElementsByClass("author_statistics").first();
            String[] statisticsParts = statisticsElement.ownText().split(" ");
            String rawDate = document.getElementsByClass("post_date").first().ownText();
            int userMessagesCount = Integer.parseInt(statisticsParts[1].replace(",", ""));
            String rawRegistrationDate = statisticsParts[statisticsParts.length - 2] + " " + statisticsParts[statisticsParts.length - 1];
            Date registrationDate = null;
            try {
                registrationDate = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH).parse(rawRegistrationDate);
            } catch(ParseException e) {
                e.printStackTrace();
            }
            int userTopicsCount = Integer.parseInt(statisticsElement.getElementsByClass("postbit_userthreads").first().ownText().replace(",", "").split(" ")[1]);
            String[] likesReceivedParts = statisticsElement.getElementsByClass("postbit_tylreceived").first().ownText().replace(",", "").split(" ");
            int userLikesReceivedCount = Integer.parseInt(likesReceivedParts[2]);
            int userLikedPostsCount = Integer.parseInt(likesReceivedParts[4]);
            int userLikesGivenCount = Integer.parseInt(statisticsElement.getElementsByClass("postbit_tylgiven").first().ownText().replace(",", "").split(" ")[2]);
            List<String> userBadges = new ArrayList<>();
            for(Element badge : document.getElementsByClass("my-badge-inner")) {
                userBadges.add(badge.text());
            }
            String userAvatarUrl = authorElement.getElementsByClass("author_avatar").first().getElementsByTag("img").first().attr("src");
            boolean userOnline = authorElement.getElementsByClass("online-status").first().attr("title").equals("Online");
            List<UnparsedUser> likeGivers = new ArrayList<>();
            String plainText = bodyElement.text();
            String html = bodyElement.html();
            String userSignatureHtml = signatureElement == null ? "" : signatureElement.html();
            return new PrivateMessage(id, plainText, html, user, rawDate, userMessagesCount, userTopicsCount, userLikesReceivedCount, userLikedPostsCount, userLikesGivenCount, userBadges, rawRegistrationDate, registrationDate, userAvatarUrl, userOnline, userSignatureHtml);
        } catch(NullPointerException e) {
            return null;
        }
    }

    /**
     * @return Private text ID (pmid)
     */
    public long getId() {
        return id;
    }

    /**
     * @return Message content as plain text
     */
    public String getPlainText() {
        return plainText;
    }

    /**
     * @return Message content as HTML
     */
    public String getHtml() {
        return html;
    }

    /**
     * @return Author of the text
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
    public int getUserMessagesCount() {
        return userMessagesCount;
    }

    /**
     * @return User's amount of topics created
     */
    public int getUserTopicsCount() {
        return userTopicsCount;
    }

    /**
     * @return User's amount of likes received
     */
    public int getUserLikesReceivedCount() {
        return userLikesReceivedCount;
    }

    /**
     * @return User's amount of posts that received likes
     */
    public int getUserLikedPostsCount() {
        return userLikedPostsCount;
    }

    /**
     * @return User's likes given
     */
    public int getUserLikesGivenCount() {
        return userLikesGivenCount;
    }

    /**
     * @return User's badges
     */
    public List<String> getUserBadges() {
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
     * @return User's signature content as HTML
     */
    public String getUserSignatureHtml() {
        return userSignatureHtml;
    }

    /**
     * Class used to build new private messages
     */
    public static class New {

        private String targetsString = "", text = "", subject = "";
        private boolean disableSmilies, readReceipt = true, saveCopy = true, signature = true;
        private long quoteId = 0;

        String getTargetsString() {
            return targetsString;
        }

        /**
         * Adds a target user
         * @param target Target user name
         * @return This for concatenating
         */
        public New addTarget(String target) {
            this.targetsString = (targetsString.isEmpty() ? "" : ",") + target;
            return this;
        }

        String getText() {
            return text;
        }

        /**
         * Sets text
         * @param text Message text
         * @return This for concatenating
         */
        public New withMessage(String text) {
            this.text = text;
            return this;
        }

        String getSubject() {
            return subject;
        }

        /**
         * Sets subject
         * @param subject Message subject
         * @return This for concatenating
         */
        public New withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        boolean isDisableSmilies() {
            return disableSmilies;
        }

        /**
         * Disable smilies on/off
         * @param disableSmilies <tt>true</tt> to disable smilies
         * @return This for concatenating
         */
        public New disableSmilies(boolean disableSmilies) {
            this.disableSmilies = disableSmilies;
            return this;
        }

        boolean isReadReceipt() {
            return readReceipt;
        }

        /**
         * Read receipt on/off
         * @param readReceipt <tt>true</tt> to enable read receipt
         * @return This for concatenating
         */
        public New readReceipt(boolean readReceipt) {
            this.readReceipt = readReceipt;
            return this;
        }

        boolean isSaveCopy() {
            return saveCopy;
        }

        /**
         * Save copy on/off
         * @param saveCopy <tt>true</tt> to save copy
         * @return This for concatenating
         */
        public New saveCopy(boolean saveCopy) {
            this.saveCopy = saveCopy;
            return this;
        }

        boolean isSignature() {
            return signature;
        }

        /**
         * Signature on/off
         * @param signature <tt>true</tt> to enable signature
         * @return This for concatenating
         */
        public New signature(boolean signature) {
            this.signature = signature;
            return this;
        }

        public long getQuoteId() {
            return quoteId;
        }

        /**
         * Quote another message
         * @param messageId Message ID
         * @return This for concatenating
         */
        public New quote(long messageId) {
            this.quoteId = messageId;
            return this;
        }
    }

    public static class PrivateMessageException extends MinecraftItaliaException {

        private List<String> errors;

        PrivateMessageException(List<String> errors) {
            super("Could not send private message: \n\t•  " + String.join("\n\t•  ", errors));
            this.errors = errors;
        }

        /**
         * @return List of errors
         */
        public List<String> getErrors() {
            return errors;
        }
    }
}
