package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Ферзь" (Queen).
 * Самая сильная фигура, сочетающая возможности {@link Rook} и {@link Bishop}.
 */
public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, "Q");
    }

    /**
     * Проверяет ход Ферзя по вертикали, горизонтали или диагонали.
     * @return {@code true}, если путь до цели пуст
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        boolean isDiagonal = (dx == dy);
        boolean isOrthogonal = (start.x == end.x || start.y == end.y);

        if (!isDiagonal && !isOrthogonal) return false;

        return isPathClear(board, start, end);
    }
}