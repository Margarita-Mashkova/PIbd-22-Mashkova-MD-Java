import java.awt.*;

public class DoorRealEllipse implements DoorInterface {
    private DoorCount doorCount;
    public void SetDoorCount(int number) {
        doorCount = DoorCount.getCount(number);
    }

    @Override
    public void DrawDoor(Graphics g, Color color, int x, int y) {
        //закраска стандартной отрисовки 2 дверей
        Color clear = new Color(240, 240, 240);
        g.setColor(clear);
        g.fillRect(x + 73, y + 25, 20, 34);
        g.fillRect(x + 190 + 103, y + 25, 20, 34);
        //если 3 двери
        g.setColor(color);
        g.fillOval(x + 15 + 18 + 17, y + 25, 20, 30);
        g.fillOval(x + 73, y + 25, 20, 30); //base
        g.fillOval(x + 190 + 103, y + 25, 20, 30); //base
        if (doorCount.equals(DoorCount.Three)) return;
        //если 4 двери
        g.fillOval(x + 220 + 50, y + 25, 20, 30);
        if (doorCount.equals(DoorCount.Four)) return;
        //если 5 дверей
        g.fillOval(x + 410, y + 25, 20, 30);
    }
}
