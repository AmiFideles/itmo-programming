package com.itmo.programming.commands;

import com.itmo.programming.communication.ArgumentHolder;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Getter @Setter
public class CommandResponse {
    private String commandName;
    private ArrayList<String> replyList;
    private ArgumentHolder argumentHolder;
    private TypeCommandResponse typeCommandResponse; //Дефолтное значение пусть будет ERROR;
    public CommandResponse(ArgumentHolder argumentHolder, ArrayList<String> replyList, TypeCommandResponse typeCommandResponse) {
        this.argumentHolder = argumentHolder;
        this.replyList = replyList;
        this.typeCommandResponse = typeCommandResponse;
    }

    public void addCommandResponseBody(String responseBody){
        replyList.add(responseBody);
    }
    public void clear(){
        replyList.clear();
    }
    public String getData(){
        return String.join("\n", replyList);
    }

    public ArgumentHolder getArgumentHolder() {
        return argumentHolder;
    }

    public void setArgumentHolder(ArgumentHolder argumentHolder) {
        this.argumentHolder = argumentHolder;
    }

}
