package com.itmo.programming.server;

import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.serialization.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class SenderResponse {
    private DatagramChannel datagramChannel;

    public SenderResponse(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public void sendResponse(Response response, InetSocketAddress clientSocketAddress) throws IOException {
        byte [] responseBytes = Serialization.serializeObject(response);
        Response dataSizeResponse = new Response(responseBytes.length, ResponseCode.DATA_SIZE);
        byte [] data = Serialization.serializeObject(dataSizeResponse);
        ByteBuffer dataBuffer = ByteBuffer.wrap(data);
        datagramChannel.send(dataBuffer, clientSocketAddress);
        ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes);
        datagramChannel.send(responseBuffer, clientSocketAddress);
    }

    public void confirmConnectionClient(Response response, InetSocketAddress clientSocketAddress ) throws IOException {
        byte [] responseBytes = Serialization.serializeObject(response);
        ByteBuffer byteBuffer = ByteBuffer.wrap(responseBytes);
        datagramChannel.send(byteBuffer, clientSocketAddress);
    }
}
