package com.itmo.programming.entity;

import lombok.*;

/**
 * Модель для локации
 */

@Getter
@Setter
public class Location implements Comparable<Location> {
    private double x;
    private Double y; //Поле не может быть null
    private double z;
    private String name; //Поле не может быть null

    public Location(double x, Double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.x, x) != 0) return false;
        if (Double.compare(location.z, z) != 0) return false;
        if (!y.equals(location.y)) return false;
        return name.equals(location.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + y.hashCode();
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Локация:" +"\n"+
                "x: " + x +"\n"+
                "y: " + y +"\n"+
                "z: " + z +"\n"+
                "name: " + name;
    }

    @Override
    public int compareTo(Location o) {
        double volume = x * y * z;
        double volume1 = o.x * o.y * o.z;
        if (volume > volume1) {
            return 1;
        } else if (volume1 > volume) {
            return -1;
        }
        return 0;
    }
}
