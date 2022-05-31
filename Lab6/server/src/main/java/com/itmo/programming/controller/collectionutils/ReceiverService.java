package com.itmo.programming.controller.collectionutils;

import com.itmo.programming.model.Location;
import com.itmo.programming.model.Person;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для приемника, который отвечает за выполнение всех команд
 */
public interface ReceiverService {
    String showStorageData();

    String getInfoAboutCollection();

    void insertKey(long key, Person person);

    void removeKey(long key);

    void clear();

    void save(String pathToFile) throws IOException;

    void exit();

    void removeGreater(Person person);

    void removeGreaterKey(long key);

    void removeAnyByBirthday(LocalDateTime date);

    long countLessThanLocation(Location location);

    List<Person> sortAscendingOrder();

    boolean checkKey(long key);

    boolean checkId(long id);

    Person getPersonById(long id);

    long getKeyById(long id);

    void updatePerson(long key, Person person);

    int getSizeCollection();
}
