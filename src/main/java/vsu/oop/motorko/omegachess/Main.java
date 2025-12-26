package vsu.oop.motorko.omegachess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vsu.oop.motorko.omegachess.logic.GameManager;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Основной класс приложения, поддерживающий два режима запуска.
 * <p>
 * Режимы определяются через ввод в стандартный поток (System.in):
 * 1. "Хочу играть!" — интерактивный режим (GUI).
 * 2. "Я наблюдатель" — не интерактивный режим (Console).
 * </p>
 * @author Tsuepeshi (Motorko A)
 * @version 1.0
 */
public class Main extends Application {
    private static String mode = "";
    private static String playerColorPreference = "";

    /**
     * Точка входа в программу. Опрашивает пользователя в консоли перед стартом.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду ('Хочу играть! [цвет]' или 'Я наблюдатель'):");
        String input = scanner.nextLine().trim();

        if (input.startsWith("Хочу играть!")) {
            mode = "INTERACTIVE";
            // Простейший парсинг цвета (белый/черный)
            if (input.toLowerCase().contains("черн")) playerColorPreference = "BLACK";
            else if (input.toLowerCase().contains("бел")) playerColorPreference = "WHITE";
            launch(args); // Запуск JavaFX для интерактивного режима
        } else if (input.equalsIgnoreCase("Я наблюдатель")) {
            mode = "OBSERVER";
            runObserverMode(); // Запуск в консоли без GUI
        } else {
            System.out.println("Неверная команда. Завершение.");
        }
    }

    /**
     * Запуск графического интерфейса для интерактивного режима.
     * @param stage подмостки JavaFX
     */
    @Override
    public void start(Stage stage) throws IOException {
        if (!mode.equals("INTERACTIVE")) return;

        // Загружаем FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Получаем ссылку на контроллер
        HelloController controller = fxmlLoader.getController();

        // Передаем выбор цвета из консоли в контроллер
        if (playerColorPreference.contains("BLACK")) {
            controller.onSelectBlack(); // Запускает игру за черных
        } else {
            controller.onSelectWhite(); // По умолчанию или если выбрали белых
        }

        // Настраиваем сцену
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Omega Chess - Interactive Mode");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Реализация не интерактивного режима.
     * Бот играет сам с собой, результат выводится в консоль.
     */
    private static void runObserverMode() {
        System.out.println("Режим наблюдателя активирован. Игра началась...");
        GameManager game = new GameManager();

        // Цикл игры ботов до мата или ничьи
        for (int i = 0; i < 50; i++) { // Ограничим 50 ходами
            game.makeBotMove();
            System.out.println("Ход #" + (i + 1) + " выполнен.");
        }
        System.out.println("Игра завершена (лимит ходов).");
    }

    /**
     * Настраивает таймер завершения игры согласно требованиям.
     * Параметры берутся из {@link vsu.oop.motorko.omegachess.logic.GameConfig}.
     * @param game текущий экземпляр менеджера игры
     */
    private void setupGameTimer(GameManager game) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Время вышло! Объявляется ничья.");
                Platform.runLater(() -> {
                    // Логика остановки игры в GUI
                });
            }
        }, 5 * 60 * 1000); // 5 минут
    }
}