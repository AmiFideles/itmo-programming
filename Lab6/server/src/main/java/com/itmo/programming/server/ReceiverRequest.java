package com.itmo.programming.server;

import com.itmo.programming.communication.Request;
import com.itmo.programming.serialization.Serialization;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ReceiverRequest {
    private DatagramChannel datagramChannel;
    private SocketAddress clientAddress;
    private final int bufferSize = 4096;

    public ReceiverRequest(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public Request receiveRequest() throws IOException {
        ByteBuffer byteBuffer =  ByteBuffer.allocate(bufferSize);
        clientAddress = datagramChannel.receive(byteBuffer);
        if (Objects.nonNull(clientAddress)){
            Request request = Serialization.deserializeObject(byteBuffer.array());
            return request;
        }
        return null;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }
}
