package org.home.nio;

import java.nio.channels.SocketChannel;

public class ChangeRequest {
    static final int CHANGEOPS = 2;

    public SocketChannel socket;
    public int type;
    int ops;

    ChangeRequest(SocketChannel socket, int type, int ops) {
        this.socket = socket;
        this.type = type;
        this.ops = ops;
    }
}