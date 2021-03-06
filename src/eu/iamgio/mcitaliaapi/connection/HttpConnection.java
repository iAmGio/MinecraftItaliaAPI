package eu.iamgio.mcitaliaapi.connection;

import eu.iamgio.mcitaliaapi.exception.MinecraftItaliaException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Connection to any common website
 * @author Gio
 */
public class HttpConnection {

    private String url;
    private Connection connection;

    /**
     * @param url URL of the web page
     */
    public HttpConnection(String url) {
        this.url = url;
    }

    public String read() throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        StringBuilder cookieString = new StringBuilder();
        for(String cookie : Cookies.cookies.keySet()) {
            cookieString.append(cookie).append("=").append(Cookies.cookies.get(cookie)).append("; ");
        }
        connection.setRequestProperty("Cookie", cookieString.toString());
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();
        return response.toString();
    }

    /**
     * Connects to the URL
     * @return This for concatenating
     */
    public HttpConnection connect() {
        this.connection = Jsoup.connect(url)
                .userAgent("Mozilla")
                .referrer("http://www.google.com")
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .followRedirects(true)
                .cookies(Cookies.cookies);
        return this;
    }

    /**
     * GET request
     * @return Parsed {@link Document}
     * @throws MinecraftItaliaException if an error occurred during the request
     */
    public Document get() throws MinecraftItaliaException {
        try {
            return this.connection.get();
        } catch(IOException e) {
            throw new MinecraftItaliaException(e.getMessage());
        }
    }

    /**
     * POST request
     * @return Parsed {@link Document}
     * @throws MinecraftItaliaException if an error occurred during the request
     */
    public Document post() throws MinecraftItaliaException {
        try {
            return this.connection.post();
        } catch(IOException e) {
            throw new MinecraftItaliaException(e.getMessage());
        }
    }

    /**
     * @return Connection response
     */
    public Connection.Response getResponse() {
        return this.connection.response();
    }

    /**
     * Adds parameter to request
     * @param k Parameter name
     * @param v Parameter value
     * @return This for concatenating
     */
    public HttpConnection data(String k, String v) {
        this.connection = connection.data(k, v);
        return this;
    }

    /**
     * Adds file parameter to request
     * @param k Parameter name
     * @param name File name
     * @param inputStream Input stream
     * @return This for concatenating
     */
    public HttpConnection data(String k, String name, InputStream inputStream) {
        this.connection = connection.data(k, name, inputStream);
        return this;
    }

    public HttpConnection cookies(Map<String, String> cookies) {
        this.connection = connection.cookies(cookies);
        return this;
    }

    /**
     * Adds header to request
     * @param k Key
     * @param v Value
     * @return This for concatenating
     */
    public HttpConnection header(String k, String v) {
        this.connection = connection.header(k, v);
        return this;
    }

    /**
     * Adds body to request
     * @param body Body string
     * @return This for concatenating
     */
    public HttpConnection requestBody(String body) {
        this.connection = connection.requestBody(body);
        return this;
    }
}
