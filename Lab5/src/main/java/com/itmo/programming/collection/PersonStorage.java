package com.itmo.programming.collection;

import com.itmo.programming.entity.Person;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс который хранит коллекцию и предоставляет методы для действий с ней
 */
public class PersonStorage {
    private final Date creationDate;
    private final LinkedHashMap<Long, Person> personMap;
    private final HashSet<Long> IdSet = new HashSet<>();

    /**
     * Конструктор PersonStorage, который инициализирует время создания коллекции и сохраняет id полученной коллекции  в Set
     * @param collection коллекция, полученная с файла
     */
    public PersonStorage(LinkedHashMap<Long, Person> collection) {
        this.personMap = collection;
        this.creationDate = new Date();
        updateIdSet();
    }


    /**
     * Добавляет в коллекции элент по ключу и значению
     * @param key ключ
     * @param person человек
     */
    public void add(long key, Person person) {
        personMap.put(key, person);
    }


    /** Удаляет элемент из коллекции по ключу
     * @param key ключ
     */
    public void remove(long key) {
        personMap.remove(key);
    }


    /**
     * Определяет размер коллекции
     * @return возвращает количество элементов  в коллекции
     */
    public int size() {
        return personMap.size();
    }


    /**
     * Очистка коллекции
     */
    public void clear() {
        personMap.clear();
    }

    /**
     * Собирает информацию о коллекции
     * @return Строка, которая содержит время инициализации,количество элементов, тип коллекции
     */
    public String prepareInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Время инициализации коллекции:").append(getInitializationTime()).append("\n");
        sb.append("Количество элеменетов в коллекции: ").append(size()).append("\n");
        sb.append("Тип коллекции: ").append(personMap.getClass()).append("\n");
        return sb.toString();
    }

    /**
     * @return Возвращает время инициализации коллекции
     */
    public Date getInitializationTime() {
        return creationDate;
    }

    /**
     * @return возвращает коллекцию
     */
    public LinkedHashMap<Long, Person> getCollection() {
        return personMap;
    }


    /**
     * Получение значение по заданному id
     * @param id уникальный идентификатор элемента коллекции
     * @return объект person
     */
    public Person getValueById(long id) {
        return personMap.values().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public long generateUniqueId() {
        long number;
        while (true) {
            number = generateNumber();
            if (!IdSet.contains(number)) {
                IdSet.add(number);
                break;
            }
        }
        return number;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Количество элементов коллекции: " + size()).append("\n");
        personMap.forEach((key, person) -> stringBuilder.append("    Person key: ").append(key).append("\n").append(person.toString()).append("\n"));
        return stringBuilder.toString();
    }

    /**
     * Генериратор id для person
     * @return число типа long
     */
    private long generateNumber() {
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }

    /**
     * Сохраняет в set все id элементов коллекции
     */
    private void updateIdSet() {
        personMap.values().forEach(x -> IdSet.add(x.getId()));
    }
}
