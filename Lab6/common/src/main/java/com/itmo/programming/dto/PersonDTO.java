package com.itmo.programming.dto;

import com.itmo.programming.console.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 7613016340933649170L;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private CoordinatesDTO coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле может быть null
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Поле может быть null
    private ColorDTO hairColor; //Поле не может быть null
    private LocationDTO location; //Поле не может быть null

    public PersonDTO(String name, CoordinatesDTO coordinates, Long height, LocalDateTime birthday, String passportID, ColorDTO hairColor, LocationDTO location) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = Date.from(Instant.now());
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }

    public PersonDTO(String name, long id, CoordinatesDTO coordinates, Long height, LocalDateTime birthday, String passportID, ColorDTO hairColor, LocationDTO location) {
        this.name = name;
        this.id=id;
        this.coordinates = coordinates;
        this.creationDate = Date.from(Instant.now());
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;
    }
    public void checkFields() {
        StringBuilder sb = new StringBuilder();
        if (id <= 0) {
            sb.append("Значения id у человека не может быть меньше 0").append("\n");
        }
        if (name == null) {
            sb.append("Значения имени не может быть null").append("\n");
        }
        if (coordinates == null) {
            sb.append("Значения координат не может быть null").append("\n");
        }else {
            if (coordinates.getX() == null) {
                sb.append("Значения координаты X  не может быть null ").append("\n");
            }
            if (coordinates.getY() == null) {
                sb.append("Значения координаты Y  не может быть null").append("\n");
            }
        }

        if (creationDate == null) {
            creationDate = Date.from(Instant.now());
        }
        if (birthday==null){
            sb.append("Значение даты рождения не может быть null").append("\n");
        }
        if (hairColor == null) {
            sb.append("Значение цвета волос не может быть null").append("\n");
        }
        if (location == null) {
            sb.append("Значение локации не может быть null").append("\n");
        }else {
            if (location.getY() == null) {
                sb.append("Координата Y локации не может быть равна null").append("\n");
            }
            if (location.getName() == null) {
                sb.append("Значения имени локации не может быть равна null").append("\n");
            }
        }
        if (!(sb.length()==0)){
            sb.insert(0, "Во входном файле есть некорректные данные. В следующий раз исправьте все виды ошибок: \n");
            throw new InvalidInputException(sb.toString());
        }
    }
}
