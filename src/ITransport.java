import java.awt.*;
public interface ITransport {
    // Установка позиции
    void SetPosition(int x, int y, int width, int height);
    // Изменение направления пермещения
    void MoveTransport(Directions direction);
    // Отрисовка
    void DrawTransport(Graphics g);
}
