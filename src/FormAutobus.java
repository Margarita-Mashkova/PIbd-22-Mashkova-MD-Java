import javax.swing.*;
import java.awt.*;

public class FormAutobus extends JPanel {
    private JPanel MainPanel;
    private JPanel Arrows;
    private JButton buttonLeft;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonRight;
    private DrawBus drawBus;
    private ITransport bus;

    public FormAutobus() {
        Initialization();
    }

    public void SetBus(ITransport bus) {
        this.bus = bus;
        drawBus.setBus(bus);
        Draw();
    }

    private void Draw() {
        drawBus.repaint();
    }

    private void Initialization() {
        buttonUp.setName("buttonUp");
        buttonDown.setName("buttonDown");
        buttonRight.setName("buttonRight");
        buttonLeft.setName("buttonLeft");
        buttonUp.addActionListener(e -> {
            Move(buttonUp);
            drawBus.repaint();
        });
        buttonRight.addActionListener(e -> {
            Move(buttonRight);
            drawBus.repaint();
        });
        buttonDown.addActionListener(e -> {
            Move(buttonDown);
            drawBus.repaint();
        });
        buttonLeft.addActionListener(e -> {
            Move(buttonLeft);
            drawBus.repaint();
        });
    }
    public void Move(JButton button) {
        String name = button.getName();
        switch (name) {
            case "buttonUp":
                bus.MoveTransport(Directions.Up);
                break;
            case "buttonDown":
                bus.MoveTransport(Directions.Down);
                break;
            case "buttonLeft":
                bus.MoveTransport(Directions.Left);
                break;
            case "buttonRight":
                bus.MoveTransport(Directions.Right);
                break;
        }
        Draw();
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private void createUIComponents() {
        drawBus = new DrawBus(bus);
    }
}
