package vsu.oop.motorko.omegachess.core;
import java.util.Objects;

/**
 * Класс, представляющий координаты клетки на игровой доске.
 * Используется для идентификации местоположения фигур и обработки кликов в UI.
 * * @author Tsuepeshi (Motorko A.)
 * @version 1.0
 */
public class Position {
    /** Индекс строки на доске */
    public final int x;
    /** Индекс столбца на доске */
    public final int y;

    /**
     * Конструктор для создания новой позиции.
     * @param x индекс строки
     * @param y индекс столбца
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Сравнивает текущую позицию с объектом {@code o}.
     * @param o объект для сравнения
     * @return {@code true}, если координаты X и Y совпадают
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}