package vsu.oop.motorko.omegachess.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для работы с конфигурацией игры.
 * Загружает данные из файла {@code config.properties}.
 */
public class GameConfig {
    private static Properties props = new Properties();

    static {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
        } catch (IOException e) {
            System.err.println("Ошибка: файл конфигурации не найден.");
        }
    }

    /**
     * Возвращает лимит времени на игру.
     * @return время в миллисекундах из файла настроек
     */
    public static long getTimeLimit() {
        return Long.parseLong(props.getProperty("game.time.limit", "300000"));
    }

    /**
     * @return размер доски (стандарт для Омега-шахмат — 12)
     */
    public static int getBoardSize() {
        return Integer.parseInt(props.getProperty("board.size", "12"));
    }
}