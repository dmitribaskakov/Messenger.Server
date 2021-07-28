package org.home.nio;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageWorker implements Runnable {
    private final List<ServerDataEvent> queue = new LinkedList<>();
    private final Map<String, SocketChannel> authenticatedClient = new HashMap<>();

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
            //ковертируем сообщение
            Message message = Message.getFromString(new String(dataEvent.data));
            if (message.operation.equals(Message.Operation_Authentication)) {
                //аутентификация
                System.out.println("[Authentication " + message.from +"]");
                authenticatedClient.put(message.from, dataEvent.socket);
                dataEvent.server.send(dataEvent.socket, dataEvent.data);


            } else if (message.operation.equals(Message.Operation_Message)) {
                //прием сообщения
                System.out.print("[From:" + message.from +" To:");
                if (message.to.isEmpty()) {
                    System.out.print("All");
                    //отправляем сообщение всем
                    for(Map.Entry<String, SocketChannel> item : authenticatedClient.entrySet()) {
                        if ((! message.from.equals(item.getKey())) || (item.getValue() != null)) {
                            sendEvent(message, item.getKey(), item.getValue(), dataEvent.server);
                        }
                    }

                } else {
                    System.out.print(message.to);
                    //отправляем сообщение указанному адресату
                    SocketChannel authSocket = authenticatedClient.get(message.to);
                    if (authSocket!=null) {
                        sendEvent(message, message.to, authSocket, dataEvent.server);
                    }

                }
                System.out.println(" Msg:" + message.body +"]");
                //dataEvent.server.send(dataEvent.socket, dataEvent.data);

            } else {
                System.out.println("[Received = '" + new String(dataEvent.data) +"']");
                dataEvent.server.send(dataEvent.socket, dataEvent.data);

            }
        }
    }
    private void sendEvent (Message message, String authTo, SocketChannel authSocket, MessengerServerNio server) {
        Message msg = new Message();
        msg.operation = Message.Operation_Message;
        msg.from = message.from;
        msg.to = authTo;
        msg.body = message.body;
        String json = msg.ToString();
        server.send(authSocket, json.getBytes());
    }
}
