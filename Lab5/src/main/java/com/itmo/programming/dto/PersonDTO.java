package com.itmo.programming.dto;

import com.itmo.programming.entity.Color;
import com.itmo.programming.entity.Coordinates;
import com.itmo.programming.entity.Location;
import com.itmo.programming.entity.Person;
import com.itmo.programming.exceptions.InvalidInputException;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

/**
 * Data transfer object для людей
 */
@Data
public class PersonDTO {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.coordinates = person.getCoordinates();
        this.creationDate = person.getCreationDate();
        this.height = person.getHeight();
        this.birthday = person.getBirthday();
        this.passportID = person.getPassportID();
        this.hairColor = person.getHairColor();
        this.location = person.getLocation();
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

    public Person transferToPerson() {
        return new Person(
                id,
                name,
                coordinates,
                creationDate,
                height,
                birthday,
                passportID,
                hairColor,
                location
        );
    }
}
