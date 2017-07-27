package ua.dex.dropwizard.chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ua.dex.dropwizard.slack.SlackUtil;

import javax.websocket.DeploymentException;
import java.io.IOException;


/**
 * Created by dexter on 11.02.17.
 */
public class ChatServerServlet extends WebSocketServlet {

    public ChatServerServlet() throws IOException, DeploymentException {
        new SlackUtil();
        EndpointHandler.EndpointHandlerBuilder.build();
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(Endpoint.class);
    }
}
