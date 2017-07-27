package ua.dex.dropwizard.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dexter on 11.02.17.
 */

public class EndpointHandler {

    private static final Logger log = Logger.getLogger(EndpointHandler.class);

    private static EndpointHandler instance;
    private static Map<String, Endpoint> endpointMap = new ConcurrentHashMap<>();
    private static Map<String, Endpoint> chanelEndpointMap = new ConcurrentHashMap<>();


    public static EndpointHandler getInstance(){
        return instance;
    }

    public void register(Endpoint endpoint){
        endpointMap.put(String.valueOf(endpoint.hashCode()), endpoint);
    }

    public void remove(Endpoint endpoint){
        endpointMap.remove(String.valueOf(endpoint.hashCode()));
    }

    public Endpoint get(String key){
        return endpointMap.get(key);
    }

    public static Map<String, Endpoint> getChanelEndpointMap() {
        return chanelEndpointMap;
    }

    public static void sendMessage(String key, String message){
        Optional<Endpoint> endpoint = Optional.ofNullable(chanelEndpointMap.get(key));
        endpoint.ifPresent(e -> {
            try {
                e.getSession().getRemote().sendString(message);
            } catch (IOException ex) {
                log.error("Some Error during send to from message ", ex);
            }
        });
    }

    public static class EndpointHandlerBuilder {
        public static void build(){
            instance = new EndpointHandler();
        }
    }
}
