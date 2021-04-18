package com.mix.command;

import com.mix.exception.ListisNullException;

import java.util.LinkedList;
import java.util.List;

public class CommandContext {

    List<BaseCommand> commands = new LinkedList<>();

    public CommandContext addCommand(BaseCommand command){
        commands.add(command);
        return this;
    }

    public void executeCommands(){
        if (commands.isEmpty()) {
            throw new ListisNullException();
        }
        commands.forEach(Command::execute);
    }

    public void addcomplete(){

    }
}
