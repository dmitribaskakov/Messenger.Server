package org.home;

import org.home.env.Settings;
import org.home.env.SettingsManager;
import org.home.nio.MessengerServerNio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessengerServer {

    //static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public static void main(String... args) throws Exception {
        SettingsManager settingsManager = new SettingsManager();
        Settings settings = settingsManager.load();

        System.out.println("MessengerServer: started" );
        //SimpleSocketServer.start();

        MessengerServerNio server = new MessengerServerNio();
        server.start(settings);

        System.out.println("MessengerServer: finished");
    }

}

