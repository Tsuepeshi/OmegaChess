package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;

/**
 * Базовый абстрактный класс для всех шахматных фигур.
 * Содержит общую логику проверки пути и состояния фигуры.
 * * @author Tsuepeshi (Motorko A.)
 * @version 1.0
 */
public abstract class Piece {
    /** Цвет фигуры */
    protected final PieceColor color;
    /** Символьное обозначение (напр. "K" для Короля) */
    protected final String name;
    /** Флаг первого хода */
    protected boolean hasMoved = false;

    public Piece(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }

    /** @return текущий цвет фигуры */
    public PieceColor getColor() { return color; }
    /** @return имя фигуры для UI */
    public String getName() { return name; }
    /** Помечает, что фигура совершила свой первый ход */
    public void markAsMoved() { this.hasMoved = true; }

    /**
     * Проверяет, может ли фигура переместиться в указанную точку.
     * @param board текущая доска
     * @param start откуда
     * @param end куда
     * @return {@code true}, если ход соответствует правилам фигуры
     */
    public abstract boolean isValidMove(Board board, Position start, Position end);

    /**
     * Вспомогательный метод для проверки: пуста ли клетка или там враг.
     * @param board доска
     * @param end целевая точка
     * @return {@code true}, если на клетку можно наступить
     */
    protected boolean isTargetValid(Board board, Position end) {
        if (!board.isTileValid(end.x, end.y)) return false;
        Piece target = board.getPiece(end.x, end.y);
        return target == null || target.getColor() != this.color;
    }

    /**
     * Проверяет, свободен ли путь от начальной до конечной точки.
     * Не включает в проверку конечную клетку.
     * * @param board доска
     * @param start начало пути
     * @param end конец пути
     * @return {@code true}, если на пути нет других фигур
     */
    protected boolean isPathClear(Board board, Position start, Position end) {
        int dx = Integer.compare(end.x, start.x);
        int dy = Integer.compare(end.y, start.y);

        int currX = start.x + dx;
        int currY = start.y + dy;

        while (currX != end.x || currY != end.y) {
            if (board.getPiece(currX, currY) != null) return false;
            currX += dx;
            currY += dy;
        }
        return true;
    }
}