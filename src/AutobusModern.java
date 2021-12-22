import com.sun.org.apache.xpath.internal.operations.Equals;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
public class AutobusModern extends Autobus{
    public Color DopColor;/// Дополнительный цвет
    public boolean FirstVagon;/// Признак наличия первого вагона
    public boolean SecondVagon; /// Признак наличия второго вагона
    public boolean Garmoshka;/// Признак наличия гармошки
    // Смена дополнительного цвета
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
    public int DoorNumber;
    public DoorInterface door;
    protected AutobusModern(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean firstVagon, boolean secondVagon, boolean garmoshka, int doorNumber, int doorType){
        super(maxSpeed, weight, mainColor, 190*2+55, 70);
        DopColor = dopColor;
        FirstVagon = firstVagon;
        SecondVagon = secondVagon;
        Garmoshka = garmoshka;
        DoorType = doorType;
        DoorNumber = doorNumber;
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
    /// Конструктор для загрузки с файла
    public AutobusModern(String info)
    {
        super(info);
        String[] strs = info.split(separator);
        if (strs.length == 9)
        {
            MaxSpeed = Integer.parseInt(strs[0]);
            Weight = Float.parseFloat(strs[1]);
            MainColor = Color.decode(strs[2]);
            DopColor = Color.decode(strs[3]);
            FirstVagon = Boolean.parseBoolean(strs[4]);
            SecondVagon = Boolean.parseBoolean(strs[5]);
            Garmoshka = Boolean.parseBoolean(strs[6]);
            DoorNumber = Integer.parseInt(strs[7]);
            DoorType = Integer.parseInt(strs[8]);
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
            door.SetDoorCount(DoorNumber);
        }
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
    @Override
    public String toString()
    {
        return super.toString()+separator+DopColor.getRGB()+separator+FirstVagon+separator+SecondVagon+separator+Garmoshka+separator+DoorNumber+separator+DoorType;
    }

    public int compareTo(AutobusModern other) {
        if (compareTo((Autobus) other) != 0) {
            return 1;
        }
        if (DopColor.getRGB() != other.DopColor.getRGB()) {
            return Integer.valueOf(DopColor.getRGB()).compareTo(other.DopColor.getRGB());
        }
        if (Garmoshka != other.Garmoshka) {
            return Boolean.valueOf(Garmoshka).compareTo(other.Garmoshka);
        }
        if (SecondVagon != other.SecondVagon) {
            return Boolean.valueOf(SecondVagon).compareTo(other.SecondVagon);
        }
        if (DoorType != other.DoorType) {
            return Integer.valueOf(DoorType).compareTo(other.DoorType);
        }
        if (DoorNumber != other.DoorNumber) {
            return Integer.valueOf(DoorNumber).compareTo(other.DoorNumber);
        }
        return 0;
    }
    /// Метод интерфейса для класса AutobusModern
    public boolean equals(AutobusModern other)
    {
        if (!equals((Autobus)other))
        {
            return false;
        }
        if (DopColor.getRGB() != other.DopColor.getRGB())
        {
            return false;
        }
        if (SecondVagon != other.SecondVagon)
        {
            return false;
        }
        if (Garmoshka != other.Garmoshka)
        {
            return false;
        }
        if (DoorType!=other.DoorType){
            return false;
        }
        if (DoorNumber!=other.DoorNumber){
            return false;
        }
        return true;
    }
    /// Перегрузка метода от object
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof AutobusModern))
        {
            return false;
        }
        else
        {
            AutobusModern autobusModern = (AutobusModern) obj;
            return equals(autobusModern);
        }
    }
    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> it = new Iterator<Object>() {

            private int current = -1;
            private int allProperties = 9;

            @Override
            public boolean hasNext() {
                current++;
                if(door!=null)
                    return current <= allProperties;
                else return current <= allProperties-1;
            }

            @Override
            public String next() {
                if(current==0){
                    return Integer.toString(MaxSpeed);
                }
                else if(current==1){
                    return Float.toString(Weight);
                }
                else if(current==2) {
                    return Integer.toString(MainColor.getRGB());
                }
                else if(current==3){
                    return Integer.toString(DopColor.getRGB());
                }
                else if(current==4){
                    return Boolean.toString(FirstVagon);
                }
                else if(current==6){
                    return Boolean.toString(SecondVagon);
                }
                else if(current==7) {
                    return Boolean.toString(Garmoshka);
                }
                else if(current==8) {
                    return Integer.toString(DoorNumber);
                }
                else {return Integer.toString(DoorType);}
            }

        };
        return it;
    }
}
