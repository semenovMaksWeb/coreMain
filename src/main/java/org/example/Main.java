package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientService httpClientService = new HttpClientService();
    String result = httpClientService.getHttpResponseString("https://caniuse.com/", "GET", null, null);
    System.out.print(result);
    }
}