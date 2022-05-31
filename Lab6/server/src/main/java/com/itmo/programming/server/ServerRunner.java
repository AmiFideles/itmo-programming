package com.itmo.programming.server;

import com.google.gson.JsonSyntaxException;
import com.itmo.programming.communication.*;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.console.exceptions.InvalidInputException;
import com.itmo.programming.controller.collectionutils.CommandReceiver;
import com.itmo.programming.controller.collectionutils.PersonStorage;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.CommandManager;
import com.itmo.programming.controller.command.InvokerCommand;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.controller.command.withoutArgument.SaveCommand;
import com.itmo.programming.controller.fileworkers.JsonReader;
import com.itmo.programming.controller.fileworkers.exceptions.IdenticalValueIdException;
import com.itmo.programming.model.Person;
import com.itmo.programming.server.exceptions.InabilityOpenServer;

import javax.xml.xpath.XPath;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ServerRunner {
    private InvokerCommand invokerCommand;
    private CommandManager commandManager;
    private ReceiverService receiverService;
    private ReceiverRequest receiverRequest;
    private SenderResponse senderResponse;
    private RequestHandler requestHandler;
    private DatagramChannel datagramChannel;
    private InetSocketAddress serverSocketAddress;
    private ConsoleInterface consoleInterface;
    private SaveCommand saveCommand;
    private Console console;
    private int serverPort;
    private boolean workState;
    private String pathFile;
    private static final String PS = "$";

    public ServerRunner(String pathFile, InetSocketAddress serverSocketAddress, ConsoleInterface consoleInterface) {
        this.pathFile = pathFile;
        this.serverSocketAddress = serverSocketAddress;
        this.consoleInterface = consoleInterface;
    }

    public void init() throws InabilityOpenServer, IOException {

        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(serverSocketAddress);
        senderResponse = new SenderResponse(datagramChannel);
        receiverRequest = new ReceiverRequest(datagramChannel);
        console = System.console();
        consoleInterface.write("Сервер запущен");
        PersonStorage personStorage = new PersonStorage(load(pathFile, consoleInterface));
        receiverService = new CommandReceiver(personStorage);
        commandManager = new CommandManager(receiverService, pathFile);
        invokerCommand = new InvokerCommand(commandManager);
        requestHandler = new RequestHandler(invokerCommand);
        saveCommand = (SaveCommand) commandManager.getCommand("save");
    }


    public void start() {
        try {
            workState = true;
            processInterruptHandler();
            init();
            consoleInterface.write("Доступные команды для сервера: save, exit");
            while (workState) {
                checkCommands();
                Request request = receiverRequest.receiveRequest();
                if (Objects.isNull(request)) {
                    continue;
                }
                if (request.getRequestCode().equals(RequestCode.CONNECT)) {
                    senderResponse.confirmConnectionClient(new Response(ResponseCode.CONNECTED), (InetSocketAddress) receiverRequest.getClientAddress());
                    System.out.println("Подключился клиент по адресу " + ((InetSocketAddress) receiverRequest.getClientAddress()).getAddress() + " Порт: " + ((InetSocketAddress) receiverRequest.getClientAddress()).getPort());
                } else {
                    System.out.println("Получен запрос " + request.getCommandName());
                    Reply reply = requestHandler.handle(request);
                    senderResponse.sendResponse(new Response(reply.getData(), ResponseCode.OK), (InetSocketAddress) receiverRequest.getClientAddress());
                }


            }
        } catch (InabilityOpenServer inabilityOpenServer) {
            inabilityOpenServer.printStackTrace();
        } catch (BindException e) {
            consoleInterface.write(String.format("Порт %d, по которому сервер запускается недоступен", serverSocketAddress.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workState = false;
        }
    }

    private void connect(InetSocketAddress inetSocketAddress) throws IOException {
        senderResponse.confirmConnectionClient(new Response(ResponseCode.CONNECTED), inetSocketAddress);
    }


    public void save()  {
        Reply reply = null;
        try {
            reply = saveCommand.execute(new ArgumentHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        consoleInterface.write(" Коллекция сохранилась в файл");
    }

    private void checkCommands() throws IOException {
        try {
            if (System.in.available() > 0) {
                String line = console.readLine();
                if ("save".equals(line)) {
                    Reply reply = saveCommand.execute(new ArgumentHolder());
                    System.out.println(reply.getData());
                }
                else if ("exit".equals(line)) {
                    close();
                }
                else {
                    consoleInterface.write("Такой команды не существует");
                }
            }
        }catch (NoSuchElementException e ){

        }
    }

       private void processInterruptHandler() {
               Runtime.getRuntime().addShutdownHook(new Thread() {
                   public void run() {
                       if (Objects.nonNull(saveCommand)){
                           save();
                       }
                   }
               });

       }
    public void close() {
        System.out.println(" Сервер заканчивает работу");
        try {
            datagramChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(-1);
    }

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
            //consoleManager.write("Коллекция успешно загружена. Элементов в коллекции " + inputCollection.size());
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
}
