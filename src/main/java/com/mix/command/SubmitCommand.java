package com.mix.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubmitCommand extends BaseCommand{

    @Override
    public void execute() {
        System.out.println("submit command 2");
    }
}
