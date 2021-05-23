package com.learn.learningproject.httpclient;

import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

import java.util.Map;

/*
created by Rahul sawaria on 19/05/21
*/
@Component
public class BasicHttpClientService {

    private static  final Logger logger = LoggerFactory.getLogger(BasicHttpClientService.class);

    private static final Gson gson = new Gson();

    public <T> T getResponse(String requestUrl, Map<String, String> header, Class<T> responseClass) {

        logger.info("Going to hit Request url : {}",requestUrl);

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl);
        if (!CollectionUtils.isEmpty(header))
            setHeaders(httpGet, header);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return convertHttpResponse(httpResponse, responseClass);
        } catch (IOException e) {
            logger.error("something went wrong in get http request....");
            e.printStackTrace();
        }

        return null;
    }

    public <V, T> T postResponse(String requestUrl, Map<String, String> header, V request, Class<T> responseClass) {

        logger.info("Going to hit Request url : {}",requestUrl);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(requestUrl);
        if (!CollectionUtils.isEmpty(header))
            setHeaders(httpPost, header);

        httpPost.setHeader("Content-Type", "application/json");

        try {
            httpPost.setEntity(new StringEntity(gson.toJson(request)));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            return convertHttpResponse(httpResponse, responseClass);
        } catch (IOException e) {
            logger.error("something went wrong in post http request....");
            e.printStackTrace();
        }

        return null;
    }

    private <T> T convertHttpResponse(HttpResponse httpResponse, Class<T> responseClass) {

        logger.info("Http status code is : {}", httpResponse.getStatusLine().getStatusCode());

        HttpEntity httpEntity = httpResponse.getEntity();

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK && httpEntity != null) {

            try {
                String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info(responseString);
                return gson.fromJson(responseString, responseClass);
            } catch (IOException e) {
                logger.error("Something went wrong while converting http response..");
                e.printStackTrace();
            }
        }

        return null;
    }


    private void setHeaders(HttpRequestBase http, Map<String, String> header) {
        for (Map.Entry<String, String> entry : header.entrySet())
            http.setHeader(entry.getKey(), entry.getValue());
    }

}
