package org.home.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.home.env.Settings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.NotYetConnectedException;
import java.util.*;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.*;
import static java.nio.channels.SelectionKey.OP_READ;
import static org.home.nio.ChangeRequest.CHANGEOPS;

public class MessengerServerNio {
    private Selector selector;
    private ByteBuffer readBuffer;
    private MessageWorker messageWorker;
    private final List<ChangeRequest> changeRequests = new LinkedList<>();
    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    //static Logger log = LoggerFactory.getLogger(MessengerServerNio.class);

    public void start(Settings settings) throws IOException {
        readBuffer = allocate(8192);
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(settings.getServerAddress(), settings.getServerPort()));
        selector = SelectorProvider.provider().openSelector();
        serverChannel.register(selector, OP_ACCEPT);

        // запускаем обработку сообщений в отдельном потоке
        messageWorker = new MessageWorker();
        new Thread(messageWorker).start();

        while (true) {
            // обрабатываем запросы на изменения селектора
            synchronized (changeRequests) {
                for (ChangeRequest change : changeRequests) {
                    switch (change.type) {
                        case CHANGEOPS:
                            SelectionKey key = change.socket.keyFor(selector);
                            key.interestOps(change.ops);
                            break;
                        default:
                    }
                }
                changeRequests.clear();
            }

            // ждем событий в канале
            selector.select();

            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                selectedKeys.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, OP_READ);
        System.out.println("Accept connection from client " + socketChannel.getRemoteAddress());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        try {
            int numRead = socketChannel.read(readBuffer);
            messageWorker.processData(this, socketChannel, readBuffer.array(), numRead);
        } catch (IOException e) { //NotYetConnectedException
            System.out.println("Close connection to client " + socketChannel.getRemoteAddress());
            closeSocketChannel(key);
        }
    }

    private void closeSocketChannel(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            socketChannel.close();
        } catch (Exception e) {
            System.out.println("Close Socket Channel not successful "+e.getMessage());
        }
    }
    void send(SocketChannel socket, byte[] data) {
        synchronized (changeRequests) {
            changeRequests.add(new ChangeRequest(socket, CHANGEOPS, OP_WRITE));
            synchronized (pendingData) {
                List<ByteBuffer> queue = pendingData.get(socket);
                if (queue == null) {
                    queue = new ArrayList<>();
                    pendingData.put(socket, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }
        selector.wakeup();
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        synchronized (pendingData) {
            List<ByteBuffer> queue = pendingData.get(socketChannel);
            while (!queue.isEmpty()) {
                ByteBuffer writeBuffer = queue.get(0);
                socketChannel.write(writeBuffer);
                if (writeBuffer.remaining() > 0) {
                    break;
                }
                System.out.println("Send echo = " + new String(writeBuffer.array()));
                queue.remove(0);
            }
            if (queue.isEmpty()) {
                key.interestOps(OP_READ);
            }
        }
    }
}
