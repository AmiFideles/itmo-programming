package com.itmo.programming;

import com.google.gson.JsonSyntaxException;
import com.itmo.programming.collection.CommandReceiver;
import com.itmo.programming.collection.PersonStorage;
import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.CommandManager;
import com.itmo.programming.command.InvokerCommand;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.entity.Person;
import com.itmo.programming.fileworkers.exceptions.IdenticalValueIdException;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.exceptions.InvalidInputException;
import com.itmo.programming.command.exceptions.NoSuchCommandException;
import com.itmo.programming.fileworkers.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/**
 * Главный класс программы
 *
 * @author Iskandarov Shakhzodbek P3133
 */
public class Main {
    private static final String PS1 = "$ ";

    public static void main(String[] args) {
        String path = args.length == 0 ? "" : args[0];
        ConsoleInterface consoleManager = new ConsoleInterface(true);
        LinkedHashMap<Long, Person> inputCollection = load(path, consoleManager);
        launchCommandLineInterface(inputCollection, consoleManager, path);
    }

    /**
     * Метод для загрузки коллекции
     *
     * @param pathToFile     путь к файлу
     * @param consoleManager интерфейс обмена данными между пользователем и программой
     * @return коллекция полученная с файла
     */
    public static LinkedHashMap<Long, Person> load(String pathToFile, ConsoleInterface consoleManager) {
        boolean success = false;
        JsonReader jsonReader = new JsonReader();
        LinkedHashMap<Long, Person> inputCollection = null;
        try {
            if ((pathToFile.length() == 0)) {
                throw new InvalidInputException("Вы не задали адрес к файлу");
            }
            File file = new File(pathToFile);
            inputCollection = jsonReader.loadCollectionFromFile(file);
            consoleManager.write("Коллекция успешно загружена. Элементов в коллекции " + inputCollection.size());
            success = true;
        } catch (InvalidInputException | IdenticalValueIdException e) {
            consoleManager.write(e.getMessage());
        } catch (FileNotFoundException e) {
            consoleManager.write("Данного файла не существует или у вас нет прав для чтения. Коллекция не будет загружена.");
        } catch (IOException e) {
            consoleManager.write("Ошибка ввода/вывода. Что то пошло не так во время считывания файла");
        } catch (JsonSyntaxException e) {
            consoleManager.write("ошибка в синтаксисе json файла");
        } catch (ClassCastException e) {
            consoleManager.write("Ошибка приведения типов. Проверьте данные в файле");
        } catch (Exception e) {
            consoleManager.write("Неизвестная ошибка. Я все исправлю");
        }
        if (!success) {
            consoleManager.write("Теперь вы работаете с пустой коллекцией");
            return new LinkedHashMap<>();
        }
        return inputCollection;
    }

    /**
     * Метод, который читает данные с консоли и взаимодействует с InvokerCommand
     *
     * @param inputCollection коллекция полученная с файла
     * @param consoleManager  интерфейс обмена данными между пользователем и программой
     * @param path            путь к файлу
     */
    public static void launchCommandLineInterface(LinkedHashMap<Long, Person> inputCollection, ConsoleInterface consoleManager, String path) {
        PersonStorage personStorage = new PersonStorage(inputCollection);
        ReceiverService storageService = new CommandReceiver(personStorage);
        ArgumentQuestioner askerArgument = new ArgumentQuestioner(consoleManager);
        CommandManager commandManager = new CommandManager(storageService, askerArgument, path);
        //commandManager.initializeAllCommand();
        InvokerCommand invokerCommand = new InvokerCommand(commandManager);
        while (true) {
            try {
                consoleManager.writeWithoutSpace(PS1);
                String line = consoleManager.read();
                invokerCommand.execute(consoleManager, line);
            } catch (IncorrectNumberOfArgumentsException | NoSuchCommandException e) {
                consoleManager.write(e.getMessage());
            } catch (InvalidInputException e) {
                consoleManager.write(e.getMessage() + " Попробуйте запустить команду заново!");
            } catch (NumberFormatException e) {
                consoleManager.write("Вы ввели некорректное число, попробуйте исправить и запустить заново");
            } catch (NoSuchElementException e) {
                consoleManager.write("Ввелен ctrl D");
            } catch (Exception e) {
                consoleManager.write("Произошла ошибка, автор не заметил её при тестировании! Все будет исправлено в скором времени. ");
                e.printStackTrace();
            }
        }
    }
}
