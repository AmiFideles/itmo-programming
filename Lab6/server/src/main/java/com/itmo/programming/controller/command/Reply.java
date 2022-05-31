package com.itmo.programming.controller.command;

import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class Reply {
    private ArrayList<String> replyList;

    public Reply() {
        this.replyList = new ArrayList<>();
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

}
