package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

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
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        File file = new File(path);
        if (!(file.exists()) | !(file.canWrite())) {
            if (!(file.exists())) {
                reply.addCommandResponseBody(String.format("Файла по пути %s не существует", path));
            } else {
                if (!(file.canWrite())) {
                    reply.addCommandResponseBody(String.format("У файла по заданному пути %s нет прав для записи", path));
                }
            }
            File file1 = new File(pathBackupFile);
            file1.createNewFile();
            storageService.save(pathBackupFile);
            reply.addCommandResponseBody(String.format("Коллекция сохранена в файл по пути %s, чтобы данные ваши не потерялись", pathBackupFile));
            return reply;
        } else {
            storageService.save(path);
            reply.addCommandResponseBody("Коллекция сохранена в файл по пути " + path);
            return reply;
        }
    }
}

