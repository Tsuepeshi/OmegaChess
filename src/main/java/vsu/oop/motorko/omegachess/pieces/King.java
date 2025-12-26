package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Король" (King).
 * В данной реализации проверяется только базовое движение на одну клетку.
 */
public class King extends Piece {

    public King(PieceColor color) {
        super(color, "K");
    }

    /**
     * Проверяет ход Короля.
     * @return {@code true}, если расстояние до клетки не более 1 по любой оси
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        // Король ходит на одну клетку в любом направлении
        return dx <= 1 && dy <= 1;
    }
}