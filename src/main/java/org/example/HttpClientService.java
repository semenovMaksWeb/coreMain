package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class HttpClientService {
    HttpClient client = HttpClient.newHttpClient();

    /**
     * generatorHttpResponse общая функция вызова запрос API в интернет
     * @param url url запроса
     * @param method тип метода
     * @param body нагрзука
     * @param headerList массив заголовков [Имя, Значение, ...]
     * @return HttpRequest
     */
    private HttpRequest generatorHttpResponse(
            String url,
            String method,
            String body,
            String[] headerList
    ) {
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url));

        // Добавление заголовков
        if(headerList != null) {
            builder.headers(Arrays.toString(headerList));
        }

        // Добавление body
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.noBody();
        if(body != null) {
            HttpRequest.BodyPublishers.ofString(body);
        }
        builder.method((method != null) ? method : "GET", bodyPublisher);

        return builder.build();
    }

    /**
     * generatorHttpResponse  функция вызова запрос API в интернет и получения строки
     * @param url url запроса
     * @param method тип метода
     * @param body нагрзука
     * @param headerList массив заголовков [Имя, Значение, ...]
     * @return HttpRequest
     */
    public String getHttpResponseString(String url, String method, String body,String[] headerList) throws IOException, InterruptedException {
        HttpRequest httpRequest = generatorHttpResponse(url, method, body, headerList);
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
    }
}