package com.makitara.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel nioServer = ServerSocketChannel.open();
        nioServer.configureBlocking(false);
        nioServer.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        nioServer.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = (SelectionKey)iterator.next();
                iterator.remove();
                if(key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(buffer);
                    if(len != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.remaining()));
                    }else {
                        socketChannel.close();
                        System.out.println("Client disconnected");
                    }
                }
            }
        }
    }
}
