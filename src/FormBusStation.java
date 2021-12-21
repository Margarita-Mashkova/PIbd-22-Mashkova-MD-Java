import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.security.KeyException;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.sun.media.sound.InvalidDataException;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;

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
    private Logger logger;

    public FormBusStation() {
        CreateGUI();
        logger = LogManager.getLogger(FormBusStation.class);
        //PropertyConfigurator.configure("src/log4j.properties");
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
                    try {
                        int textIndex = Integer.parseInt(textField.getText());
                        ITransport bus = busStationCollection.get(listBoxBusStations.getSelectedValue()).takeAutobus(textIndex);
                        if (bus != null) {
                            linkedList.add(bus);
                            logger.info("Добавили автобус " + bus + " в LinkedList");
                            Draw();
                            JOptionPane.showMessageDialog(null, "Объект с индексом " + textIndex + " добавлен в LinkedList", "Инфо", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (BusStationNotFoundException ex){
                        logger.warn("Попытались положить в коллекцию несуществующий автобус (неверный индекс)");
                        JOptionPane.showMessageDialog(null, "Не найден автобус по введенному месту", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
                logger.info("Забрали автобус " + linkedList.get(0) + " из LinkedList");
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
            logger.info("Добавили автовокзал " + textBoxBusStationName.getText());
            busStationCollection.AddBusStation(textBoxBusStationName.getText());
            reloadLevels();
            Draw();
        });
        /// Обработка нажатия кнопки "Удалить автовокзал"
        buttonDelBusStation.addActionListener(e -> {
            if (listBoxBusStations.getSelectedIndex() > -1) {
                if ((JOptionPane.showConfirmDialog(null, "Удалить автовокзал " + listBoxBusStations.getSelectedValue() + "?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    logger.info("Удалили автовокзал " + listBoxBusStations.getSelectedValue());
                    busStationCollection.DelBusStation(listBoxBusStations.getSelectedValue());
                    reloadLevels();
                    Draw();
                }
            }
        });
        /// Метод обработки выбора элемента на listBoxLevels
        listBoxBusStations.addListSelectionListener(e -> {
            drawBusStation.setSelectedItem(listBoxBusStations.getSelectedValue());
            logger.info("Перешли на автовокзал " + listBoxBusStations.getSelectedValue());
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
    // Метод, чтобы передать автобус с формы FormAutobusConfig на форму FormBusStation (поставить автобус)
    public void setAutobus(ITransport bus) {
        try {
            if (busStationCollection.get(listBoxBusStations.getSelectedValue()).add(bus) > -1) {
                logger.info("Добавили автобус " + bus);
                Draw();
            }
        } catch (BusStationOverflowException ex) {
            logger.warn("Попытались поставить автобус в переполненный автовокзал");
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Переполнение", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex){
            logger.fatal("Неизвестная неудачная попытка поставить автобус на автовокзал" + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Неизвестная ошибка", JOptionPane.ERROR_MESSAGE);
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
            saveBusStation();
        });
        loadBusStaion = new JMenuItem("Загрузить вокзал");
        loadBusStaion.addActionListener(e -> {
            loadBusStation();
        });
        menuBusStation.add(saveBusStaion);
        menuBusStation.add(loadBusStaion);
        menuBar.add(menuBusStation);
        frameBusStation.add(menuBar, BorderLayout.NORTH);
    }
    /// Метод обработки нажатия пункта меню "Сохранить файл"
    public void saveFile() {
        JFileChooser fileSaveDialog = new JFileChooser();
        fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileSaveDialog.showSaveDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.SaveData(fileSaveDialog.getSelectedFile().getPath());
                logger.info("Сохранено в файл " + fileSaveDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                logger.fatal("Неизвестная ошибка при сохранении файла" + ex.getMessage());
                JOptionPane.showMessageDialog(frameBusStation, "Неизвестная ошиюбка. Файл не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Загрузить файл"
    public void loadFile(){
        JFileChooser fileOpenDialog = new JFileChooser();
        fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileOpenDialog.showOpenDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.LoadData(fileOpenDialog.getSelectedFile().getPath());
                logger.info("Загржено из файла " + fileOpenDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно загружен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                Draw();
            } catch (FileNotFoundException ex){
                logger.error("Попытались загрузить несуществующий файл");
                JOptionPane.showMessageDialog(frameBusStation, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (InvalidPropertiesFormatException ex){
                logger.error("Попытались загрузить файл неверного формата");
                JOptionPane.showMessageDialog(frameBusStation, "Файл не соответствует требуемому формату", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (InvalidDataException ex){
                logger.error("Попытались загрузить объект неизвестного типа");
                JOptionPane.showMessageDialog(frameBusStation, "Нверный тип загружаемого объекта", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex){
                logger.fatal("Неизвестная ошибка при загрузке файла " + ex.getMessage());
                JOptionPane.showMessageDialog(frameBusStation, "Неизвестная ошиюбка. Файл не загружен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Сохранить автовокзал"
    public void saveBusStation() {
        JFileChooser fileSaveDialog = new JFileChooser();
        fileSaveDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        if (listBoxBusStations.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(frameBusStation, "Выберите стоянку", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = fileSaveDialog.showSaveDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.saveBusStation(fileSaveDialog.getSelectedFile().getPath(), listBoxBusStations.getSelectedValue());
                logger.info("Сохранили автовокзал " + listBoxBusStations.getSelectedValue() + " в файл " + fileSaveDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно сохранен", "Результат", JOptionPane.INFORMATION_MESSAGE);
            }catch (KeyException ex){
                logger.error("Попытались сохранить автовокзал с несуществующим ключом");
                JOptionPane.showMessageDialog(frameBusStation, "Автовокзал с таким ключом не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex){
                logger.fatal("Неизвестная ошибка при сохранении автовокзала " + ex.getMessage());
                JOptionPane.showMessageDialog(frameBusStation, "Неизвестная ошиюбка. Автовокзал не сохранен", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /// Метод обработки нажатия пункта меню "Загрузить автовокзал"
    public void loadBusStation(){
        JFileChooser fileOpenDialog = new JFileChooser();
        fileOpenDialog.setFileFilter(new FileNameExtensionFilter("Текстовый файл", "txt"));
        int result = fileOpenDialog.showOpenDialog(frameBusStation);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                busStationCollection.loadBusStation(fileOpenDialog.getSelectedFile().getPath());
                logger.info("Загрузили автовокзал " + listBoxBusStations.getSelectedValue() + " из файла " + fileOpenDialog.getSelectedFile().getPath());
                JOptionPane.showMessageDialog(frameBusStation, "Файл успешно загружен", "Результат", JOptionPane.INFORMATION_MESSAGE);
                reloadLevels();
                Draw();
            }catch (InvalidPropertiesFormatException ex){
                logger.error("Попытались загрузить файл неверного формата");
                JOptionPane.showMessageDialog(frameBusStation, "Файл не соответствует требуемому формату", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (InvalidDataException ex){
                logger.error("Попыталсиь загрузить файл, содержащий объекты неверного формата");
                JOptionPane.showMessageDialog(frameBusStation, "В файле содержатся объекты неверного формата", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex){
                logger.fatal("Неизвестная ошибка при загрузке автовокзала" + ex.getMessage());
                JOptionPane.showMessageDialog(frameBusStation, "Неизвестная ошибка. Автовокзал не загружен", "Ошибка", JOptionPane.ERROR_MESSAGE);
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