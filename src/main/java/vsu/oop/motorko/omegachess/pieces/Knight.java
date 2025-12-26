package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Конь" (Knight).
 * Перемещается буквой «Г» (две клетки в одном направлении и одна под прямым углом).
 * Является «прыгающей» фигурой, поэтому не использует проверку пути.
 */
public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color, "N");
    }

    /**
     * Проверяет ход коня.
     * @return {@code true}, если ход выполнен по схеме (2,1) или (1,2)
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        // Проверка Г-образного прыжка
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}