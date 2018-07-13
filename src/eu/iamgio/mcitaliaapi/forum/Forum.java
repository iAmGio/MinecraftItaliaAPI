package eu.iamgio.mcitaliaapi.forum;

import eu.iamgio.mcitaliaapi.connection.HttpConnection;
import eu.iamgio.mcitaliaapi.user.UnparsedUser;
import eu.iamgio.mcitaliaapi.util.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the forum
 * @author Gio
 */
public class Forum {

    private Document document;

    private static Forum instance;

    private Forum() {
        instance = this;
        this.update();
    }

    /**
     * @return Forum instance
     */
    public static Forum getForum() {
        return instance == null ? new Forum() : instance;
    }

    /**
     * Updates connection
     */
    public void update() {
        this.document = new HttpConnection("https://www.minecraft-italia.it/forum/").connect().get();
    }

    /**
     * @return Section containers
     */
    public List<ForumSectionContainer> getSectionContainers() {
        List<ForumSectionContainer> containers = new ArrayList<>();
        Elements containersElements = document.getElementsByClass("forum-section-title");
        for(int i = 0; i < containers.size() - 1; i++) {
            Element link = containersElements.get(i).getElementsByTag("a").first();
            String name = link.ownText();
            String url = link.attr("href");
            Element div = document.getElementsByClass("forumbit-sections").get(i);
            containers.add(new ForumSectionContainer(name, url, div));
        }
        return containers;
    }

    /**
     * @return Total registered users
     */
    public int getTotalUsersCount() {
        return Integer.parseInt(document.getElementsByClass("row_numbers").first().child(0).ownText().replace(".", ""));
    }

    /**
     * @return Total topics opened
     */
    public int getTotalTopicsCount() {
        return Integer.parseInt(document.getElementsByClass("row_numbers").first().child(1).ownText().replace(".", ""));
    }

    /**
     * @return Total messages sent
     */
    public int getTotalMessagesCount() {
        return Integer.parseInt(document.getElementsByClass("row_numbers").first().child(2).ownText().replace(".", ""));
    }

    /**
     * @return First 50 online users
     */
    public List<UnparsedUser> getOnlineUsers() {
        List<UnparsedUser> users = new ArrayList<>();
        Element div = document.getElementById("boardstats_e").getElementsByClass("trow1").first();
        for(Element userElement : div.getElementsByClass("username-inner")) {
            users.add(new UnparsedUser(userElement.ownText()));
        }
        return users;
    }

    /**
     * @return Online users count
     */
    public int getOnlineUsersCount() {
        return Integer.parseInt(document.getElementById("boardstats_e").getElementsByClass("trow1").first().ownText().split(" ")[0]);
    }

    /**
     * @return Today's online users count
     */
    public int getTodaysOnlineUsersCount() {
        return Integer.parseInt(document.getElementById("boardstats_e").getElementsByClass("trow1").get(1).ownText().split(" ")[0]);
    }

    /**
     * @return Today's birthdays as [user, age]
     */
    public List<Pair<UnparsedUser, Integer>> getTodaysBirthdays() {
        List<Pair<UnparsedUser, Integer>> birthdays = new ArrayList<>();
        String raw = document.getElementById("boardstats_e").getElementsByClass("trow1").get(2).text();
        for(String part : raw.split(", ")) {
            String[] subparts = part.split(" ");
            birthdays.add(new Pair<>(new UnparsedUser(subparts[0]), Integer.parseInt(subparts[1].substring(1, subparts[1].length() - 1))));
        }
        return birthdays;
    }

    /**
     * @return Newest registered user
     */
    public UnparsedUser getNewestUser() {
        return new UnparsedUser(document.getElementById("boardstats_e").getElementsByClass("trow1").get(3).getElementsByTag("a").first().ownText());
    }
}