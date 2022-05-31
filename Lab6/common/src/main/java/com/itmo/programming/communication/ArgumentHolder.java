package com.itmo.programming.communication;

import com.itmo.programming.dto.LocationDTO;
import com.itmo.programming.dto.PersonDTO;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class ArgumentHolder implements Serializable {
    private static final long serialVersionUID = 3019356103866688971L;
    private String[] inputParameterLine;
    private int countOfArguments;
    private PersonDTO inputPerson;
    private LocationDTO inputLocation;
    private long key;
    private long id;
    private LocalDateTime birthday;
    public int getCountParameter() {
        return inputParameterLine.length;
    }

    public ArgumentHolder(String [] inputParameterLine){
        this.inputParameterLine = inputParameterLine;
        this.countOfArguments = inputParameterLine.length;
    }
    public ArgumentHolder(){}

}
