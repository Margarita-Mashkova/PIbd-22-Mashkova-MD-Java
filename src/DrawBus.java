import javax.swing.*;
import java.awt.*;

public class DrawBus extends JPanel {
    private ITransport bus;
    public DrawBus(ITransport bus){
        this.bus = bus;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(bus != null) {
            bus.DrawTransport(g);
        }
        super.repaint();
    }
    public void setBus(ITransport bus){
        this.bus = bus;
    }
}
