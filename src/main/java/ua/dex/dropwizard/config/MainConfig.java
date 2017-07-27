package ua.dex.dropwizard.config;

import io.dropwizard.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dexter on 11.02.17.
 */
public class MainConfig extends Configuration {

    private String webHook;
    private String token;
    private Map<String, String> channels = new HashMap<>();


    public String getWebHook() {
        return webHook;
    }

    public void setWebHook(String webHook) {
        this.webHook = webHook;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, String> channels) {
        this.channels = channels;
    }
}
