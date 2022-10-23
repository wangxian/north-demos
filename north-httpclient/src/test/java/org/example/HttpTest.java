package org.example;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// 参考文档：https://hc.apache.org/httpcomponents-client-5.1.x/quickstart.html
public class HttpTest {
    @Test
    public void testPost() throws IOException, ParseException {
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
}
