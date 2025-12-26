package vsu.oop.motorko.omegachess.pieces;

import vsu.oop.motorko.omegachess.core.*;
/**
 * Класс, описывающий фигуру "Чемпион" (Champion).
 * Чемпион — это комбинированная фигура, которая может:
 * 1. Шагать на 1 клетку по диагонали.
 * 2. Прыгать на 2 клетки по горизонтали или вертикали (перепрыгивая через фигуры).
 */
public class Champion extends Piece {

    public Champion(PieceColor color) {
        super(color, "C");
    }

    /**
     * Проверяет ход Чемпиона.
     * @return {@code true}, если ход соответствует правилам Чемпиона
     */
    @Override
    public boolean isValidMove(Board board, Position start, Position end) {
        if (!isTargetValid(board, end)) return false;

        int dx = Math.abs(start.x - end.x);
        int dy = Math.abs(start.y - end.y);

        // 1. Шаг на 1 клетку по диагонали (1,1)
        boolean stepDiag = (dx == 1 && dy == 1);

        // 2. Прыжок на 2 клетки по прямой (2,0) или (0,2)
        boolean jumpOrth = (dx == 2 && dy == 0) || (dx == 0 && dy == 2);

        return stepDiag || jumpOrth;
    }
}