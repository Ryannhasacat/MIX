package com.mix.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHttpResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author Ryan has a cat
 */
public class HttpClientBuilder {

    public HttpGet getHttp(String host,int port,String path){
        URIBuilder uriBuilder = new URIBuilder();
        HttpGet httpGet = null;
        try {
            URI uri = uriBuilder.setScheme("http")
                    .setHost(host)
                    .setPort(port)
                    .setPath(path)
                    .build();

             httpGet= new HttpGet(uri);
             return httpGet;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Optional<HttpGet> hg = Optional.empty();
        return null;
    }

    public HttpResponse buildResponse(){
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK,"ok");
        return response;
    }
}
