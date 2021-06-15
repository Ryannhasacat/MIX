package com.mix.utils;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.DisposableChannel;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan Z
 */
public class WebClientUtils {

    /**
     * 默认3分钟超时时间
     */
    private final static Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofMinutes(3L);

    /**
     * 默认代理超时时间
     */
    private final static Long DEFAULT_PROXY_TIMEOUT_MILLIS = DEFAULT_REQUEST_TIMEOUT.toMillis();

    //region 生成WebClient.Builder的方法

    /**
     * 给了一个默认的WebClient，这个Client里面配置了默认请求超时时间
     *
     * @return 返回一个带超时时间的{@link WebClient.Builder}
     */
    public static WebClient.Builder getDefaultWebClientBuilder() {
        return getWebClientBuilder(DEFAULT_REQUEST_TIMEOUT);
    }

    /**
     * [基础创建方法]
     * 给了一个默认的WebClient，这个Client里面配置了指定了请求超时时间
     *
     * @param requestTimeOut 请求超时时间
     * @return 返回一个带超时时间的{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilder(Duration requestTimeOut) {
        if (requestTimeOut == null) {
            requestTimeOut = DEFAULT_REQUEST_TIMEOUT;
        }
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient
                .create()
                //重新定向开启
                .followRedirect(true)
                .responseTimeout(requestTimeOut)));
    }

    /**
     * 给到一个带默认超时时间，并带有不校验任何SSL整数的WebClient
     *
     * @return 返回一个带默认超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrust() {
        return getWebClientBuilderWithSslTrust(DEFAULT_REQUEST_TIMEOUT);
    }

    /**
     * 给到一个带超时时间，并带有不校验任何SSL整数的WebClient
     *
     * @param requestTimeOut 超时时间
     * @return 返回一个带超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrust(Duration requestTimeOut) {
        return getWebClientBuilderWithSslTrust(requestTimeOut, false);
    }

    /**
     * [基础创建方法]
     * 给到一个带超时时间，并带有不校验任何SSL整数的WebClient
     *
     * @param requestTimeOut     超时时间
     * @param compressionEnabled 开启压缩？默认关闭
     * @return 返回一个带超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrust(Duration requestTimeOut, boolean compressionEnabled) {
        if (requestTimeOut == null) {
            requestTimeOut = DEFAULT_REQUEST_TIMEOUT;
        }
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient
                .create()
                //重新定向开启
                .followRedirect(true)
                //这里注入了一个抛弃一切SSL认证的sslContext
                .secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))
                .responseTimeout(requestTimeOut)
                .compress(compressionEnabled)
        ));
    }

    /**
     * 给到一个带超时时间，带代理，并带有不校验任何SSL整数的WebClient
     *
     * @param requestTimeOut 超时时间
     * @param proxyDO        代理实体
     * @return 返回一个带超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrustAndPolicy(Duration requestTimeOut, ProxyDO proxyDO) {
        return getWebClientBuilderWithSslTrustAndPolicy(requestTimeOut, proxyDO, false);
    }

    /**
     * [基础创建方法]
     * 给到一个带超时时间，带代理，并带有不校验任何SSL整数的WebClient
     *
     * @param requestTimeOut     超时时间
     * @param proxyDO            代理实体
     * @param compressionEnabled 开启压缩？默认关闭
     * @return 返回一个带超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrustAndPolicy(Duration requestTimeOut, ProxyDO proxyDO, boolean compressionEnabled) {
        if (requestTimeOut == null) {
            requestTimeOut = DEFAULT_REQUEST_TIMEOUT;
        }
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient
                .create()
                //这里注入了一个抛弃一切SSL认证的sslContext
                .secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))
                .responseTimeout(requestTimeOut)
                .compress(compressionEnabled)
                //重新定向开启
                .followRedirect(true)
                .tcpConfiguration(tcpClient -> tcpClient.proxy(
                        p -> {
                            ProxyProvider.Builder pb = p.type(ProxyProvider.Proxy.HTTP)
                                    .address(InetSocketAddress.createUnresolved(proxyDO.getServiceAddress(), Integer.parseInt(proxyDO.getPort())));
                            if (StringUtils.isNotEmpty(proxyDO.getUserName())) {
                                pb.username(proxyDO.getUserName())
                                        .password(v -> proxyDO.getPassword());
                            }
                            Long proxyTimeOutMillis = proxyDO.getProxyTimeOutMillis();
                            if (proxyTimeOutMillis != null && proxyTimeOutMillis > 0) {
                                pb.connectTimeoutMillis(proxyTimeOutMillis);
                            } else {
                                pb.connectTimeoutMillis(DEFAULT_PROXY_TIMEOUT_MILLIS);
                            }
                        }
                ))
        ));
    }

    /**
     * [基础创建方法]
     * 给到一个带超时时间，带代理，并带有不校验任何SSL整数的WebClient
     * 注意，由于使用代理的，基本都是一次性使用，故默认对连接进行关闭后释放操作
     *
     * @param requestTimeOut        超时时间
     * @param proxyDO               代理实体
     * @param compressionEnabled    开启压缩？默认关闭
     * @param disposeOnDisconnected true则在构建连接池的时候额外加上.doOnDisconnected(DisposableChannel::dispose)属性，否则不设定
     * @return 返回一个带超时时间和默认全局信任的SSL请求校验器{@link WebClient.Builder}
     */
    public static WebClient.Builder getWebClientBuilderWithSslTrustAndProxy(
            Duration requestTimeOut
            , ProxyDO proxyDO
            , boolean compressionEnabled
            , boolean disposeOnDisconnected) {
        if (requestTimeOut == null) {
            requestTimeOut = DEFAULT_REQUEST_TIMEOUT;
        }

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient
                .create(
                        /*
                        这里使用ConnectionProvider.builder().build()进行构建
                        构建出的PooledConnectionProvider用于HttpClient的连接池
                        注意，不要使用HttpClient.create()方法，大批量调用会导致OOM的问题
                         */
                        ConnectionProvider.builder(Thread.currentThread().getStackTrace()[2].getClass().getSimpleName() + "_" + proxyDO.getProxyContentStr()).build()
                )
                //这里注入了一个抛弃一切SSL认证的sslContext
                .secure(sslContextSpec -> sslContextSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))
                .responseTimeout(requestTimeOut)
                .compress(compressionEnabled)
                //重新定向开启
                .followRedirect(true)
                .tcpConfiguration(tcpClient -> {
                            TcpClient tcpClientNew =
                                    tcpClient.proxy(
                                            p -> {
                                                ProxyProvider.Builder pb = p.type(ProxyProvider.Proxy.HTTP)
                                                        .address(InetSocketAddress.createUnresolved(proxyDO.getServiceAddress(), Integer.parseInt(proxyDO.getPort())));
                                                if (StringUtils.isNotEmpty(proxyDO.getUserName())) {
                                                    pb.username(proxyDO.getUserName())
                                                            .password(v -> proxyDO.getPassword());
                                                }
                                                Long proxyTimeOutMillis = proxyDO.getProxyTimeOutMillis();
                                                if (proxyTimeOutMillis != null && proxyTimeOutMillis > 0) {
                                                    pb.connectTimeoutMillis(proxyTimeOutMillis);
                                                } else {
                                                    pb.connectTimeoutMillis(DEFAULT_PROXY_TIMEOUT_MILLIS);
                                                }
                                            }
                                    );
                            if (disposeOnDisconnected) {
                                //重要，在完成连接后关闭连接
                                return tcpClientNew.doOnDisconnected(DisposableChannel::dispose);
                            } else {
                                return tcpClientNew;
                            }
                        }
                )
        ));
    }

    //endregion

    /**
     * 将http相应中的Cookie转换为用于http请求中的cookie
     * 方法中仅进行简单转换，不会对Cookie有效期等进行判断
     *
     * @param responseCookie 需要被转换的cookie
     * @return 返回可以用于请求的Cookies
     */
    public static MultiValueMap<String, String> transformResponseCookiesToRequestCookies(MultiValueMap<String, ResponseCookie> responseCookie) {
        MultiValueMap<String, String> ret = new LinkedMultiValueMap<>();
        if (responseCookie == null || responseCookie.size() == 0) {
            return ret;
        }


        for (Map.Entry<String, List<ResponseCookie>> entity : responseCookie.entrySet()) {
            String key = entity.getKey();
            List<ResponseCookie> value = entity.getValue();
            int size = value.size();
            if (size == 0) {
                continue;
            }
            List<String> cookies = new ArrayList<>(size);
            for (ResponseCookie cookie : value) {
                cookies.add(cookie.getValue());
            }
            ret.addAll(key, cookies);
        }
        return ret;
    }

}
