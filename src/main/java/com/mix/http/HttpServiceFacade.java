package com.mix.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/apitest")
public class HttpServiceFacade {
    private static final Logger logger = LoggerFactory.getLogger(HttpServiceFacade.class);

    static CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }

    @RequestMapping(value = "/hisJson", method = RequestMethod.POST)
    @ResponseBody
    public static String jsonApi(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody String jsonData) throws IOException {

        JSONObject dataJson = JSON.parseObject(jsonData);

        logger.info("请求json参数信息=[{}]", jsonData);

        String name = dataJson.getString("name");
        String url = dataJson.getString("apiUri");
        JSONObject params = dataJson.getJSONObject("params");

        String method = request.getMethod();

        String result = "";
        HttpRequestBase requestBase= null;

        if (method.equals(RequestMethod.GET)){
            requestBase = new HttpGet(url);
            result = processRequest(requestBase);

        }
        if (method.equals(RequestMethod.POST)){
            requestBase = new HttpPost(url);
            // 设置post参数
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            params.forEach((key, value) -> parameters.add(new BasicNameValuePair(key, (String) value)));
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            HttpPost httpPost = decorateData((HttpPost) requestBase, formEntity);

            requestBase.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            processRequest(httpPost);
        }
        //TODO unchecked

        return result;
    }

    private static HttpPost decorateData(HttpPost requestBase,UrlEncodedFormEntity formEntity) {
        requestBase.setEntity(formEntity);
        return requestBase;
    }

    public static String processRequest(HttpRequestBase requestBase) throws IOException {
        String content = "";
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            // 执行请求
            closeableHttpResponse = httpClient.execute(requestBase);
            // 判断返回状态是否为200
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                //请求体内容
                content = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
            //相当于关闭浏览器
            httpClient.close();
        }

        return content;
    }

}
