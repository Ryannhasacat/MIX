package com.mix.command;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ryan has a cat
 */
public class CommandHistory {

    List<Command> commandQueue = new LinkedList<>();

    public void push(BaseCommand command){
        commandQueue.add(command);
    }

    public void pop(BaseCommand command){
        int commandOrder = command.getOrder();
        commandQueue.remove(commandOrder);
    }

    public boolean isEmpty(){
        return commandQueue.isEmpty();
    }
}
