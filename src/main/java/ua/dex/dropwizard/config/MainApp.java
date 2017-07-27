package ua.dex.dropwizard.config;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import ua.dex.dropwizard.chat.ChatServerServlet;
import ua.dex.dropwizard.slack.SlackUtil;

import javax.servlet.ServletRegistration;

/**
 * Created by dexter on 11.02.17.
 */
public class MainApp extends Application<MainConfig> {


    public static void main(String[] args) throws Exception {
        new MainApp().run(args);
    }

    @Override
    public void run(MainConfig mainConfig, Environment environment) throws Exception {

        SlackUtil.init(mainConfig.getToken(), mainConfig.getWebHook());
        SlackUtil.channels.putAll(mainConfig.getChannels());

        ServletRegistration.Dynamic websocket = environment.servlets().addServlet("ws", new ChatServerServlet());
        websocket.setAsyncSupported(true);
        websocket.addMapping("/ws/*");
    }
}
