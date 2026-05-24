package org.hades;

import org.example.HttpClientService;
import org.example.ParsingHtmlService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Nodes;

import java.io.IOException;
import java.util.Objects;

public class Hades {
    public static void main(String[] args) throws IOException, InterruptedException {
//         Получение html  с  списком богов
        String urlDarBog = "https://hades.fandom.com/ru/wiki/%D0%94%D0%B0%D1%80_%D0%B1%D0%BE%D0%B3%D0%BE%D0%B2";
        HttpClientService httpClientService = new HttpClientService();
        String html = httpClientService.getHttpResponseString(urlDarBog, "GET", null, null);
        System.out.println(html);
//        Парсинг html
        ParsingHtmlService parsingHtmlService = new ParsingHtmlService();
        Document document = parsingHtmlService.getDocument(html, "https://hades.fandom.com");
        Nodes<Element> itemBog = parsingHtmlService.getElements(document, "span[typeof='mw:File']");
        itemBog.forEach(element -> {
            String urlBog = parsingHtmlService.getAttr(
                    parsingHtmlService.getElement(element, "a.mw-file-description.image"),
                    "href"
            );

            Element imageElement = parsingHtmlService.getElement(element, "img.mw-file-element");
            String imageUrl = parsingHtmlService.getAttr(imageElement, "src");
            if(Objects.equals(imageUrl, "data:image/gif;base64,R0lGODlhAQABAIABAAAAAP///yH5BAEAAAEALAAAAAABAAEAQAICTAEAOw%3D%3D")){
                imageUrl = parsingHtmlService.getAttr(imageElement, "data-src");
            }

            String name = parsingHtmlService.getText(Objects.requireNonNull(element.nextElementSibling()));

            System.out.println("--------------------------");
            System.out.println(urlBog);
            System.out.println(imageUrl);
            System.out.println(name);
        });
    }
}