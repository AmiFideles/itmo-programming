package com.itmo.programming.client;

import com.itmo.programming.client.exceptions.InabilityConnectException;
import com.itmo.programming.client.exceptions.WrongAmountOfElementsException;
import com.itmo.programming.commands.CommandHolder;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;

import java.io.IOException;
import java.net.BindException;
import java.net.UnknownHostException;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class Application {
    private static String host;
    private static int port;

    public static void main(String[] args) throws IOException, InabilityConnectException {
        //args = new String[]{"localhost", "50004"};
        if (!initializeConnectionAddress(args)) {
            System.out.println("Всего доброго!");
        } else {
            System.out.println("Начинаем работу");
            ClientRunner clientRunner = ClientRunner.getClientRunner();
            ConsoleInterface consoleInterface = new ConsoleInterface(true);
            Client client = new Client(host, port, 20000,  consoleInterface);
            clientRunner.setConsoleInterface(consoleInterface);
            clientRunner.init(new UserHandler(new CommandHolder(new ArgumentQuestioner(new ConsoleInterface(true)))), client);
            try {
                clientRunner.start();
                }catch (InabilityConnectException e){
                consoleInterface.write("Сервер недоступен");
            }catch (UnknownHostException e ){
                consoleInterface.write("Вы ввели неверное имя хоста");
            }catch (BindException e){
                consoleInterface.write(String.format("Порт %d, по которому запускается клиентское приложение недоступен", client.getClientPort()));
            }

        }
    }

    public static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new ArithmeticException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: java -jar jarName <host> <port>");
            return false;
        } catch (NumberFormatException exception) {
            System.out.println("Порт должен быть представлен числом!");
            return false;
        } catch (ArithmeticException exception) {
            System.out.println("Порт не может быть отрицательным!");
            return false;
        }
    }
}
