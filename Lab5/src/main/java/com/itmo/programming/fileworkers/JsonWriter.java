package com.itmo.programming.fileworkers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itmo.programming.dto.PersonDTO;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * Класс предназначен для записи коллекции в JSON файл
 * @author Iskandarov Shakhzodbek P3133
 */
public class JsonWriter {
    private final String path;


    public JsonWriter(String path) {
        this.path = path;
    }

    /**
     * Записывать данные в файле в формате json
     * @param linkedHashMap коллекцию, которую нужно записать в файл
     */
    public void writeCollectionToFile(LinkedHashMap<Long, PersonDTO> linkedHashMap) throws IOException {
        File file = new File(path);
        try (OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream)) {
            String jsonFormatText = serializeToJSON(linkedHashMap);
            outputStreamWriter.write(jsonFormatText);
        }
    }

    /**
     * @return возвращает строку из элементов коллекции в формте json
     */
    private String serializeToJSON(LinkedHashMap<Long, PersonDTO> linkedHashMap) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).setPrettyPrinting().serializeNulls().create();
        return gson.toJson(linkedHashMap);
    }
}
