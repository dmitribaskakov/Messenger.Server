package org.home;

public class MessengerServer {

    public static void main(String... args) throws Exception {
        System.out.println("MessengerServer: started" );

        SimpleSocketServer.test();


        System.out.println("MessengerServer: finished");
    }

}

