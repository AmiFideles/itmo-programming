package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

import java.io.File;
import java.io.IOException;


public class SaveCommand extends Command {
    private final ReceiverService storageService;
    private final String path;
    private final String pathBackupFile = System.getProperty("user.dir") + File.separator + "backupFile.json";

    public SaveCommand(ReceiverService storageService, String path) {
        super("save", ": сохраняет коллекцию в файл", 0);
        this.storageService = storageService;
        this.path = path;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException, IOException {
        checkCountOfArgument(argument.length);
        File file = new File(path);
        if (!(file.exists()) | !(file.canWrite())) {
            if (!(file.exists())) {
                consoleManager.write(String.format("Файла по пути %s не существует", path));
            } else {
                if (!(file.canWrite())) {
                    consoleManager.write(String.format("У файла по заданному пути %s нет прав для записи", path));
                }
            }
            File file1 = new File(pathBackupFile);
            file1.createNewFile();
            storageService.save(pathBackupFile);
            consoleManager.write(String.format("Коллекция сохранена в файл по пути %s, чтобы данные ваши не потерялись", pathBackupFile));

        } else {
            storageService.save(path);
            consoleManager.write("Коллекция сохранена в файл по пути " + path);
        }
    }
}
