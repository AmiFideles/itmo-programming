package com.itmo.programming.console;

import com.itmo.programming.console.exceptions.InvalidInputException;
import com.itmo.programming.dto.ColorDTO;
import com.itmo.programming.dto.CoordinatesDTO;
import com.itmo.programming.dto.LocationDTO;
import com.itmo.programming.dto.PersonDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Менеджер команд. Данный класс инициализирует команд и предоставляет возможность получить команду по ее названию
 */
public class ArgumentQuestioner {
    //private final ConsoleInterface consoleManager;

    public ArgumentQuestioner(ConsoleInterface consoleInterface){
    }

    public PersonDTO readPersonDTO(ConsoleInterface consoleManager) {
        String name = readName(consoleManager);
        CoordinatesDTO coordinates = readCoordinates(consoleManager);
        Long height = readHeight(consoleManager);
        LocalDateTime birthday = readBirthday(consoleManager);
        String passportID = readPassportId(consoleManager);
        ColorDTO hairColor = readColor(consoleManager);
        LocationDTO location = readLocation(consoleManager);

        return new PersonDTO(name, coordinates, height, birthday, passportID, hairColor, location);
    }
/*
    public void updatePerson(PersonDTO person) {
        if (getConfirmationForUpdate("имя")) person.setName(readName(consoleManager));
        if (getConfirmationForUpdate("координаты")) person.setCoordinates(readCoordinates(consoleManager));
        if (getConfirmationForUpdate("рост")) person.setHeight(readHeight(ConsoleInterface consoleManager));
        if (getConfirmationForUpdate("дату рождения")) person.setBirthday(readBirthday(ConsoleInterface consoleManager));
        if (getConfirmationForUpdate("Паспорт ID")) person.setPassportID(readPassportId(ConsoleInterface consoleManager));
        if (getConfirmationForUpdate("цвет волос")) person.setHairColor(readColor(ConsoleInterface consoleManager));
        if (getConfirmationForUpdate("локацию")) person.setLocation(readLocation(ConsoleInterface consoleManager));

    }*/

    public LocationDTO readLocation(ConsoleInterface consoleManager) {
        double x, z;
        Double y;
        String name;

        while (true) {
            try {
                x = Double.parseDouble(consoleManager.readWithMessage("Введите координату X относительно локации. ", false).trim());
                y = Double.parseDouble(consoleManager.readWithMessage("Введите координату Y относительно локации. ", false).trim());
                z = Double.parseDouble(consoleManager.readWithMessage("Введите координату Z относительно локации. ", false).trim());
                name = consoleManager.readWithMessage("Введите имя локации: ", false).trim();
                break;

            } catch (NumberFormatException e) {
                consoleManager.write("Неправильный ввод. Координаты должны быть представлены числом и не могут являться null. Попробуйте зааново");
            }
        }
        return new LocationDTO(x, y, z, name);
    }

    private String readName(ConsoleInterface consoleManager) {
        return consoleManager.readWithMessage("Введите имя: ", canNotNull()).trim();
    }

    private CoordinatesDTO readCoordinates(ConsoleInterface consoleManager) throws NumberFormatException {
        Long x;
        Double y;

        while (true) {
            try {
                x = Long.parseLong(consoleManager.readWithMessage("Введите расположение координаты по X. ", canNotNull()).trim());
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Координата X должна быть числом типа long. Оно не может являться null");
            }
        }
        while (true) {
            try {
                y = Double.parseDouble(consoleManager.readWithMessage("Введите расположение координаты по Y. ", canNotNull()).trim());
                if (y < -536) {
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Координата Y должна быть числом типа float. Оно не может являться null и не меньше -537");
            }
        }
        return new CoordinatesDTO(x, y);
    }


    private Long readHeight(ConsoleInterface consoleManager) {
        Long height = null;
        while (true) {
            try {
                String line = consoleManager.readWithMessage("Введите значение height. Оно должно быть больше 0 ", canBeNull());
                if (line != null) {
                    height = Long.parseLong(line.trim());
                    if (height <= 0) {
                        continue;
                    }
                }
                break;
            } catch (NumberFormatException e) {
                consoleManager.write("Height должна быть числом типа long");
            }
        }
        return height;
    }

    private LocalDateTime readBirthday(ConsoleInterface consoleManager) {
        LocalDateTime localDateTime;
        while (true) {
            try {
                String time = consoleManager.readWithMessage("Введите дату дня рождения в таком формате (YYYY-MM-DD): ", canNotNull());
/*                if (time == null) {
                    break;
                }*/
                localDateTime = LocalDate.parse(time, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
                if (localDateTime.compareTo(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault())) > 0) {
                    consoleManager.write("Нужно ввести дату рождения. Она не может в будущем. Исправьте некорректные данные");
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                consoleManager.write("Вы указали неверный формат даты");
            }
        }
        return localDateTime;
    }

    private ColorDTO readColor(ConsoleInterface consoleManager) {
        StringBuilder stringBuilder = new StringBuilder();
        String color;
        Arrays.stream(ColorDTO.values()).forEach(x -> stringBuilder.append(x.getColor()).append("\n"));
        if (consoleManager.isConsoleMode()) consoleManager.write(stringBuilder.toString());

        while (true) {
            try {
                color = consoleManager.readWithMessage("Введите один из предложенных цветов: ", canNotNull()).trim();
                if (!ColorDTO.isPresent(color)) {
                    throw new InvalidInputException("Вы неправильны ввели цветы. Попробуйте еще раз");
                }
                break;
            } catch (InvalidInputException e) {
                consoleManager.write(e.getMessage());
            }
        }
        return ColorDTO.getColorByString(color);
    }

    private String readPassportId(ConsoleInterface consoleManager) {
        return consoleManager.readWithMessage("Введите passport id ", canBeNull());

    }

/*    private boolean getConfirmationForUpdate(String field) {
        String question = String.format("Вы хотетие поменять у человека %s ? Введите + или -", field);
        String answer;
        while (true) {
            answer = consoleManager.readWithMessage(question, canNotNull());
            if (answer.equals("+") || answer.equals("-")) {
                break;
            }
            consoleManager.write("Неправильный ввод");
        }
        return answer.equals("+");
    }*/

    private boolean canBeNull() {
        return true;
    }

    private boolean canNotNull() {
        return false;
    }

}
