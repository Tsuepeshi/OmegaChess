package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Волшебник" (Wizard).
 * Волшебник — прыгающая фигура, способная:
 * 1. Шагать на 1 клетку по диагонали.
 * 2. Прыгать на поле (3,1) или (1,3) относительно текущего.
 */
public class Wizard extends Piece {

    public Wizard(PieceColor color) {
        super(color, "W");
    }

    /**
     * Проверяет ход Волшебника.
     * @return {@code true}, если ход соответствует правилам Волшебника
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        // 1. Шаг на 1 клетку по диагонали (1,1)
        boolean stepDiag = (dx == 1 && dy == 1);

        // 2. Прыжок "Волшебника" (3,1) или (1,3)
        boolean jumpWizard = (dx == 3 && dy == 1) || (dx == 1 && dy == 3);

        return stepDiag || jumpWizard;
    }
}
