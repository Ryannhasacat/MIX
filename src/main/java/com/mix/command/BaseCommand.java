package com.mix.command;

import lombok.Data;

@Data
public abstract class BaseCommand implements Command{

    int order;

    void  backUp(){
        System.out.println("Base BackUp");
    }

    void undo(){
        System.out.println("Base undo");
    }
}
