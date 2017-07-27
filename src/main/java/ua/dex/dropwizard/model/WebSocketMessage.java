package ua.dex.dropwizard.model;

/**
 * Created by dexter on 11.02.17.
 */
public class WebSocketMessage {

    public String channel;
    public String user;
    public String message;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WebSocketMessage{");
        sb.append("channel='").append(channel).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
