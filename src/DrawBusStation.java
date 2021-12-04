import javax.swing.*;
import java.awt.*;

public class DrawBusStation extends JPanel {
    private BusStationCollection busStationCollection = new BusStationCollection(1400, 700);;
    private String selectedItem = null;
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(selectedItem != null) {
            if (busStationCollection != null) {
                busStationCollection.get(selectedItem).Draw(g);
            }
        }
        super.repaint();
    }
    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }
    public BusStationCollection getBusStationCollection(){
        return busStationCollection;
    }
}
