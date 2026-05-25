package org.hades;

import org.example.FilesService;
import org.example.HttpClientService;
import org.example.ParsingHtmlService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Nodes;
import java.io.IOException;

public class Hades {

    String domain = "https://hades.ruswiki.ru/";
    String urlDarBog = "https://hades.ruswiki.ru/category/bogi/";
    String selectorDarBog = ".freshwp-recent-post";

    String catalogHtmlHades = "E:\\CoreMain\\CoreMain\\src\\main\\resources\\hades\\";
    String catalogHtmlHadesBogList = catalogHtmlHades + "bogList\\";
    String htmlNameFileHadesDarBog = "barBog.html";


    HttpClientService httpClientService = new HttpClientService();
    FilesService filesService = new FilesService();
    ParsingHtmlService parsingHtmlService = new ParsingHtmlService();

    JSONArray bogListJson = new JSONArray();

    /**
     * Получения html для парсинга с файловой системы или с сайта
     * @param path путь к файлу
     * @param fileName имя файла
     * @param url url к сайту html для парсинга
     * @return Возвращает html для парсинга
     */
    public String getHtml(String path, String fileName, String url) throws IOException, InterruptedException {
        String fullPath = path + fileName;
        if(filesService.checkFile(fullPath)) {
            System.out.println("Чтения с файла " + fullPath);
            return filesService.getFileString(fullPath);
        }else {
            System.out.println("Взята с сайта " + url);
            String html = httpClientService.getHttpResponseString(url, "GET", null, null);
            filesService.createFile(path, fileName, html);
            return html;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Hades hades = new Hades();
        String html = hades.getHtml(hades.catalogHtmlHades, hades.htmlNameFileHadesDarBog, hades.urlDarBog);
        hades.parsingHtmlDarBogName(html);
    }

    public void parsingHtmlDarBogName(String html)  {
        Document document = parsingHtmlService.getDocument(html, domain);
        Nodes<@NotNull Element> itemBog = parsingHtmlService.getElements(document, selectorDarBog);
        final int[] index = { 0 };
        itemBog.forEach(element -> {
            index[0] = index[0] + 1;
            Element link = parsingHtmlService.getElement(element, ".freshwp-recent-post-title a");
            String urlBog = parsingHtmlService.getAttr(link, "href");
            String name = parsingHtmlService.getText(link);
            JSONObject bogItem = new JSONObject();
            bogItem.put("name", name);
            bogItem.put("id", index[0]);
            bogListJson.put(bogItem);
            try {
                String htmlBogWindow = getHtml(catalogHtmlHadesBogList, name + ".html", urlBog);
                parsingHtmlDarBogItems(htmlBogWindow, urlBog);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(bogListJson.toString());
    }

    public void parsingHtmlDarBogItems(String html, String url){
        Document document = parsingHtmlService.getDocument(html, url);
        Nodes<@NotNull Element> listRowsDar = document.select(".wp-block-table tbody tr");
        listRowsDar.forEach(element -> {
            String nameDar = parsingHtmlService.getElement(element, "td").text();
            String descriptionDar = element.select("td").get(2).text();
            String requirementDar = element.select("td").get(4).text();
            System.out.println(nameDar);
            System.out.println(descriptionDar);
            System.out.println(requirementDar);
        });
    }
}