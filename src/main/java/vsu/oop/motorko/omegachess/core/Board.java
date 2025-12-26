package vsu.oop.motorko.omegachess.core;

import vsu.oop.motorko.omegachess.pieces.Piece;

/**
 * Класс игровой доски для Омега-шахмат.
 * Управляет сеткой объектов {@link Piece} и проверяет валидность ячеек.
 * * @author Tsuepeshi (Motorko A.)
 * @version 1.0
 */
public class Board {
    /** Двумерный массив, хранящий фигуры */
    private Piece[][] grid;
    /** Размер квадратной сетки */
    private final int size;

    /**
     * Создает доску заданного размера.
     * @param size количество клеток по одной стороне
     */
    public Board(int size) {
        this.size = size;
        this.grid = new Piece[size][size];
    }

    /**
     * Проверяет, является ли клетка частью Омега-доски (10x10 + 4 угла).
     * @param x координата строки
     * @param y координата столбца
     * @return {@code true}, если клетка доступна для игры
     */
    public boolean isTileValid(int x, int y) {
        // Проверка четырех угловых эркеров
        if ((x == 0 && y == 0) || (x == 0 && y == 11) ||
                (x == 11 && y == 0) || (x == 11 && y == 11)) return true;

        // Основное поле 10x10 (индексы от 1 до 10)
        return x >= 1 && x <= 10 && y >= 1 && y <= 10;
    }

    /**
     * Возвращает фигуру в указанной позиции.
     * @param x строка
     * @param y столбец
     * @return объект {@link Piece} или {@code null}, если клетка пуста
     */
    public Piece getPiece(int x, int y) {
        return isTileValid(x, y) ? grid[x][y] : null;
    }

    /**
     * Устанавливает фигуру в заданную клетку.
     * @param x строка
     * @param y столбец
     * @param piece фигура для установки
     */
    public void setPiece(int x, int y, Piece piece) {
        if (isTileValid(x, y)) grid[x][y] = piece;
    }

    /**
     * Физически перемещает фигуру из одной точки в другую.
     * @param from начальная позиция {@link Position}
     * @param to конечная позиция {@link Position}
     */
    public void movePiece(Position from, Position to) {
        setPiece(to.x, to.y, getPiece(from.x, from.y));
        setPiece(from.x, from.y, null);
    }

    /**
     *Поиск короля, проверка что он жив
     */

    public boolean isKingPresent(PieceColor color) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                Piece p = getPiece(i, j);
                if (p != null && "K".equals(p.getName()) && p.getColor() == color) return true;
            }
        }
        return false;
    }

    public int getSize() { return size; }
}