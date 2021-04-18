package com.mix.command;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetailCommand extends BaseCommand{

    @Override
    public void execute() {
        System.out.println("detail command 1");
    }
}
