package vsu.oop.motorko.omegachess;

import javafx.scene.control.Alert;
import vsu.oop.motorko.omegachess.core.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import vsu.oop.motorko.omegachess.logic.GameManager;
import vsu.oop.motorko.omegachess.pieces.Piece;
import vsu.oop.motorko.omegachess.core.PieceColor;

/**
 * Контроллер интерфейса игры.
 * Отвечает за отрисовку доски и обработку кликов пользователя.
 */
public class HelloController {
    @FXML
    private GridPane boardGrid;

    private GameManager game = new GameManager();
    private Position selectedPosition = null;

    private PieceColor userColor; // Хранит выбор игрока (белые или черные)
    private boolean gameActive = true; // Флаг для блокировки доски после мата

    /**
     * Инициализация доски. Вызывается автоматически JavaFX.
     */
    @FXML
    public void initialize() {
        drawBoard();
    }

    /**
     * Отрисовывает сетку 12x12 и расставляет фигуры.
     */
    private void drawBoard() {
        System.out.println("Отрисовка доски началась...");
        boardGrid.getChildren().clear();
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                // Рисуем клетку только если она валидна по правилам Омега-шахмат
                if (game.getBoard().isTileValid(x, y)) {
                    Button btn = createTile(x, y);
                    boardGrid.add(btn, y, x);
                }
            }
        }
    }

    /**
     * Создает кнопку-клетку.
     * @param x координата строки
     * @param y координата столбца
     * @return настроенная кнопка
     */
    private Button createTile(int x, int y) {
        Button btn = new Button();

        btn.setMinSize(55, 55);
        btn.setMaxSize(55, 55);

        String color = ((x + y) % 2 == 0) ? "#eeeed2" : "#769656";

        btn.setStyle("-fx-background-color: " + color + "; " +
                "-fx-background-radius: 0; " +
                "-fx-font-size: 32; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 0;");

        Piece piece = game.getBoard().getPiece(x, y);
        if (piece != null) {
            btn.setText(getUnicodePiece(piece.getName()));

            if (piece.getColor() == PieceColor.WHITE) {
                btn.setTextFill(Color.WHITE);
                // Эффект обводки для белых
                // Параметры: тип, цвет, радиус размытия, распространение, x-смещение, y-смещение
                btn.setStyle(btn.getStyle() + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 3, 0.6, 0, 0);");
            } else {
                btn.setTextFill(Color.BLACK);
                // Легкое свечение для черных
                btn.setStyle(btn.getStyle() + "-fx-effect: dropshadow(one-pass-box, rgba(255,255,255,0.4), 2, 0, 0, 0);");
            }
        }

        btn.setOnAction(e -> handleCellClick(x, y));
        return btn;
    }

    /**
     * Преобразует техническое имя фигуры в красивый Unicode символ.
     * @param name краткое имя фигуры (K, Q, W, C...)
     * @return строковый символ фигуры
     */
    private String getUnicodePiece(String name) {
        return switch (name) {
            case "K" -> "♚";
            case "Q" -> "♛";
            case "R" -> "♜";
            case "B" -> "♝";
            case "N" -> "♞";
            case "P" -> "♟";
            case "C" -> "⛊"; // Чемпион
            case "W" -> "☄"; // Волшебник
            default -> name;
        };
    }


    public void onSelectWhite() {
        setupGame(PieceColor.WHITE);
    }

    public void onSelectBlack() {
        setupGame(PieceColor.BLACK);
    }

    private void setupGame(PieceColor color) {
        this.userColor = color;
        this.gameActive = true;

        drawBoard();

        // Если игрок ЧЕРНЫЙ, бот за БЕЛЫХ должен походить сразу
        if (this.userColor == PieceColor.BLACK) {
            System.out.println("Бот (Белые) делает первый ход...");
            executeBotTurn();
        }
    }
    /**
     * Логика выделения фигуры и хода.
     */
    private void handleCellClick(int x, int y) {
        if (!gameActive) return;

        // ПРОВЕРКА ОЧЕРЕДИ: Если сейчас ход не игрока, клики игнорируются
        if (game.getCurrentTurn() != userColor) {
            System.out.println("Сейчас не ваш ход!");
            return;
        }

        Piece clickedPiece = game.getBoard().getPiece(x, y);

        if (selectedPosition == null) {
            if (clickedPiece != null && clickedPiece.getColor() == userColor) {
                selectedPosition = new Position(x, y);
            }
        } else {
            // Попытка хода игрока через GameManager
            if (game.processMove(selectedPosition, new Position(x, y))) {
                selectedPosition = null;
                refreshGameState();

                // После успешного хода игрока, если игра не окончена, ходит бот
                if (gameActive) {
                    executeBotTurn();
                }
            } else {
                selectedPosition = null;
                drawBoard();
            }
        }
    }

    // Вспомогательный метод для запуска бота с задержкой, чтобы не фризило GUI
    private void executeBotTurn() {
        javafx.application.Platform.runLater(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            boolean moved = game.makeBotMove();
            if (moved) {
                refreshGameState();
            }
        });
    }

    private void refreshGameState() {
        drawBoard();

        boolean whiteKing = game.getBoard().isKingPresent(PieceColor.WHITE);
        boolean blackKing = game.getBoard().isKingPresent(PieceColor.BLACK);

        if (!whiteKing || !blackKing) {
            gameActive = false; // Останавливаем игру
            String winner = !whiteKing ? "ЧЕРНЫЕ" : "БЕЛЫЕ";

            System.out.println(">>> ИГРА ОКОНЧЕНА! ПОБЕДИЛИ " + winner + " <<<");

            // Показываем окно пользователю
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат матча");
            alert.setHeaderText(null);
            alert.setContentText("Король пал! Победитель: " + winner);
            alert.showAndWait();
        }
    }


}