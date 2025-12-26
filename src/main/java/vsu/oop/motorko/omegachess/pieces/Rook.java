package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс фигуры "Ладья" (Rook).
 * Ходит на любое количество клеток по горизонтали или вертикали.
 * Использование метода {@link #isPathClear(Board, Position, Position)} обязательно.
 */
public class Rook extends Piece {
    public Rook(PieceColor color) { super(color, "R"); }

    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        // Только прямые линии
        if (start.x != end.x && start.y != end.y) return false;

        return isPathClear(board, start, end);
    }
}
