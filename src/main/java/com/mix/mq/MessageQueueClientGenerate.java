package com.mix.mq;

public interface MessageQueueClientGenerate<T> {

    /**\
     * 获取MQ Client
     * @return
     */
    T generateClient();
}
