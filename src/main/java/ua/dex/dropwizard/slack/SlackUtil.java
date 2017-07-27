package ua.dex.dropwizard.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.webhook.Payload;
import org.apache.log4j.Logger;
import ua.dex.dropwizard.chat.EndpointHandler;
import ua.dex.dropwizard.model.WebSocketMessage;
import ua.dex.dropwizard.util.JsonUtil;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dexter on 11.02.17.
 */
public class SlackUtil {

    private static final Logger log = Logger.getLogger(SlackUtil.class);

    private static String WEBHOOK;

    private static RTMClient client;

    public static Map<String, String> channels = new ConcurrentHashMap<>();

    public static void init(String token, String webHook) throws IOException, DeploymentException {
        WEBHOOK = webHook;

        client = new Slack().rtm(token);

        client.addMessageHandler(m -> {

            Optional<Message> message = Optional.ofNullable(JsonUtil.toObject(m, Message.class));
            message.filter(msg -> "message".equals(msg.getType())).ifPresent(msg -> {
                log.info("Message from Slack : " + msg);
                String channel = channels.get(msg.getChannel());
                EndpointHandler.sendMessage(channel, msg.getText());
            });


        });

        client.connect();
    }

    public static void sendMessage(WebSocketMessage webSocketMessage){
        Slack slack = Slack.getInstance();
        Payload payload = Payload.builder()
                .channel(webSocketMessage.channel)
                .username(webSocketMessage.user)
                .iconEmoji(":smile_cat:")
                .text(webSocketMessage.message)
                .build();

        try {
            slack.send(WEBHOOK, payload);
        } catch (IOException e) {
            log.error("Some Error during sending to Slack : ", e);
        }
    }

}
