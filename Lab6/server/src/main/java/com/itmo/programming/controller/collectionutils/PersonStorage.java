package com.itmo.programming.controller.collectionutils;

import com.itmo.programming.model.Person;

import java.util.*;
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

    public long getKeyById(long id){
        //TODO Так коряво, что кровь из глаз, но сейчас 3 часа ночи. Я забыл вообще как пользоваться стримами
        Optional<Long> temp = personMap.entrySet().stream().filter(x->x.getValue().getId()==id).map(Map.Entry::getKey).findFirst();
        return temp.get();

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
        List<String> tempList = new ArrayList<>();
        tempList.add("Количество элементов коллекции: " + size());
        personMap.forEach((key,person)->tempList.add(String.format("    Ключ элемента: %s\n" + person.toString(), key)));
        return String.join("\n", tempList);
    /*    personMap.forEach((key, person) -> stringBuilder.append("    Ключ элемента: ").append(key).append("\n").append(person.toString()).append("\n"));
        return stringBuilder.toString();*/
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
