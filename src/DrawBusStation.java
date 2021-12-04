import javax.swing.*;
import java.awt.*;

public class DrawBusStation extends JPanel {
    private BusStation<ITransport, DoorInterface> busStation;
    public DrawBusStation(BusStation<ITransport, DoorInterface> busStation){
        this.busStation = busStation;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(busStation != null) {
            busStation.Draw(g);
        }
        super.repaint();
    }
}
