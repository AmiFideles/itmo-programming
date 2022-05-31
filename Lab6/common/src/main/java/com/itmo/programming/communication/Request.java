package com.itmo.programming.communication;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -5570790526731334044L;
    private String commandName;
    private ArgumentHolder argumentHolder;
    private RequestCode requestCode;

    public Request(RequestCode requestCode) {
        this.requestCode = requestCode;
    }

    public Request(String commandName, ArgumentHolder argumentHolder, RequestCode requestCode) {
        this.commandName = commandName;
        this.argumentHolder = argumentHolder;
        this.requestCode = requestCode;
    }

}
