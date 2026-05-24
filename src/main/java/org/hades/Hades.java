package org.hades;

import org.example.FilesService;
import org.example.HttpClientService;
import org.example.ParsingHtmlService;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Nodes;

import java.io.IOException;
import java.util.Objects;

public class Hades {

    String domain = "https://hades.fandom.com";
    String urlDarBog = "https://hades.fandom.com/ru/wiki/%D0%94%D0%B0%D1%80_%D0%B1%D0%BE%D0%B3%D0%BE%D0%B2";

    String catalogHtmlHades = "E:\\CoreMain\\CoreMain\\src\\main\\resources\\hades\\";
    String htmlNameFileHadesDarBog = "barBog.html";

    HttpClientService httpClientService = new HttpClientService();
    FilesService filesService = new FilesService();
    ParsingHtmlService parsingHtmlService = new ParsingHtmlService();

    void main() throws IOException, InterruptedException {
        // Получение html  с  списком богов
        String html;

        if(filesService.checkFile(catalogHtmlHades + htmlNameFileHadesDarBog)) {
            System.out.println("Чтения с файла");
            html = filesService.getFileString(catalogHtmlHades + htmlNameFileHadesDarBog);
        } else {
            System.out.println("Взята с сайта");
            html = httpClientService.getHttpResponseString(urlDarBog, "GET", null, null);
            filesService.createFile(catalogHtmlHades, htmlNameFileHadesDarBog, html);
        }
        parsingHtmlDarBogName(html);
    }

    public void parsingHtmlDarBogName(String html) {
        Document document = parsingHtmlService.getDocument(html, domain);
        Nodes<@NotNull Element> itemBog = parsingHtmlService.getElements(document, "span[typeof='mw:File']");
        itemBog.forEach(element -> {
            Element link = element.nextElementSibling();
            assert link != null;
            String urlBog = parsingHtmlService.getAttr(link, "href");

            Element imageElement = parsingHtmlService.getElement(element, "img.mw-file-element");
            String imageUrl = parsingHtmlService.getAttr(imageElement, "src");
            if(Objects.equals(imageUrl, "data:image/gif;base64,R0lGODlhAQABAIABAAAAAP///yH5BAEAAAEALAAAAAABAAEAQAICTAEAOw%3D%3D")){
                imageUrl = parsingHtmlService.getAttr(imageElement, "data-src");
            }

            String name = parsingHtmlService.getText(Objects.requireNonNull(link));

            System.out.println("--------------------------");
            System.out.println(domain + urlBog);
            System.out.println(imageUrl);
            System.out.println(name);
        });
    }
}