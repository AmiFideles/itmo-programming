package com.itmo.programming.console;

import java.io.*;

/**
 * Класс отвечает за обмен информации в консоли с пользователям
 */
public class ConsoleInterface {
    private final Writer writer;
    private final boolean interactive;
    private final Console console;
    private final String lineSeparator = "\n";
    private BufferedReader reader;

    /**
     * Констуктор, который создает consoleInterface и определяет куда писать данные и откуда считывать
     * @param reader откуда происходит чтение
     * @param interactive флаг, отвечающий за режим работы
     */
    public ConsoleInterface(BufferedReader reader, boolean interactive) {
        this.console = System.console();
        this.writer = console.writer();
        this.reader = reader;
        this.interactive = interactive;
    }

    public ConsoleInterface(boolean interactive) {
        this.console = System.console();
        this.writer = console.writer();
        this.interactive = interactive;
    }

    /**
     * Метод для считывания.
     * @return возращает считаную строку.
     */
    public String read() throws IOException {
            return interactive?console.readLine():reader.readLine();
    }

    /**
     * Выводит сообщение для пользователя
     * @param message строкад для вывода
     */
    public void write(String message) {
        try {
            writer.write(message);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeWithoutSpace(String message){
        try{
            writer.write(message);
            writer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, заправшивает ввод данных. Перед вводом выводится пользователю сообщение
     * @param message сообщение для пользователя, будет выведено перед вводом
     * @param nullable если true, то значение может быть null, иначе false
     * @return введенное пользователем строка
     */
    public String readWithMessage(String message, boolean nullable) {
        String text = !nullable?"Данное значение не может быть null":"";
        String line = "";
        do {
            if (interactive) {
                write(message + text);
            }
            try {
                line = read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null || line.isEmpty() || line.trim().isEmpty()){
                line = null;
            }
                //line = line.isEmpty() ? null : line;
        } while (!nullable && line == null);
        return line;
    }

    public String readLineFromFile() throws IOException {
        return reader.readLine();
    }
    /**
     * @return возвращает режим работы ConsoleInterface
     */
    public boolean isInteractive() {
        return interactive;
    }

}
