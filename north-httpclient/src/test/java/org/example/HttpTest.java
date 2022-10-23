package org.example;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 参考文档：https://hc.apache.org/httpcomponents-client-5.1.x/quickstart.html
public class HttpTest {
    @Test
    public void testGet() throws IOException, ParseException {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://httpbin.org/get?id=11&name=中");

        // 设置 UA
        httpGet.setHeader("User-Agent", "north-httpclient");

        final CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));

        // 手动关闭资源
        response.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws IOException, ParseException {
        // 使用 try语法 自动关闭资源
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://httpbin.org/post");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", "汤米"));
            params.add(new BasicNameValuePair("password", "小☀️"));

            // 使用 UTF8 发送请求
            httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            // 设置 UA
            httpPost.setHeader("User-Agent", "north-httpclient");

            try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * 需要增加 httpclient5-fluent 库依赖
     */
    @Test
    public void testFluent() throws IOException {
        String body = "";
        body = Request.get("https://httpbin.org/get?id=fluent")
                      .setHeader("header-name", "header-value")
                      .execute()
                      .returnContent().toString();
        System.out.println(body);


        // form post
        body = Request.post("https://httpbin.org/post?id=fluent")
                      .setHeader("header-name", "header-value")
                      .bodyForm(Form.form().add("user", "name").build())
                      .execute()
                      .returnContent().toString();
        System.out.println(body);

        // post json
        // .setEntity(new StringEntity(json, ContentType.create("application/json")));
        body = Request.post("https://httpbin.org/post?id=fluent")
                      .setHeader("header-name", "header-value")
                      .connectTimeout(Timeout.ofSeconds(1))
                      .body(new StringEntity("{\"id\":12}", ContentType.APPLICATION_JSON))
                      .execute()
                      .returnContent().toString();
        System.out.println(body);
    }

    @Test
    public void testRequestConfig() throws IOException, ParseException {
        // 使用 try语法 自动关闭资源
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://httpbin.org/post");

            // 增加配制参数，如超时时间等
            RequestConfig requestConfig = RequestConfig.custom()
                                                       .setConnectTimeout(Timeout.ofSeconds(5))
                                                       .setResponseTimeout(Timeout.ofSeconds(3))
                                                       .setRedirectsEnabled(true)
                                                       .build();
            httpPost.setConfig(requestConfig);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", "汤米"));
            params.add(new BasicNameValuePair("password", "小☀️"));

            // 使用 UTF8 发送 json 请求
            httpPost.setEntity(new StringEntity(new Gson().toJson(params), ContentType.APPLICATION_JSON));

            // 设置 UA
            httpPost.setHeader("User-Agent", "north-httpclient");

            try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            }
        }
    }

    @Test
    public void testPool() throws IOException, ParseException {
        final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

        // 设置最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(100);

        // 设置每个主机（域名）的最大连接数（并发量大时，避免某一个使用过大而其他过小）
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);

        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();

        final CloseableHttpResponse response = httpClient.execute(new HttpGet("https://httpbin.org/get?from=testPool"));
        System.out.println(EntityUtils.toString(response.getEntity()));

        httpClient.close();
        response.close();
    }
}
