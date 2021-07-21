package org.home;

import org.home.env.Settings;
import org.home.env.SettingsManager;
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


//        Map<String, String> states = new HashMap<>();
//        states.put("1", "Germany");
//        states.put("2", "Spain");
//        states.put("4", "France");
//        states.put("3", "Italy");
//
//        for(Map.Entry<String, String> item : states.entrySet()) {
//            foo(item.getKey(), item.getValue());
//        }
//        System.out.println("MessengerServer: test done");
//
//        states.forEach((auth, socket) -> {
//                    //foo(k, v);
//                    String sk = "'"+auth+"'";
//                    System.out.printf("Key: %s  ", sk);
//                    System.out.printf("Value: %s \n", socket);
//                });
//
//        System.out.println("MessengerServer: test done");

        MessengerServerNio server = new MessengerServerNio();
        server.start(settings);

        System.out.println("MessengerServer: finished");
    }

    private static void foo(String key, String value) {
        System.out.printf("Key: %s  Value: %s \n", key, value);
    }

}

