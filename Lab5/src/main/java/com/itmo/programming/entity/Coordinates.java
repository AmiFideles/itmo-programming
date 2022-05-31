package com.itmo.programming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель координат
 */
@Getter @Setter
@AllArgsConstructor
public class Coordinates {
    private Long x; //Поле не может быть null
    private Double y; //Значение поля должно быть больше -537

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (!x.equals(that.x)) return false;
        return y != null ? y.equals(that.y) : that.y == null;
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Координаты: " +"\n"+
                "x: " + x + "\n"+
                "y: " + y;
    }
}
