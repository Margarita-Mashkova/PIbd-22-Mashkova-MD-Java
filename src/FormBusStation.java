import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FormBusStation {
    private JPanel mainPanel;
    private JPanel toolPanel;
    private JPanel paintPanel;
    private JButton buttonSetAutobus;
    private JButton buttonSetAutobusModern;
    private JTextField textField;
    private JButton buttonTake;
    private DrawBusStation drawBusStation;
    private BusStation<ITransport, DoorInterface> busStation;// Объект от класса-парковки
    public FormBusStation() {
        /// Обработка нажатия кнопки "Припарковать автомобиль"
        buttonSetAutobus.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(null, "Choose a main color", Color.RED);
            if (mainColor!=null) {
                ITransport bus = new Autobus(100, 1000, mainColor);
                if (busStation.add(bus) >= 0) {
                    Draw();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Автовокзал переполнен");
                }
            }
        });
        /// Обработка нажатия кнопки "Припарковать автобус с гармошкой
        buttonSetAutobusModern.addActionListener(e -> {
            Color mainColor = JColorChooser.showDialog(null, "Choose a main color", Color.RED);
            if(mainColor != null){
                Color dopColor = JColorChooser.showDialog(null, "Choose a main color", Color.BLUE);
                if(dopColor != null){
                    Random rnd = new Random();
                    ITransport bus = new AutobusModern(100, 1000, mainColor, dopColor, true, true, true, rnd.nextInt(3)+3);
                    if (busStation.add(bus) >= 0) {
                        Draw();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Автовокзал переполнен");
                    }
                }
            }
        });
        /// Обработка нажатия кнопки "Забрать"
        buttonTake.addActionListener(e -> {
            if(!textField.getText().equals("")){
                int textIndex = Integer.parseInt(textField.getText());
                ITransport bus = busStation.takeAutobus(textIndex);
                if (bus != null){
                    JFrame frame = new JFrame("Автобус");
                    frame.setSize(1420,850);
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    FormAutobus form = new FormAutobus();
                    frame.add(form.getMainPanel());
                    form.SetBus(bus);
                }
                Draw();
            }
        });
    }
    /// Метод отрисовки парковки
    private void Draw(){
        drawBusStation.repaint();
    }

    public JPanel getMainPanel(){return mainPanel;}

    private void createUIComponents() {
        busStation = new BusStation<ITransport, DoorInterface>(1400, 700);
        drawBusStation = new DrawBusStation(busStation);
    }
}