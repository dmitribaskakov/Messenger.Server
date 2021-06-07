package org.home.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.ByteBuffer.allocate;

public class MessengerServerNio {
    private String ServerAddress;
    private int ServerPort;
    private Selector selector;
    private ByteBuffer readBuffer;
    private WorkerResponseToMessage workerResponseToMessage = new WorkerResponseToMessage();
//    private final List<ChangeRequest> changeRequests = new LinkedList<>();
    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public void start(String ServerAddress, int ServerPort) {
        this.ServerAddress = ServerAddress;
        this.ServerPort = ServerPort;
        this.readBuffer = allocate(8192);

    }
}
