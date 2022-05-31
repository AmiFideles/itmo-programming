package com.itmo.programming.fileworkers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.entity.Person;
import com.itmo.programming.fileworkers.exceptions.IdenticalValueIdException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс предназначен для чтение коллекции с JSON файла
 * @author Iskandarov Shakhzodbek P3133
 */
public class JsonReader {

    /**
     * Метод загружает коллекцию из файла
     * @param file полученный файл
     * @return коллекция, полученная с файла
     * @throws IdenticalValueIdException бросается, если в файле не уникальные id элементов коллекции
     */
    public LinkedHashMap<Long, Person> loadCollectionFromFile(File file) throws IdenticalValueIdException, IOException {
        LinkedHashMap<Long, PersonDTO> mapPersonDTO;
        LinkedHashMap<Long, Person> personMap = new LinkedHashMap<>();
        try (FileReader fileReader = new FileReader(file)) {
            if (file.length() == 0) {
                return new LinkedHashMap<>();
            }
            mapPersonDTO = deserializeFromJSON(fileReader);
            if (!checkUniquenessId(mapPersonDTO)) {
                throw new IdenticalValueIdException("Во входном файле значения id у person не уникальные. Теперь вы работаете с пустой коллекцией");
            }
            for (Map.Entry<Long, PersonDTO> longPersonDTOEntry : mapPersonDTO.entrySet()) {
                personMap.put(longPersonDTOEntry.getKey(), longPersonDTOEntry.getValue().transferToPerson());
            }
        }
        return personMap;
    }

    /**
     * @param fileReader входной поток
     * @return возвращает десериализованные данные с фалйа
     */
    private LinkedHashMap<Long, PersonDTO> deserializeFromJSON(FileReader fileReader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            LinkedHashMap<Long, Person> personMap = null;
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).setPrettyPrinting().create();
            LinkedHashMap<Long, PersonDTO> mapPersonDTO = gson.fromJson(bufferedReader, new TypeToken<LinkedHashMap<Long, PersonDTO>>() {
            }.getType());
            mapPersonDTO.values().forEach(PersonDTO::checkFields);
            return mapPersonDTO;

        }
    }

    /**
     * @return возвращает true, если в коллекции уникальные id у элементов
     */
    private boolean checkUniquenessId(LinkedHashMap<Long, PersonDTO> linkedHashMap) {
        Set<Long> setId = linkedHashMap.values().stream().map(PersonDTO::getId).collect(Collectors.toSet());
        return setId.size() - linkedHashMap.size() == 0;
    }

}
