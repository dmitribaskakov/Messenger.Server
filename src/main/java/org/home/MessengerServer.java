package org.home;

import org.home.env.Settings;
import org.home.env.SettingsManager;
import org.home.jdbc.JdbcExample;
import org.home.nio.MessengerServerNio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MessengerServer {

    //static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public static void main(String... args) throws Exception {
        SettingsManager settingsManager = new SettingsManager();
        Settings settings = settingsManager.load();

        System.out.println("MessengerServer: started" );
        //SimpleSocketServer.start();


        //JdbcExample j = new JdbcExample();
        JdbcExample.test();


        MessengerServerNio server = new MessengerServerNio();
        server.start(settings);

        System.out.println("MessengerServer: finished");
    }

    private static void foo(String key, String value) {
        System.out.printf("Key: %s  Value: %s \n", key, value);
    }

}

