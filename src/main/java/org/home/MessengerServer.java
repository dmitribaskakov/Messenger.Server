package org.home;

import org.home.nio.MessengerServerNio;

public class MessengerServer {

    public static void main(String... args) throws Exception {
        final int PORT = 19000;
        final String ADDRESS = "localhost";

        System.out.println("MessengerServer: started" );
        //SimpleSocketServer.start();

        MessengerServerNio Server = new MessengerServerNio();
        Server.start(ADDRESS, PORT);

        System.out.println("MessengerServer: finished");
    }

}

