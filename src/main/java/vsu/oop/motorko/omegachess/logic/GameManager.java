package vsu.oop.motorko.omegachess.logic;

import vsu.oop.motorko.omegachess.core.*;
import vsu.oop.motorko.omegachess.pieces.*;
import java.util.*;

/**
 * Главный контроллер игры.
 * Управляет жизненным циклом матча, очередностью ходов и ботами.
 * * @author Tsuepeshi ( Motorko A.)
 * @version 1.0
 */
public class GameManager {
    /** Ссылка на объект доски {@link Board} */
    private Board board;
    /** Текущий цвет игрока, который должен ходить */
    private PieceColor currentTurn = PieceColor.WHITE;
    /** Состояние завершения игры */
    private boolean isGameOver = false;

    /**
     * Инициализирует новую игру и расставляет фигуры.
     */
    public GameManager() {
        this.board = new Board(12);
        initializePieces();
    }

    /**
     * Выполняет начальную расстановку всех фигур на доске.
     * <p>
     * Согласно правилам Омега-шахмат, метод расставляет:
     * <ul>
     * <li>Волшебников в угловые ячейки (эркеры).</li>
     * <li>Основной состав фигур на 1-ю и 10-ю горизонтали.</li>
     * <li>Пешки на 2-ю и 9-ю горизонтали.</li>
     * </ul>
     * </p>
     * @see #setupMainRow(int, PieceColor)
     */
    private void initializePieces() {
        // Углы для Волшебников (W)
        board.setPiece(0, 0, new Wizard(PieceColor.BLACK));
        board.setPiece(0, 11, new Wizard(PieceColor.BLACK));
        board.setPiece(11, 0, new Wizard(PieceColor.WHITE));
        board.setPiece(11, 11, new Wizard(PieceColor.WHITE));

        // Основные фигуры
        setupMainRow(1, PieceColor.BLACK);
        setupMainRow(10, PieceColor.WHITE);

        // Пешки
        for (int y = 1; y <= 10; y++) {
            board.setPiece(2, y, new OmegaPawn(PieceColor.BLACK));
            board.setPiece(9, y, new OmegaPawn(PieceColor.WHITE));
        }
    }

    /**
     * Вспомогательный метод для заполнения горизонтали основными фигурами.
     * @param row индекс строки (горизонтали)
     * @param color цвет устанавливаемых фигур {@link PieceColor}
     */
    private void setupMainRow(int row, PieceColor color) {
        board.setPiece(row, 1, new Champion(color));
        board.setPiece(row, 2, new Rook(color));
        board.setPiece(row, 3, new Knight(color));
        board.setPiece(row, 4, new Bishop(color));
        board.setPiece(row, 5, new Queen(color));
        board.setPiece(row, 6, new King(color));
        board.setPiece(row, 7, new Bishop(color));
        board.setPiece(row, 8, new Knight(color));
        board.setPiece(row, 9, new Rook(color));
        board.setPiece(row, 10, new Champion(color));
    }

    /**
     * Выполняет ход, если он валиден.
     * @param from начальная позиция
     * @param to конечная позиция
     * @return {@code true}, если ход прошел успешно
     */
    public synchronized boolean processMove(Position from, Position to) {
        if (isGameOver) return false;

        Piece p = board.getPiece(from.x, from.y);
        if (p == null || p.getColor() != currentTurn) return false;

        if (p.isValidMove(board, from, to)) {
            logMoveToConsole(p.getName(), p.getColor(), from.x, from.y, to.x, to.y);

            board.movePiece(from, to);
            p.markAsMoved();
            switchTurn();
            return true;
        }
        return false;
    }

    /**
     * Переключает очередь хода между {@link PieceColor#WHITE} и {@link PieceColor#BLACK}.
     */
    private void switchTurn() {
        currentTurn = (currentTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
    }

    /**
     * Реализует логику автоматического хода (ИИ).
     * Бот сканирует доску, находит все фигуры текущего цвета и
     * выполняет первый попавшийся валидный ход.
     */
    public boolean makeBotMove() {
        if (!board.isKingPresent(PieceColor.WHITE) || !board.isKingPresent(PieceColor.BLACK)) {
            return false;
        }

        List<Position> myPieces = new ArrayList<>();
        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                Piece p = board.getPiece(x, y);
                if (p != null && p.getColor() == currentTurn) {
                    myPieces.add(new Position(x, y));
                }
            }
        }

        Collections.shuffle(myPieces);
        for (Position from : myPieces) {
            for (int tx = 0; tx < 12; tx++) {
                for (int ty = 0; ty < 12; ty++) {
                    Position to = new Position(tx, ty);
                    if (processMove(from, to)) { // Используем общий метод передвижения
                        checkAndAnnounceWinner();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkAndAnnounceWinner() {
        if (!board.isKingPresent(PieceColor.WHITE)) {
            System.out.println(">>> ПОБЕДА ЧЕРНЫХ! Король белых пал. <<<");
        } else if (!board.isKingPresent(PieceColor.BLACK)) {
            System.out.println(">>> ПОБЕДА БЕЛЫХ! Король черных пал. <<<");
        }
    }
    private void logMoveToConsole(String pieceName, PieceColor color, int fromX, int fromY, int toX, int toY) {
        String colorStr = (color == PieceColor.WHITE) ? "Белые" : "Черные";

        // Простая конвертация координат в формат [ряд, столбец]
        System.out.printf("[%s] %s: (%d,%d) -> (%d,%d)%n",
                colorStr, pieceName, fromX, fromY, toX, toY);
    }

    /** @return объект текущей доски */
    public Board getBoard() { return board; }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }
}