package org.home.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.OP_ACCEPT;

public class MessengerServerNio {
    private InetSocketAddress serverAddress;
    private Selector selector;
    private ByteBuffer readBuffer;
//    private WorkerResponseToMessage workerResponseToMessage = new WorkerResponseToMessage();
//    private final List<ChangeRequest> changeRequests = new LinkedList<>();
//    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public void start(String ServerAddress, int ServerPort) throws IOException {
        serverAddress = new InetSocketAddress(ServerAddress, ServerPort);
        readBuffer = allocate(8192);
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(serverAddress);
        selector = SelectorProvider.provider().openSelector();
        serverChannel.register(selector, OP_ACCEPT);


    }
}
