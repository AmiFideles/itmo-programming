package com.itmo.programming.communication;


import com.itmo.programming.serialization.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 5467842688395534629L;
    private String responseBody;
    private ResponseCode responseCode;
    private int buffer_size;

    public Response(int buffer_size, ResponseCode responseCode) {
        this.buffer_size = buffer_size;
        this.responseCode = responseCode;
    }

    public Response(String responseBody, ResponseCode responseCode) {
        this.responseBody = responseBody;
        this.responseCode = responseCode;
    }
    public Response(ResponseCode responseCode){
        this.responseCode = responseCode;
    }

}
