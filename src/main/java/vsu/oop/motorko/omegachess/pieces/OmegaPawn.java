package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;

/**
 * Класс, реализующий специфику пешки в Омега-шахматах.
 * В отличие от классики, может ходить вперед на 1, 2 или 3 клетки при первом ходе.
 * * @author Tsuepeshi (Motorko A.)
 * @version 1
 */
public class OmegaPawn extends Piece {
    /** Направление движения: -1 для белых (вверх), 1 для черных (вниз) */
    private final int direction;

    public OmegaPawn(PieceColor color) {
        super(color, "P");
        this.direction = (color == PieceColor.WHITE) ? -1 : 1;
    }

    /**
     * Реализует правила хода пешки, включая прыжок на 3 клетки и взятие.
     * @param board текущее состояние {@link Board}
     * @param start позиция начала
     * @param end позиция конца
     * @return {@code true}, если маневр допустим
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!board.isTileValid(end.x, end.y)) return false;

        int dx = end.x - start.x;
        int dy = end.y - start.y;
        Piece target = board.getPiece(end.x, end.y);

        // Тихий ход (прямо)
        if (dy == 0) {
            // Ход на 1 клетку
            if (dx == direction && target == null) return true;

            // Ход на 2 или 3 клетки (только первый ход)
            if (!hasMoved && target == null) {
                if ((dx == direction * 2 || dx == direction * 3)) {
                    return isPathClear(board, start, end);
                }
            }
        }

        // Взятие фигуры (диагональ на 1 клетку)
        if (Math.abs(dy) == 1 && dx == direction) {
            return target != null && target.getColor() != this.color;
        }

        return false;
    }
}
