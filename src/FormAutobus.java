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
    private Graphics g;
    private ITransport bus;
    public FormAutobus() {
        Initialization();
    }
    private void Draw(){ bus.DrawTransport(g);}
    private void Initialization() {
        buttonCreate.addActionListener(e -> {
            g = MainPanel.getGraphics();
            MainPanel.update(g);
            Random rnd = new Random();
            bus = new Autobus(rnd.nextInt(200) + 100, rnd.nextInt(500)+1000, Color.red);
            //bus.Init(, Color.black, true,  true,true, rnd.nextInt(3)+3);
            bus.SetPosition(rnd.nextInt(390) + 10, rnd.nextInt(90) + 10, MainPanel.getWidth(), MainPanel.getHeight());
            Draw();
        });
        buttonCreateModern.addActionListener(e->{
            g = MainPanel.getGraphics();
            MainPanel.update(g);
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
            g = MainPanel.getGraphics();
            MainPanel.update(g);
            Move(buttonUp);
        });
        buttonRight.addActionListener(e -> {
            g = MainPanel.getGraphics();
            MainPanel.update(g);
            Move(buttonRight);
        });
        buttonDown.addActionListener(e -> {
            g = MainPanel.getGraphics();
            MainPanel.update(g);
            Move(buttonDown);
        });
        buttonLeft.addActionListener(e-> {
            g = MainPanel.getGraphics();
            MainPanel.update(g);
            Move(buttonLeft);
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
}
