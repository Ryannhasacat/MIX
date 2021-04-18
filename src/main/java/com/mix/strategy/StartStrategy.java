package com.mix.strategy;

import com.mix.command.BaseCommand;
import com.mix.command.CommandContext;
import com.mix.command.DetailCommand;
import com.mix.command.SubmitCommand;

public class StartStrategy implements Strategy{
    @Override
    public void executeStrategy() {
        BaseCommand detailCommand = new DetailCommand();
        detailCommand.setOrder(1);
        BaseCommand submitCommand = new SubmitCommand();
        submitCommand.setOrder(2);

        CommandContext context = new CommandContext();
        context
                .addCommand(detailCommand)
                .addCommand(submitCommand)
                .addcomplete();

        context.executeCommands();

    }
}
