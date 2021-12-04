import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FormAutobus extends JPanel{
    private JPanel MainPanel;
    private JButton buttonCreate;
    private JPanel Creating;
    private JPanel Arrows;
    private JButton buttonLeft;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonRight;
    private JButton buttonCreateModern;
    private DrawBus drawBus;
    private ITransport bus;

    public FormAutobus() {
        Initialization();
    }
    public void SetBus(ITransport bus){
        this.bus = bus;
        drawBus.setBus(bus);
        Draw();
    }
    private void Draw(){ drawBus.repaint();}
    private void Initialization() {
        buttonCreate.addActionListener(e -> {
            Random rnd = new Random();
            bus = new Autobus(rnd.nextInt(200) + 100, rnd.nextInt(500)+1000, Color.red);
            bus.SetPosition(rnd.nextInt(390) + 10, rnd.nextInt(90) + 10, MainPanel.getWidth(), MainPanel.getHeight());
            Draw();
        });
        buttonCreateModern.addActionListener(e->{
            Random rnd = new Random();
            bus = new AutobusModern(rnd.nextInt(200) + 100, rnd.nextInt(500)+1000, Color.red, Color.BLUE, true, true, true, rnd.nextInt(3)+3);
            bus.SetPosition(rnd.nextInt(390) + 10, rnd.nextInt(90) + 10, MainPanel.getWidth(), MainPanel.getHeight());
            Draw();
        });
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
        buttonLeft.addActionListener(e-> {
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
    public JPanel getMainPanel(){return MainPanel;}

    private void createUIComponents() {
        drawBus = new DrawBus(bus);
    }
}
