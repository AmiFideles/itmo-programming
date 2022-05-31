package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.CommandManager;
import com.itmo.programming.command.InvokerCommand;
import com.itmo.programming.command.exceptions.EndlessLoopingException;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;


public class ExecuteScriptCommand extends Command {
    private final ReceiverService receiverService;
    private final String pathToSave;
    private final static HashSet<String> path = new HashSet<>();

    public ExecuteScriptCommand(ReceiverService storageService,String pathToSave) {
        super("execute_script", ": считает и исполняет скрипт из указанного файла. В скрипте должны содержатся команды в таком же виде, в котором вы вводите в интерактивном режиме. ", 1);
        this.receiverService = storageService;
        this.pathToSave = pathToSave;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        String scriptPath = argument[0];
        long startTime = System.currentTimeMillis();
        path.add(scriptPath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(scriptPath), StandardCharsets.UTF_8))){
            ConsoleInterface fileInterface = new ConsoleInterface(bufferedReader,  false);
            ArgumentQuestioner argumentQuestioner = new ArgumentQuestioner(fileInterface);
            CommandManager commandManager = new CommandManager(receiverService, argumentQuestioner, pathToSave);
            //commandManager.initializeAllCommand();
            consoleManager.write("Начинаем выполнять скрипт " + scriptPath);
            InvokerCommand invokerCommand = new InvokerCommand(commandManager);
            String inputLine;
            while ((inputLine=fileInterface.readLineFromFile())!=null) {
                String[] listScriptPath = inputLine.split(" ");
                if("execute_script".equals(listScriptPath[0])) {
                    if (!path.contains(listScriptPath[1])) {
                        invokerCommand.execute(fileInterface, inputLine);
                    } else {
                        throw new EndlessLoopingException("Вы пытаетесь закциклить выполнение. Хотите сломать программу???");
                    }
                } else {
                    invokerCommand.execute(fileInterface, inputLine);
                }
            }
            fileInterface.write(String.format("Скрипт %s выполнен успешно. Его выполнение заняло %d ms", scriptPath, System.currentTimeMillis() - startTime));
        } catch (FileNotFoundException e) {
            if (!(new File(scriptPath).exists())){
                consoleManager.write("Данного скрипта не существует");
            }
            if (!(new File(scriptPath).canRead())){
                consoleManager.write("Нету прав для чтения скрипта");
            }
        } catch (EndlessLoopingException e ){
            consoleManager.write(e.getMessage());
        } catch (Exception e) {
            consoleManager.write("Ошибка во время выполнения скрипта. Проверьте корректность данных в скрипте");
        }
        path.remove(scriptPath);
    }
}
