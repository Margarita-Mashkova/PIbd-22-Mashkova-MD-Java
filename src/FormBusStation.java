import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.LinkedList;

public class FormBusStation {
    private JPanel mainPanel;
    private JPanel toolPanel;
    private JPanel paintPanel;
    private JButton buttonSetAutobus;
    private JButton buttonSetAutobusModern;
    private JTextField textField;
    private JButton buttonTake;
    private DrawBusStation drawBusStation;
    private JTextField textBoxBusStationName;
    private JButton buttonDelBusStation;
    private JList<String> listBoxBusStations;
    private DefaultListModel<String> listAutobus;
    private LinkedList<ITransport> linkedList;
    private JButton buttonAddBusStation;
    private JButton buttonTakeToFormAutobus;
    private  BusStationCollection busStationCollection; //Объект от класса коллекции

    public FormBusStation() {
        listAutobus = new DefaultListModel<>();
        listBoxBusStations.setModel(listAutobus);
        linkedList = new LinkedList<>();
        busStationCollection = drawBusStation.getBusStationCollection();
        /// Обработка нажатия кнопки "Поставить автобус"
        buttonSetAutobus.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(null, "Choose a main color", Color.RED);
                if (mainColor != null) {
                    ITransport bus = new Autobus(100, 1000, mainColor);
                    if (busStationCollection.get(listBoxBusStations.getSelectedValue()).add(bus) > -1) {
                        Draw();
                    } else {
                        JOptionPane.showMessageDialog(null, "Автовокзал переполнен", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Сначала создайте автовокзал", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        /// Обработка нажатия кнопки "Поставить автобус с гармошкой"
        buttonSetAutobusModern.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(null, "Choose a main color", Color.RED);
                if (mainColor != null) {
                    Color dopColor = JColorChooser.showDialog(null, "Choose a main color", Color.BLUE);
                    if (dopColor != null) {
                        Random rnd = new Random();
                        ITransport bus = new AutobusModern(100, 1000, mainColor, dopColor, true, true, true, rnd.nextInt(3) + 3);
                        if (busStationCollection.get(listBoxBusStations.getSelectedValue()).add(bus) >= 0) {
                            Draw();
                        } else {
                            JOptionPane.showMessageDialog(null, "Автовокзал переполнен", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Сначала создайте автовокзал", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        /// Обработка нажатия кнопки "Забрать в коллекцию"
        buttonTake.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                if (!textField.getText().equals("")) {
                    int textIndex = Integer.parseInt(textField.getText());
                    ITransport bus = busStationCollection.get(listBoxBusStations.getSelectedValue()).takeAutobus(textIndex);
                    if (bus != null) {
                        linkedList.add(bus);
                        Draw();
                        JOptionPane.showMessageDialog(null, "Объект с индексом " + textIndex + " добавлен в LinkedList", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Сначала создайте автовокзал", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        /// Обработка нажатия кнопки "Забрать на вторую форму"
        buttonTakeToFormAutobus.addActionListener(e -> {
            if(!linkedList.isEmpty()) {
                JFrame frame = new JFrame("Автобус");
                frame.setSize(1420, 850);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                FormAutobus form = new FormAutobus();
                frame.add(form.getMainPanel());
                form.SetBus(linkedList.get(0));
                linkedList.remove(0);
                Draw();
            }
            else {
                JOptionPane.showMessageDialog(null, "Сначала добавьте объект в LinkedList", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        /// Обработка нажатия кнопки "Добавить автовокзал"
        buttonAddBusStation.addActionListener(e -> {
            if (textBoxBusStationName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Введите название автовокзала", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            busStationCollection.AddBusStation(textBoxBusStationName.getText());
            reloadLevels();
            Draw();
        });
        /// Обработка нажатия кнопки "Удалить автовокзал"
        buttonDelBusStation.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                if ((JOptionPane.showConfirmDialog(null, "Удалить автовокзал " + listBoxBusStations.getSelectedValue() + "?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    busStationCollection.DelBusStation(listBoxBusStations.getSelectedValue());
                    reloadLevels();
                    Draw();
                }
            }
        });
        /// Метод обработки выбора элемента на listBoxLevels
        listBoxBusStations.addListSelectionListener(e -> {
            drawBusStation.setSelectedItem(listBoxBusStations.getSelectedValue());
            Draw();
        });
    }
    //Заполнение listBox
    private void reloadLevels() {
        int index = listBoxBusStations.getSelectedIndex();
        listAutobus.removeAllElements();
        int i = 0;
        for (String name : busStationCollection.keys()) {
            listAutobus.add(i, name);
            i++;
        }
        if (listAutobus.size() > 0 && (index < 0 || index >= listAutobus.size())) {
            listBoxBusStations.setSelectedIndex(0);
        } else if (listAutobus.size() > 0 && index > -1 && index < listAutobus.size()) {
            listBoxBusStations.setSelectedIndex(index);
        }
    }
    /// Метод отрисовки парковки
    private void Draw() {
        drawBusStation.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        drawBusStation = new DrawBusStation();
    }
}