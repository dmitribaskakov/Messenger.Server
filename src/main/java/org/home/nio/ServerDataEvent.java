package org.home.nio;

import java.nio.channels.SocketChannel;

public class ServerDataEvent {
    MessengerServerNio server;
    public SocketChannel socket;
    public byte[] data;

    ServerDataEvent(MessengerServerNio server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}