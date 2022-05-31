package com.itmo.programming.server;

import com.itmo.programming.communication.Request;
import com.itmo.programming.communication.Response;
import com.itmo.programming.serialization.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class Server {
    private DatagramChannel serverDatagramChannel;
    private SocketAddress serverSocketAddress;
    private SocketAddress clientSocketAddress;
    private final int bufferSize = 4096;
    private int serverPort;
    //Переписать инициализацию объектов в другой метод
    public Server(int serverPort){
        this.serverPort = serverPort;

    }

    public boolean init() throws IOException {
        boolean temp = true;
        serverDatagramChannel = DatagramChannel.open();
        serverDatagramChannel.configureBlocking(false);
        serverDatagramChannel.bind(new InetSocketAddress(serverPort));
        return temp;
    }

    public void sendResponse(Response response) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        //Разобраться что за ошибка тут может вылезти
        byteBuffer.put(Serialization.serializeObject(response));
        byteBuffer.flip();
        serverDatagramChannel.send(byteBuffer, clientSocketAddress);
    }

    public Request receiveRequest() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        clientSocketAddress = serverDatagramChannel.receive(byteBuffer);
        byteBuffer.flip();
        return Serialization.deserializeObject(byteBuffer.array());
    }

    public void run(){
        //Отправка доступных команд
        //

    }
}

