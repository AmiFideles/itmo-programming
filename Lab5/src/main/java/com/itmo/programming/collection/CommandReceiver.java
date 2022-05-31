package com.itmo.programming.collection;

import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.entity.Location;
import com.itmo.programming.entity.Person;
import com.itmo.programming.fileworkers.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс приемник команд, в которым описаны методы для реализации основных команд
 */

public class CommandReceiver implements ReceiverService {
    private final PersonStorage personStorage;
    /**
     * Конструктор, который инциализирует коллекцию с которой работать.
     * @param personStorage коллекция, с который будет работать receiver.
     */
    public CommandReceiver(PersonStorage personStorage) {
        this.personStorage = personStorage;
    }

    /**Возвращает
     * Возврашает все элементы коллекции
     * @return  Строку со всеми элементами коллекции
     */
    @Override
    public String showStorageData() {
        return personStorage.toString();
    }

    /**
     * Возвращает информацию о коллекции
     * @return Строка, которая содержит время инициализации,количество элементов, тип коллекции
     */
    @Override
    public String getInfoAboutCollection() {
        return personStorage.prepareInfo();
    }

    /**
     * Добавляет в коллекцию объект person
     * @param person объект
     * @param key ключ
     */
    @Override
    public void insertKey(long key, Person person) {
        long id = personStorage.generateUniqueId();
        person.setId(id);
        personStorage.add(key, person);
    }


    /**
     * Удаляет элемент коллекции по его ключу
     * @param key ключ
     */
    @Override
    public void removeKey(long key) {
        personStorage.remove(key);
    }

    /**
     * Полностью чистит коллекцию
     */
    @Override
    public void clear() {
        personStorage.clear();
    }

    /**
     * Cохраняет коллекцию в JSON файл
     * @param pathToFile путь до файла
     * @throws IOException Пробрасывается, в случае если команда работает с I/O и произошла ошибка
     */
    @Override
    public void save(String pathToFile) throws IOException {

        JsonWriter jsonWriter = new JsonWriter(pathToFile);
        LinkedHashMap<Long, PersonDTO> mapPersonDTO = new LinkedHashMap<>();
        for (Map.Entry<Long, Person> longPersonEntry : personStorage.getCollection().entrySet()) {
            mapPersonDTO.put(longPersonEntry.getKey(), new PersonDTO(longPersonEntry.getValue()));
        }
        jsonWriter.writeCollectionToFile(mapPersonDTO);
    }

    /**
     * Метод, который завершает выполнение программы
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /**
     * Удаляет все элементы из коллекции, превыщающий заданный.
     * @param person объект
     */
    @Override
    public void removeGreater(Person person) {
        personStorage.getCollection().entrySet().removeIf(element -> element.getValue().compareTo(person) > 0);
    }

    /**
     * Удаляет из коллекции все элементы, ключ которых превышает заданный
     * @param key ключ
     */
    @Override
    public void removeGreaterKey(long key) {
        personStorage.getCollection().entrySet().removeIf(element -> element.getKey() > key);
    }

    /**
     * Удаляет из коллекции элементы, значение поля birthday которого эквивалентно заданному"
     * @param date дата рождения
     */
    @Override
    public void removeAnyByBirthday(LocalDateTime date) {
        personStorage.getCollection().entrySet().removeIf(tempEntry -> tempEntry.getValue().getBirthday().equals(date));
    }

    /**
     *  Вычисляет количество элементов, значение поля location которых меньше заданного
     * @param location объект локация
     * @return количество таких локаций
     */
    @Override
    public long countLessThanLocation(Location location) {
        return personStorage.getCollection().values().stream().filter(x -> x.getLocation().compareTo(location) == -1).count();
    }

    /** Сортирует все элементы в порядке возрастания
     * @return отсортированную коллекцию в виде листа
     */
    @Override
    public List<Person> sortAscendingOrder() {
        return personStorage.getCollection().values().stream().sorted().collect(Collectors.toList());

    }

    /**
     * Проверяет существует ли элемент в коллекции по данному ключу
     * @param key ключ
     * @return True, если элемент не содержится в коллекции. False, если содержится
     */
    @Override
    public boolean checkKey(long key) {
        return !personStorage.getCollection().containsKey(key);
    }

    /**
     * Возвращает person по его id
     * @param id человека
     * @return объект Person, полученный по заданному id
     */
    @Override
    public Person getPersonById(long id) {
        return personStorage.getValueById(id);
    }

    /**
     * Проверяет существует ли элемент в коллекции
     * @param id человека
     * @return True если существует элемент в коллекции по заданному id
     */
    @Override
    public boolean checkId(long id) {
        return (personStorage.getCollection().values().stream().anyMatch(x -> x.getId() == id));
    }
}
