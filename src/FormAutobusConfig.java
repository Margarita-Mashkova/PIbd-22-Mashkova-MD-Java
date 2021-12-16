import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormAutobusConfig{
    private JPanel toolPanel;
    private JLabel labelAutobusModern;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private JLabel labelAutobus;
    private JLabel labelParam;
    private JSpinner spinnerMaxSpeed;
    private JSpinner spinnerWeight;
    private JCheckBox checkBoxGarmoshka;
    private JLabel labelSpeed;
    private JLabel labelMainColor;
    private JLabel labelDopColor;
    private JPanel panelRed;
    private JPanel panelYellow;
    private JPanel panelGreen;
    private JPanel panelPurple;
    private JPanel panelPink;
    private JPanel panelBlue;
    private JPanel panelCyan;
    private JPanel panelBlack;
    private JCheckBox checkBoxSecondVagon;
    private JLabel labelWeight;
    private JPanel panelParam;
    private JLabel labelColor;
    private JPanel panelColorParam;
    private JLabel labelType;
    private JPanel panelButtons;
    private JPanel panelTypeBody;
    private DrawBus drawBus;
    private JPanel panelTypeDoor;
    private JLabel labelDoorEllipse;
    private JLabel labelDoorRect;
    private JLabel labelDoorSquare;
    private JLabel labelDoor;
    private JPanel panelNumberDoor;
    private JLabel labelNumberDoor;
    private JLabel labelDoor5;
    private JLabel labelDoor4;
    private JLabel labelDoor3;
    private JPanel panelColor;
    ITransport bus;
    Color color;
    int doorNumber = 4;
    int doorType = 1;

    public FormAutobusConfig(FormBusStation formBusStation) {
        CreateGUI();
        MouseMotionListener typeListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                JLabel label = (JLabel) e.getSource();
                switch (label.getText()) {
                    case "Простой автобус":
                        bus = new Autobus((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.YELLOW);
                        break;
                    case "Автобус с гармошкой":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), doorNumber, doorType);
                        break;
                }
            }
        };
        // Форма дверей
        MouseMotionListener typeDoorListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                JLabel label = (JLabel) e.getSource();
                switch (label.getText()) {
                    case "Круглые двери":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), doorNumber, 2);
                        doorType = 2;
                        break;
                    case "Квадратные двери":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), doorNumber, 3);
                        doorType = 3;
                        break;
                    case "Прямоугольные двери":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), doorNumber, 1);
                        doorType = 1;
                        break;
                }
            }
        };
        // Количество дверей
        MouseMotionListener numberDoorListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                JLabel label = (JLabel) e.getSource();
                switch (label.getText()) {
                    case "3":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), 3, doorType);
                        doorNumber = 3;
                        break;
                    case "4":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), 4, doorType);
                       doorNumber = 4;
                        break;
                    case "5":
                        bus = new AutobusModern((int) spinnerMaxSpeed.getValue(), (int) spinnerWeight.getValue(), Color.GREEN, Color.RED,
                                true, checkBoxSecondVagon.isSelected(), checkBoxGarmoshka.isSelected(), 5, doorType);
                        doorNumber = 5;
                        break;
                }
            }
        };
        MouseListener typeListenerDrop = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (checkLocation(e, drawBus)) {
                    bus.SetPosition(80, 30, drawBus.getWidth(), drawBus.getHeight());
                    drawBus.setBus(bus);
                    drawBus.repaint();
                }
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        MouseMotionListener colorListenerDrag = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                JPanel panelColor = (JPanel) e.getSource();
                color = panelColor.getBackground();
            }
        };
        MouseListener colorListenerDrop = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawBus.getAutobus() != null) {
                    if (checkLocation(e, labelMainColor)) {
                        bus.setMainColor(color);
                    }
                    else if (checkLocation(e, labelDopColor)) {
                        AutobusModern autobusModern = (AutobusModern) bus;
                        autobusModern.setDopColor(color);
                    }
                    drawBus.repaint();
                }
            }
        };
        // Обработка нажатия кнопки "Отмена"
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) mainPanel.getParent().getParent().getParent().getParent();
                frame.dispose();
            }
        });
        // Обработка нажатия кнопки "Добавить"
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formBusStation.setAutobus(bus);
                JFrame frame = (JFrame) mainPanel.getParent().getParent().getParent().getParent();
                frame.dispose();
            }
        });
        labelAutobus.addMouseMotionListener(typeListenerDrag);
        labelAutobusModern.addMouseMotionListener(typeListenerDrag);
        labelAutobus.addMouseListener(typeListenerDrop);
        labelAutobusModern.addMouseListener(typeListenerDrop);
        labelDoorSquare.addMouseMotionListener(typeDoorListenerDrag);
        labelDoorRect.addMouseMotionListener(typeDoorListenerDrag);
        labelDoorEllipse.addMouseMotionListener(typeDoorListenerDrag);
        labelDoorSquare.addMouseListener(typeListenerDrop);
        labelDoorRect.addMouseListener(typeListenerDrop);
        labelDoorEllipse.addMouseListener(typeListenerDrop);
        labelDoor3.addMouseMotionListener(numberDoorListenerDrag);
        labelDoor4.addMouseMotionListener(numberDoorListenerDrag);
        labelDoor5.addMouseMotionListener(numberDoorListenerDrag);
        labelDoor3.addMouseListener(typeListenerDrop);
        labelDoor4.addMouseListener(typeListenerDrop);
        labelDoor5.addMouseListener(typeListenerDrop);
        // Привязываем ко всем цветовым панелям drag&drop
        for (Component component : panelColor.getComponents()) {
            if (component instanceof JPanel) {
                component.addMouseMotionListener(colorListenerDrag);
                component.addMouseListener(colorListenerDrop);
            }
        }
    }
    public boolean checkLocation(MouseEvent e, JComponent component) {
        Point point = component.getLocationOnScreen();
        return e.getXOnScreen() >= point.x && e.getXOnScreen() <= point.x + component.getWidth() && e.getYOnScreen() >= point.y && e.getYOnScreen() <= point.y + component.getHeight();
    }
    public void CreateGUI() {
        spinnerMaxSpeed.setValue(100);
        spinnerWeight.setValue(100);
        labelAutobus.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelAutobusModern.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelMainColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDopColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        toolPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelParam.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelColorParam.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        labelDoorEllipse.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDoorRect.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDoorSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDoor3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDoor4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelDoor5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        drawBus = new DrawBus(bus);
    }
}

//Double dSpeed=(Double)spinnerMaxSpeed.getValue();
//int speed=dSpeed.intValue();
//Double dWeight= (Double) spinnerWeight.getValue();
//float weight = dWeight.floatValue();