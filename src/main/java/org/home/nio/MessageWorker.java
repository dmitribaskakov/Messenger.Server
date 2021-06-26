package org.home.nio;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class MessageWorker implements Runnable {
    private final List<ServerDataEvent> queue = new LinkedList<>();

    void processData(MessengerServerNio server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized (queue) {
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    public void run() {
        ServerDataEvent dataEvent;
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dataEvent = queue.remove(0);
            }
            System.out.println("Recieved = " + new String(dataEvent.data));
            dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }
}
