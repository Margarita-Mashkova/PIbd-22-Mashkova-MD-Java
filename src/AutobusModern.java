import java.awt.*;
public class AutobusModern extends Autobus{
    public Color DopColor;/// Дополнительный цвет
    public boolean FirstVagon;/// Признак наличия первого вагона
    public boolean SecondVagon; /// Признак наличия второго вагона
    public boolean Garmoshka;/// Признак наличия гармошки
    public void setDopColor(Color dopColor){ this.DopColor = dopColor; }
    public Color getDopColor(){
        return DopColor;
    }
    private void setFirstVagon(boolean firstVagon){
        this.FirstVagon = firstVagon;
    }
    public boolean getFirstVagon(){
        return FirstVagon;
    }
    private void setSecondVagon(boolean secondVagon){
        this.SecondVagon = secondVagon;
    }
    public boolean getSecondVagon(){
        return SecondVagon;
    }
    private void setGarmoshka(boolean garmoshka){
        this.Garmoshka = garmoshka;
    }
    public boolean getGarmoshka(){
        return Garmoshka;
    }
    public int DoorType;
    private DoorInterface door;
    protected AutobusModern(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean firstVagon, boolean secondVagon, boolean garmoshka, int doorNumber, int doorType){
        super(maxSpeed, weight, mainColor, 190*2+55, 70);
        DopColor = dopColor;
        FirstVagon = firstVagon;
        SecondVagon = secondVagon;
        Garmoshka = garmoshka;
        DoorType = doorType;
        switch (DoorType) {
            case 1:
                door = new DoorRealRect();
                break;
            case 2:
                door = new DoorRealEllipse();
                break;
            case 3:
                door = new DoorRealSquare();
                break;
        }
        door.SetDoorCount(doorNumber);
    }
    /// Отрисовка автомобиля
    @Override
    public void DrawTransport(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setStroke(new BasicStroke(2));
        super.DrawTransport(g);
        int lengthG = 30; //длина гармошки
        if (Garmoshka)
        {
            //Гармошка
            g.setColor(DopColor);
            g.drawRect(_startPosX + 190, _startPosY + 3, lengthG, 60 - 6);
            for (int n = 5; n < lengthG; n += 5)
            {
                g.drawLine(_startPosX + 190 + n, _startPosY + 3, _startPosX + 190 + n, _startPosY + 3 + 60 - 6);
            }
        }
        if (SecondVagon)
        {
            //Корпус2  //Смещаем первый вагон со всеми его элментами по Х на carWidth + lengthG
            g.drawRect(_startPosX + 190 + lengthG, _startPosY, 190+30, 60);
            //Дверь
            g.fillRect(_startPosX + 190 + lengthG + 73, _startPosY + 25, 20, 35);
            //Окна
            g.drawOval(_startPosX + 5 + 190 + lengthG, _startPosY + 10, 18, 28); //первое
            g.drawOval(_startPosX + 12 + 17 + 190 + lengthG, _startPosY + 10, 18, 28); //второе
            for (int n = 5; n < (5 + 18) * 4; n += 5 + 18)
            {
                g.drawOval(_startPosX + 5 + 18 + 17 + 50 + n + 190 + lengthG, _startPosY + 10, 18, 28);
            }
            //Колёса
            g.setColor(DopColor);
            g.fillOval(_startPosX + 20 + 190 + lengthG, _startPosY + 60 - 10, 25, 25); //заливка
            g.setColor(DopColor);
            g.drawOval(_startPosX + 20 + 190 + lengthG, _startPosY + 60 - 10, 25, 25); //заднее
            g.setColor(DopColor);
            g.fillOval(_startPosX + 190 - 20 - 25 + 190 + lengthG, _startPosY + 60 - 10, 25, 25);//заливка
            g.setColor(DopColor);
            g.drawOval(_startPosX + 190 - 20 - 25 + 190 + lengthG, _startPosY + 60 - 10, 25, 25);//переднее
        }
        door.DrawDoor(g, DopColor, _startPosX,_startPosY);
    }
}
