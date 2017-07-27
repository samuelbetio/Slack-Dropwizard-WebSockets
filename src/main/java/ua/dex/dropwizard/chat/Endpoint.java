package ua.dex.dropwizard.chat;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import ua.dex.dropwizard.model.WebSocketMessage;
import ua.dex.dropwizard.slack.SlackUtil;
import ua.dex.dropwizard.util.JsonUtil;

import java.util.Optional;

/**
 * Created by dexter on 11.02.17.
 */

@WebSocket
public class Endpoint {

    private static final Logger log = Logger.getLogger(Endpoint.class);

    private Session session;

    public Session getSession(){
        return session;
    }

    @OnWebSocketConnect
    public void onOpen(Session session){
        log.info("New Session was added : " + session.getLocalAddress().getHostName());
        this.session = session;
        EndpointHandler endpointHandler = EndpointHandler.getInstance();
        endpointHandler.register(this);
    }

    @OnWebSocketMessage
    public void opMessage(Session session, String message){

        Optional<WebSocketMessage> webSocketMessage = Optional.ofNullable(JsonUtil.toObject(message, WebSocketMessage.class));
        webSocketMessage.ifPresent(m -> {
            log.info("Message was received : " + m);
            EndpointHandler.getChanelEndpointMap().put(m.channel, this);
            SlackUtil.sendMessage(m);
        });

    }

    @OnWebSocketClose
    public void onClose(int code, String message){
        log.info("WebSocket was closed. code : " + code + ". message : " + message);
        EndpointHandler endpointHandler = EndpointHandler.getInstance();
        endpointHandler.remove(this);
    }

    @OnWebSocketError
    public void onError(Throwable throwable){

        log.error("Some Error", throwable);

        EndpointHandler endpointHandler = EndpointHandler.getInstance();
        endpointHandler.remove(this);
    }

}
