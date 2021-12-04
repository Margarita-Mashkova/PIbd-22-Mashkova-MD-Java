import java.awt.*;

public class DoorRealRect implements DoorInterface {
    private DoorCount doorCount;

    public void SetDoorCount(int number) {
        doorCount = DoorCount.getCount(number);
    }

    @Override
    public void DrawDoor(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x + 15 + 18 + 17, y + 25, 20, 35);
        if (doorCount.equals(DoorCount.Three)) return;
        g.fillRect(x + 220 + 50, y + 25, 20, 35);
        if (doorCount.equals(DoorCount.Four)) return;
        g.fillRect(x + 410, y + 25, 20, 35);
    }
}