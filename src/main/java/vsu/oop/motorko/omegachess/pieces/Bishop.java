package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Слон" (Bishop).
 * Слон перемещается на любое количество полей по диагонали.
 * Для корректной работы проверяет отсутствие препятствий на пути.
 */
public class Bishop extends Piece {

    /**
     * Конструктор для создания Слона.
     * @param color цвет фигуры {@link PieceColor}
     */
    public Bishop(PieceColor color) {
        super(color, "B");
    }

    /**
     * Проверяет валидность хода Слона.
     * @return {@code true}, если смещение по X равно смещению по Y и путь свободен
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        // Условие диагонали: изменение по X должно быть равно изменению по Y
        if (dx != dy) return false;

        return isPathClear(board, start, end);
    }
}