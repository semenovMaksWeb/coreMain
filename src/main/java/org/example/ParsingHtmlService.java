package org.example;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Nodes;

public class ParsingHtmlService {
    /**
     * Функция парсит строку в Document
     * @param html html для парсинга
     * @param urlSite url сайта домен
     * @return Document
     */
    public Document getDocument(String html, String urlSite){
        return Jsoup.parse(html, urlSite);
    }

    /**
     * Функция вызывает select
     * @param element element от поиска
     * @param selector selector поиска css
     * @return Nodes<Element>
     */
    public Nodes<@NotNull Element> getElements(@NotNull Element element, String selector){
        return element.select(selector);
    }

    /**
     * Функция вызывает select.getFirst() - возвращает первого потомка
     * @param element element от поиска
     * @param selector selector поиска css
     * @return Element
     */
    public Element getElement(@NotNull Element element, String selector){
        return element.select(selector).getFirst();
    }

    /**
     * Возвращает атрибут элемента
     * @param element element dom
     * @param name имя атрибута
     * @return String
     */
    public String getAttr(@org.jetbrains.annotations.NotNull Element element, String name){
        return  element.attr(name);
    }

    /**
     * Возвращает содержимое текста элемента
     * @param element element dom
     * @return String
     */
    public String getText(@NotNull Element element){
        return element.text();
    }
}
