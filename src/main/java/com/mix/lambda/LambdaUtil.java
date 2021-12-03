package com.mix.lambda;

public class LambdaUtil {

    /**
     *  如果参数为true抛出异常
     *
     * @param flag
     * @return com.mix.lambda.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTrue(boolean flag){

        return errorMessage -> {
            if (flag){
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param flag
     * @return com.mix.lambda.BranchHandle
     **/
    public static BranchHandle isTureOrFalse(boolean flag){

        return (trueHandle, falseHandle) -> {
            if (flag){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * str为空执行对应操作，不为空执行消费动作
     *
     * @param str
     * @return com.mix.lambda.PresentOrElseHandler
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str){

        return (consumer, runnable) -> {
            if (str == null || str.length() == 0){
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }


}
