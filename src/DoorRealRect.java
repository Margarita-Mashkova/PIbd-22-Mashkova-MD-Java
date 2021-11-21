import java.awt.*;

public class DoorRealRect implements DoorInterface {
    private DoorCount doorCount;
    public void SetDoorCount(int number)
    {
        doorCount = DoorCount.getCount(number);
    }
    @Override
    public void DrawDoor(Graphics g, Color color, int x, int y)
    {
        g.setColor(color);
        if (doorCount.equals(DoorCount.Three)) {
            g.fillRect(x + 15 + 18 + 17, y + 25, 20, 35);
        }
        else if (doorCount.equals(DoorCount.Four)) {
            g.fillRect(x + 15 + 18 + 17, y + 25, 20, 35);
            g.fillRect(x + 220 + 50, y + 25, 20, 35);
        }
        else if (doorCount.equals(DoorCount.Five)) {
            g.fillRect(x + 15 + 18 + 17, y + 25, 20, 35);
            g.fillRect(x + 220 + 50, y + 25, 20, 35);
            g.fillRect(x + 410, y + 25, 20, 35);
        }
    }
}
