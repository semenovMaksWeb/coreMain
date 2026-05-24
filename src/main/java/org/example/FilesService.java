package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesService {

    /**
     * Создания файла
     * @param url путь до файла
     * @param name имя файла
     * @param content содержимое файла
     */
    public void createFile(String url, String name, String content) throws IOException {
        Path pathFile = Path.of(url);
        Path pathFull = Path.of(url + name);
        Files.createDirectories(pathFile);
        Files.createFile(pathFull);
        Files.writeString(pathFull, content);
    }

    /**
     * Проверка что файл существует
     * @param url путь к файлу +  имя файла
     * @return возвращает признак существования файла
     */
    public Boolean checkFile(String url) {
        return  Files.exists(Path.of(url));
    }

    /**
     * Возвращает файл в формате строки
     * @param url путь к файлу +  имя файла
     * @return Возвращает содержимое файла
     */
    public String getFileString(String url) throws IOException {
        Path path = Path.of(url);
        if(this.checkFile(url)){
            return Files.readString(path);
        }
        throw new FileNotFoundException("Файл не найден по пути: " + path.toAbsolutePath());
    }
}
