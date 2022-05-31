package com.itmo.programming.commands;

import com.itmo.programming.commands.exceptions.NoSuchCommandException;
import com.itmo.programming.commands.withargument.*;
import com.itmo.programming.commands.withoutargument.*;
import com.itmo.programming.console.ArgumentQuestioner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class CommandHolder {
    private Map<String, Command> allCommand = new HashMap<>();
    private ArgumentQuestioner argumentQuestioner;

    public CommandHolder(ArgumentQuestioner argumentQuestioner) {
        this.argumentQuestioner = argumentQuestioner;
        initializeClientCommand();
    }






    public Command getCommand(String nameCommand) throws NoSuchCommandException {
/*        if (!existCommand(nameCommand)){
            throw new NoSuchCommandException(String.format("Команды %s не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды", nameCommand));
        }*/
        if (allCommand.containsKey(nameCommand)) {
            return allCommand.get(nameCommand);
        } else {
            throw new NoSuchCommandException(String.format("Команды %s не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды", nameCommand));
        }
    }

    private void initializeClientCommand(){
        List<Command> commandList = Stream.of(new ClearCommand(), new HelpCommand(), new HistoryCommand(), new InfoCommand(), new PrintAscendingCommand(), new ShowCommand(), new CountLessThanLocation(argumentQuestioner), new ExecuteScriptCommand(), new InsertCommand(argumentQuestioner), new RemoveAnyByBirthday(argumentQuestioner), new RemoveCommand(), new RemoveGreaterCommand(argumentQuestioner), new RemoveGreaterKeyCommand(), new UpdateCommand(argumentQuestioner), new ExitCommand()).collect(Collectors.toList());
        convertToMap(commandList);
    }
    private void convertToMap(List<Command> commands) {
        commands.forEach(x -> allCommand.put(x.getName(), x));
    }


}
