import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FormBusStation {
    JFrame frameBusStation;
    private JPanel mainPanel;
    private JPanel toolPanel;
    private JPanel paintPanel;
    private JButton buttonSetAutobus;
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
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuBusStation;
    private JMenuItem saveFile;
    private JMenuItem loadFile;
    private JMenuItem saveBusStaion;
    private JMenuItem loadBusStaion;
    private BusStationCollection busStationCollection; //Объект от класса коллекции

    public FormBusStation() {
        CreateGUI();
        listAutobus = new DefaultListModel<>();
        listBoxBusStations.setModel(listAutobus);
        linkedList = new LinkedList<>();
        busStationCollection = drawBusStation.getBusStationCollection();
        /// Обработка нажатия кнопки "Добавить автобус"
        buttonSetAutobus.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                JFrame frameConfig = new JFrame("Настройка автобуса");
                frameConfig.setSize(600, 600);
                frameConfig.setVisible(true);
                frameConfig.setLocationRelativeTo(null);
                FormAutobusConfig formAutobusConfig = new FormAutobusConfig(FormBusStation.this);
                frameConfig.add(formAutobusConfig.getMainPanel());
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
            } else {
                JOptionPane.showMessageDialog(null, "Сначала создайте автовокзал", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        /// Обработка нажатия кнопки "Забрать на вторую форму"
        buttonTakeToFormAutobus.addActionListener(e -> {
            if (!linkedList.isEmpty()) {
                JFrame frameAutobus = new JFrame("Автобус");
                frameAutobus.setSize(1420, 850);
                frameAutobus.setVisible(true);
                frameAutobus.setLocationRelativeTo(null);
                FormAutobus formAutobus = new FormAutobus();
                frameAutobus.add(formAutobus.getMainPanel());
                formAutobus.SetBus(linkedList.get(0));
                linkedList.remove(0);
                Draw();
            } else {
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
    // Метод, чтобы передать автобус с формы FormAutobusConfig на форму FormBusStation
    public void setAutobus(ITransport bus) {
        if (busStationCollection.get(listBoxBusStations.getSelectedValue()).add(bus) > -1) {
            Draw();
        } else {
            JOptionPane.showMessageDialog(null, "Автовокзал переполнен", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void CreateGUI(){
        frameBusStation = new JFrame("Автовокзал");
        frameBusStation.setSize(1600, 800);
        frameBusStation.add(mainPanel);
        frameBusStation.setVisible(true);
        frameBusStation.setLocationRelativeTo(null);
        frameBusStation.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        menuFile = new JMenu("Файл");
        menuBar.add(menuFile);
        saveFile = new JMenuItem("Сохранить файл");
        saveFile.addActionListener(e -> {
            saveFile();
        });
        loadFile = new JMenuItem("Загрузить файл");
        loadFile.addActionListener(e -> {
            loadFile();
        });
        menuFile.add(saveFile);
        menuFile.add(loadFile);
        menuBusStation = new JMenu("Вокзал");
        saveBusStaion = new JMenuItem("Сохранить вокзал");
        saveBusStaion.addActionListener(e -> {
            saveBusStaion();
        });
        loadBusStaion = new JMenuItem("Загрузить вокзал");
        loadBusStaion.addActionListener(e -> {
            loadBusStaion();
        });
        menuBusStation.add(saveBusStaion);
        menuBusStation.add(loadBusStaion);
        menuBar.add(menuBusStation);
        frameBusStation.add(menuBar, BorderLayout.NORTH);
    }
    /// Метод обработки нажатия пункта меню "Сохранить файл"
    public void saveFile(){
        JFileChooser fileSaveDialog = new JFileChooser();
        fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileSaveDialog.showSaveDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.SaveData(fileSaveDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frameBusStation, "Файл не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Загрузить файл"
    public void loadFile(){
        JFileChooser fileOpenDialog = new JFileChooser();
        fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileOpenDialog.showOpenDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.LoadData(fileOpenDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно загружен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                Draw();
            } else {
                JOptionPane.showMessageDialog(frameBusStation, "Файл не загружен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Сохранить автовокзал"
    public void saveBusStaion(){
        JFileChooser fileSaveDialog = new JFileChooser();
        fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        if (listBoxBusStations.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frameBusStation, "Выберите стоянку", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = fileSaveDialog.showSaveDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.saveBusStation(fileSaveDialog.getSelectedFile().getPath(), listBoxBusStations.getSelectedValue())) {
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frameBusStation, "Файл не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Загрузить автовокзал"
    public void loadBusStaion(){
        JFileChooser fileOpenDialog = new JFileChooser();
        fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileOpenDialog.showOpenDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            if (busStationCollection.loadBusStation(fileOpenDialog.getSelectedFile().getPath())) {
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно загружен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                Draw();
            } else {
                JOptionPane.showMessageDialog(frameBusStation, "Файл не загружен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод отрисовки парковки
    private void Draw() {
        drawBusStation.repaint();
    }
    private void createUIComponents() { drawBusStation = new DrawBusStation();}
    public JPanel getMainPanel() {
        return mainPanel;
    }
}