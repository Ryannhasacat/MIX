package com.mix.utils;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Ryan Z
 */
@Data
public class ProxyDO implements Serializable {
    private String serviceAddress;
    private String port;
    private String userName;
    private String password;
    private Long proxyTimeOutMillis;
    private String proxyContentStr;
}
