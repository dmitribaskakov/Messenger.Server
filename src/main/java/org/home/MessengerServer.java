package org.home;

import org.home.nio.MessengerServerNio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessengerServer {

    //static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public static void main(String... args) throws Exception {
        final int PORT = 19000;
        final String ADDRESS = "localhost";

        System.out.println("MessengerServer: started" );
        //SimpleSocketServer.start();

        MessengerServerNio server = new MessengerServerNio();
        server.start(ADDRESS, PORT);

        System.out.println("MessengerServer: finished");
    }

}

